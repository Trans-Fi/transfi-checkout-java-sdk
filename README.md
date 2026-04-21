# TransFi Java SDK

Official Java SDK for TransFi Payment Gateway integration.

## Installation

### Maven

Add JitPack repository and dependency to your `pom.xml`:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.Trans-Fi</groupId>
        <artifactId>transfi-checkout-java-sdk</artifactId>
        <version>v1.0.0</version>
    </dependency>
</dependencies>
```

### Gradle

Add JitPack repository and dependency to your `build.gradle`:

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.Trans-Fi:transfi-checkout-java-sdk:v1.0.0'
}
```

## Requirements

- Java 11 or higher

## Quick Start

### 1. Initialize the SDK

```java
sdkConfig config = new sdkConfig("YOUR_PUBLIC_KEY", "YOUR_SECRET_KEY");
transfiPaymentApi api = new transfiPaymentApi(config);
```

### 2. Create a Payment Invoice

```java
paymentInvoiceRequest request = paymentInvoiceRequest.builder()
    .paymentLinkId("YOUR_PAYMENT_LINK_ID")
    .amount("100.00")
    .currency("USD")
    .productDetails(productDetails.builder()
        .name("Premium Subscription")
        .description("Monthly subscription plan")
        .build())
    .individual(individual.builder()
        .firstName("John")
        .lastName("Doe")
        .email("john.doe@example.com")
        .phone("1234567890")
        .phoneCode("+1")
        .country("US")
        .build())
    .successRedirectUrl("https://yoursite.com/success")
    .failureRedirectUrl("https://yoursite.com/failure")
    .customerOrderId("order-" + System.currentTimeMillis())
    .build();

try {
    String paymentUrl = api.createPaymentInvoice(request);
    System.out.println("Payment URL: " + paymentUrl);
    // Redirect user to paymentUrl to complete payment
} catch (transfiError e) {
    System.err.println("Error: " + e.getMessage());
    System.err.println("Status Code: " + e.getStatusCode());
    System.err.println("Response: " + e.getResponseData());
}
```

## API Reference

### Configuration

#### `sdkConfig`

```java
sdkConfig config = new sdkConfig(String publicKey, String secretKey);
```

- `publicKey`: Your TransFi public API key
- `secretKey`: Your TransFi secret API key

### Payment API

#### `transfiPaymentApi`

```java
transfiPaymentApi api = new transfiPaymentApi(sdkConfig config);
```

##### Methods

- `String createPaymentInvoice(paymentInvoiceRequest request)` - Creates a payment invoice and returns the payment URL

### Models

#### `paymentInvoiceRequest`

Builder pattern for creating payment requests:

```java
paymentInvoiceRequest.builder()
    .paymentLinkId(String)           // Required: Payment link identifier
    .amount(String)                   // Required: Payment amount
    .currency(String)                 // Required: Currency code (USD, EUR, etc.)
    .productDetails(productDetails)   // Required: Product information
    .individual(individual)           // Required: Customer information
    .successRedirectUrl(String)       // Required: Success redirect URL
    .failureRedirectUrl(String)       // Required: Failure redirect URL
    .customerOrderId(String)          // Required: Unique order identifier
    .build();
```

#### `productDetails`

```java
productDetails.builder()
    .name(String)                     // Product name
    .description(String)              // Product description
    .build();
```

#### `individual`

```java
individual.builder()
    .firstName(String)                // Customer first name
    .lastName(String)                 // Customer last name
    .email(String)                    // Customer email
    .phone(String)                    // Customer phone number
    .phoneCode(String)                // Phone country code (e.g., "+1")
    .country(String)                  // Country code (e.g., "US")
    .build();
```

#### `address` (Optional)

```java
address.builder()
    .line1(String)                    // Address line 1
    .city(String)                     // City
    .state(String)                    // State/Province
    .postalCode(String)               // Postal code
    .country(String)                  // Country code
    .build();
```

### Error Handling

#### `transfiError`

All SDK methods throw `transfiError` on failure:

```java
try {
    String paymentUrl = api.createPaymentInvoice(request);
} catch (transfiError e) {
    int statusCode = e.getStatusCode();      // HTTP status code
    String message = e.getMessage();          // Error message
    String responseData = e.getResponseData(); // Raw response data
}
```

## Example

See [example/example.java](example/example.java) for a complete working example.

## Support

For issues and questions:
- Email: support@transfi.com
- GitHub Issues: [https://github.com/Trans-Fi/transfi-checkout-java-sdk/issues](https://github.com/Trans-Fi/transfi-checkout-java-sdk/issues)

## License

MIT License - see [LICENSE](LICENSE) file for details