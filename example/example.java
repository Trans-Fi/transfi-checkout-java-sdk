public class example {

    public static void main(String[] args) {
        sdkConfig config = new sdkConfig("YOUR_PUBLIC_KEY", "YOUR_SECRET_KEY");
        transfiPaymentApi api = new transfiPaymentApi(config);

        paymentInvoiceRequest request = paymentInvoiceRequest.builder()
                .paymentLinkId("TEST_LINK_ID")
                .amount("100")
                .currency("USD")
                .productDetails(productDetails.builder()
                        .name("Test Premium Product")
                        .description("Sample product from SDK test")
                        .build())
                .individual(individual.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .email("test@example.com")
                        .phone("1234567890")
                        .phoneCode("+1")
                        .country("US")
                        .build())
                .successRedirectUrl("https://example.com/success")
                .failureRedirectUrl("https://example.com/failure")
                .customerOrderId("order-" + System.currentTimeMillis())
                .build();

        System.out.println("Attempting to create invoice...");
        try {
            String paymentUrl = api.createPaymentInvoice(request);
            System.out.println("Success: " + paymentUrl);
        } catch (transfiError e) {
            System.out.println("transfiError caught.");
            System.out.println("Message:     " + e.getMessage());
            System.out.println("Status Code: " + e.getStatusCode());
            System.out.println("Data:        " + e.getResponseData());
        }
    }
}
