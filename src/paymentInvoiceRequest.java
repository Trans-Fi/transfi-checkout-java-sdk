import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class paymentInvoiceRequest {

    private String paymentLinkId;
    private String amount;
    private String currency;
    private productDetails productDetails;
    private individual individual;
    private String successRedirectUrl;
    private String failureRedirectUrl;
    private String customerOrderId;

    public paymentInvoiceRequest() {}

    public String getPaymentLinkId() { return paymentLinkId; }
    public void setPaymentLinkId(String v) { this.paymentLinkId = v; }

    public String getAmount() { return amount; }
    public void setAmount(String v) { this.amount = v; }

    public String getCurrency() { return currency; }
    public void setCurrency(String v) { this.currency = v; }

    public productDetails getProductDetails() { return productDetails; }
    public void setProductDetails(productDetails v) { this.productDetails = v; }

    public individual getIndividual() { return individual; }
    public void setIndividual(individual v) { this.individual = v; }

    public String getSuccessRedirectUrl() { return successRedirectUrl; }
    public void setSuccessRedirectUrl(String v) { this.successRedirectUrl = v; }

    public String getFailureRedirectUrl() { return failureRedirectUrl; }
    public void setFailureRedirectUrl(String v) { this.failureRedirectUrl = v; }

    public String getCustomerOrderId() { return customerOrderId; }
    public void setCustomerOrderId(String v) { this.customerOrderId = v; }

    public static builder builder() { return new builder(); }

    public static class builder {
        private final paymentInvoiceRequest r = new paymentInvoiceRequest();
        public builder paymentLinkId(String v) { r.paymentLinkId = v; return this; }
        public builder amount(String v) { r.amount = v; return this; }
        public builder currency(String v) { r.currency = v; return this; }
        public builder productDetails(productDetails v) { r.productDetails = v; return this; }
        public builder individual(individual v) { r.individual = v; return this; }
        public builder successRedirectUrl(String v) { r.successRedirectUrl = v; return this; }
        public builder failureRedirectUrl(String v) { r.failureRedirectUrl = v; return this; }
        public builder customerOrderId(String v) { r.customerOrderId = v; return this; }
        public paymentInvoiceRequest build() { return r; }
    }
}