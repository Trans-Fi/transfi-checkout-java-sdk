import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class transfiPaymentApi {

    private static final MediaType jsonMediaType = MediaType.get("application/json; charset=utf-8");

    private final String publicKey;
    private final String secretKey;
    private final String baseUrl;
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;

    public transfiPaymentApi(sdkConfig config) {
        this.publicKey = config.getPublicKey();
        this.secretKey = config.getSecretKey();
        this.baseUrl = "https://checkout-server.transfi.com";
        this.httpClient = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public transfiPaymentApi(sdkConfig config, String baseUrl) {
        this.publicKey = config.getPublicKey();
        this.secretKey = config.getSecretKey();
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        this.httpClient = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    private Headers generateHeaders(String method, String path, String bodyJson) throws transfiError {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String message = method.toUpperCase() + path + timestamp + bodyJson;
        String signature = hmacSha256(secretKey, message);

        return new Headers.Builder()
                .add("x-api-key", publicKey)
                .add("x-timestamp", timestamp)
                .add("x-signature", signature)
                .add("Content-Type", "application/json")
                .add("X-Api-Version", "v1")
                .build();
    }

    private String hmacSha256(String secret, String message) throws transfiError {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] raw = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (byte b : raw) hex.append(String.format("%02x", b));
            return hex.toString();
        } catch (Exception e) {
            throw new transfiError("Failed to generate HMAC signature: " + e.getMessage(), e);
        }
    }

    public String createPaymentInvoice(paymentInvoiceRequest paymentData) throws transfiError {
        String path = "/checkout/payment-link/invoice";

        String bodyJson;
        try {
            bodyJson = objectMapper.writeValueAsString(paymentData);
        } catch (IOException e) {
            throw new transfiError("Failed to serialize request: " + e.getMessage(), e);
        }

        Headers headers = generateHeaders("POST", path, bodyJson);
        Request request = new Request.Builder()
                .url(baseUrl + path)
                .headers(headers)
                .post(RequestBody.create(bodyJson, jsonMediaType))
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            String body = response.body() != null ? response.body().string() : "";

            if (!response.isSuccessful()) {
                Object errorData = tryParseJson(body);
                throw new transfiError("API Error: " + response.code(), response.code(),
                        errorData != null ? errorData : body);
            }

            JsonNode root = objectMapper.readTree(body);
            JsonNode paymentUrl = root.path("data").path("paymentUrl");
            if (paymentUrl.isMissingNode() || paymentUrl.isNull()) {
                paymentUrl = root.path("data").path("data").path("paymentUrl");
            }

            if (paymentUrl.isMissingNode() || paymentUrl.isNull())
                throw new transfiError("Unexpected response: paymentUrl not found");

            return paymentUrl.asText();

        } catch (transfiError e) {
            throw e;
        } catch (IOException e) {
            throw new transfiError("Network error: " + e.getMessage(), e);
        }
    }

    private Object tryParseJson(String json) {
        try { return objectMapper.readTree(json); } catch (Exception e) { return null; }
    }
}