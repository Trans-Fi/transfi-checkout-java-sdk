import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class productDetails {

    private String name;
    private String description;
    private String imageUrl;

    public productDetails() {}

    public String getName() { return name; }
    public void setName(String v) { this.name = v; }

    public String getDescription() { return description; }
    public void setDescription(String v) { this.description = v; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String v) { this.imageUrl = v; }

    public static builder builder() { return new builder(); }

    public static class builder {
        private final productDetails p = new productDetails();
        public builder name(String v) { p.name = v; return this; }
        public builder description(String v) { p.description = v; return this; }
        public builder imageUrl(String v) { p.imageUrl = v; return this; }
        public productDetails build() { return p; }
    }
}