public class sdkConfig {

    private final String publicKey;
    private final String secretKey;

    public sdkConfig(String publicKey, String secretKey) {
        if (publicKey == null || publicKey.isBlank())
            throw new IllegalArgumentException("publicKey must not be null or empty");
        if (secretKey == null || secretKey.isBlank())
            throw new IllegalArgumentException("secretKey must not be null or empty");
        this.publicKey = publicKey;
        this.secretKey = secretKey;
    }

    public String getPublicKey() { return publicKey; }
    public String getSecretKey() { return secretKey; }
}