import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class address {

    private String city;
    private String state;
    private String street;
    private String postalCode;

    public address() {}

    public String getCity() { return city; }
    public void setCity(String v) { this.city = v; }

    public String getState() { return state; }
    public void setState(String v) { this.state = v; }

    public String getStreet() { return street; }
    public void setStreet(String v) { this.street = v; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String v) { this.postalCode = v; }

    public static builder builder() { return new builder(); }

    public static class builder {
        private final address a = new address();
        public builder city(String v) { a.city = v; return this; }
        public builder state(String v) { a.state = v; return this; }
        public builder street(String v) { a.street = v; return this; }
        public builder postalCode(String v) { a.postalCode = v; return this; }
        public address build() { return a; }
    }
}