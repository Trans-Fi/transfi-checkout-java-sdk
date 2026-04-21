import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class individual {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String phoneCode;
    private String country;
    private address address;

    public individual() {}

    public String getFirstName() { return firstName; }
    public void setFirstName(String v) { this.firstName = v; }

    public String getLastName() { return lastName; }
    public void setLastName(String v) { this.lastName = v; }

    public String getEmail() { return email; }
    public void setEmail(String v) { this.email = v; }

    public String getPhone() { return phone; }
    public void setPhone(String v) { this.phone = v; }

    public String getPhoneCode() { return phoneCode; }
    public void setPhoneCode(String v) { this.phoneCode = v; }

    public String getCountry() { return country; }
    public void setCountry(String v) { this.country = v; }

    public address getAddress() { return address; }
    public void setAddress(address v) { this.address = v; }

    public static builder builder() { return new builder(); }

    public static class builder {
        private final individual i = new individual();
        public builder firstName(String v) { i.firstName = v; return this; }
        public builder lastName(String v) { i.lastName = v; return this; }
        public builder email(String v) { i.email = v; return this; }
        public builder phone(String v) { i.phone = v; return this; }
        public builder phoneCode(String v) { i.phoneCode = v; return this; }
        public builder country(String v) { i.country = v; return this; }
        public builder address(address v) { i.address = v; return this; }
        public individual build() { return i; }
    }
}