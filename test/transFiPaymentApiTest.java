import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class transfiPaymentApiTest {

    private MockWebServer mockServer;
    private transfiPaymentApi api;

    @BeforeEach
    void setUp() throws Exception {
        mockServer = new MockWebServer();
        mockServer.start();
        sdkConfig config = new sdkConfig("test-public-key", "test-secret-key");
        api = new transfiPaymentApi(config, mockServer.url("/").toString());
    }

    @AfterEach
    void tearDown() throws Exception {
        mockServer.shutdown();
    }

    @Test
    void createPaymentInvoice_returnsPaymentUrl_onSuccess() throws Exception {
        mockServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"data\":{\"paymentUrl\":\"https://pay.transfi.com/abc123\"}}"));

        String result = api.createPaymentInvoice(buildSampleRequest());

        assertEquals("https://pay.transfi.com/abc123", result);
    }

    @Test
    void createPaymentInvoice_acceptsLegacyDoubleWrappedData() throws Exception {
        mockServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"data\":{\"data\":{\"paymentUrl\":\"https://pay.transfi.com/legacy\"}}}"));

        String result = api.createPaymentInvoice(buildSampleRequest());

        assertEquals("https://pay.transfi.com/legacy", result);
    }

    @Test
    void createPaymentInvoice_throwsTransfiError_on401() {
        mockServer.enqueue(new MockResponse()
                .setResponseCode(401)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"message\":\"Unauthorized\"}"));

        transfiError error = assertThrows(transfiError.class,
                () -> api.createPaymentInvoice(buildSampleRequest()));

        assertEquals(401, error.getStatusCode());
        assertTrue(error.getMessage().contains("401"));
    }

    @Test
    void createPaymentInvoice_throwsTransfiError_on500() {
        mockServer.enqueue(new MockResponse()
                .setResponseCode(500)
                .setBody("{\"message\":\"Internal Server Error\"}"));

        transfiError error = assertThrows(transfiError.class,
                () -> api.createPaymentInvoice(buildSampleRequest()));

        assertEquals(500, error.getStatusCode());
    }

    @Test
    void createPaymentInvoice_throwsTransfiError_whenPaymentUrlMissing() {
        mockServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"data\":{}}"));

        transfiError error = assertThrows(transfiError.class,
                () -> api.createPaymentInvoice(buildSampleRequest()));

        assertTrue(error.getMessage().contains("paymentUrl not found"));
    }

    @Test
    void createPaymentInvoice_setsRequiredHeaders() throws Exception {
        mockServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"data\":{\"paymentUrl\":\"https://pay.transfi.com/test\"}}"));

        api.createPaymentInvoice(buildSampleRequest());

        RecordedRequest recorded = mockServer.takeRequest();
        assertNotNull(recorded.getHeader("x-api-key"));
        assertNotNull(recorded.getHeader("x-timestamp"));
        assertNotNull(recorded.getHeader("x-signature"));
        assertEquals("v1", recorded.getHeader("X-Api-Version"));
        assertTrue(recorded.getHeader("Content-Type").contains("application/json"));
    }

    @Test
    void sdkConfig_throwsException_whenPublicKeyBlank() {
        assertThrows(IllegalArgumentException.class,
                () -> new sdkConfig("", "secret"));
    }

    @Test
    void sdkConfig_throwsException_whenSecretKeyNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new sdkConfig("pubkey", null));
    }

    @Test
    void transfiError_storesStatusCodeAndResponseData() {
        transfiError error = new transfiError("test error", 422, "some data");
        assertEquals(422, error.getStatusCode());
        assertEquals("some data", error.getResponseData());
        assertEquals("test error", error.getMessage());
    }

    @Test
    void transfiError_nullFields_whenOnlyMessageProvided() {
        transfiError error = new transfiError("network failure");
        assertNull(error.getStatusCode());
        assertNull(error.getResponseData());
    }

    private paymentInvoiceRequest buildSampleRequest() {
        return paymentInvoiceRequest.builder()
                .paymentLinkId("LINK_001")
                .amount("100")
                .currency("USD")
                .individual(individual.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .email("john@example.com")
                        .phoneCode("+1")
                        .phone("1234567890")
                        .country("US")
                        .build())
                .productDetails(productDetails.builder()
                        .name("Test Product")
                        .description("A test product")
                        .build())
                .successRedirectUrl("https://example.com/success")
                .failureRedirectUrl("https://example.com/failure")
                .customerOrderId("order-123")
                .build();
    }
}