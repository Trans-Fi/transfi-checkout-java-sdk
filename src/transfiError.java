public class transfiError extends Exception {

    private final Integer statusCode;
    private final Object responseData;

    public transfiError(String message) {
        super(message);
        this.statusCode = null;
        this.responseData = null;
    }

    public transfiError(String message, Integer statusCode, Object responseData) {
        super(message);
        this.statusCode = statusCode;
        this.responseData = responseData;
    }

    public transfiError(String message, Throwable cause) {
        super(message, cause);
        this.statusCode = null;
        this.responseData = null;
    }

    public Integer getStatusCode() { return statusCode; }
    public Object getResponseData() { return responseData; }

    @Override
    public String toString() {
        return "transfiError{message='" + getMessage() +
               "', statusCode=" + statusCode +
               ", responseData=" + responseData + '}';
    }
}