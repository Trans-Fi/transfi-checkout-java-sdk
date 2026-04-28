# transfi-checkout-java-sdk

## Overview
Official Java SDK for integrating the TransFi Payment Gateway, enabling Java/JVM applications to create payment invoices and interact with the TransFi Checkout API.

## Category
SDK/Library

## Tech Stack
- Java (Maven)
- JitPack (distribution)
- OkHttp / standard HTTP client (payment API calls)

## Key Directories
- `src/main/`: SDK source — `transfiPaymentApi.java`, `paymentInvoiceRequest.java`, `individual.java`, `address.java`, `productDetails.java`, `transfiError.java`, `sdkConfig.java`
- `example/`: Usage examples
- `test/`: Unit tests
- `target/`: Maven build output

## Related Services
- `transfi-checkout-server`: The backend API this SDK calls
- `transfi-checkout-sdk`: Multi-language SDK repo (Node, Python, PHP SDKs)

## Development
```bash
mvn install       # build and install to local Maven repo
mvn test          # run tests
```

## Notes
- Distributed via JitPack (`com.github.Trans-Fi:transfi-checkout-java-sdk:v1.0.0`)
- Add JitPack repository to your Maven `pom.xml` or Gradle `build.gradle` before adding the dependency
- Current version: `v1.0.0`
