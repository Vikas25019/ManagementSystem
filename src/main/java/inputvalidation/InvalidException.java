package inputvalidation;

public class InvalidException extends Exception {
    String message = "";

    InvalidException(String error) {
        message = error;
        /*
        By calling super(message), we initialize the exception's error message and the base class takes care of setting
        up the custom message, according to the message.
         */
    }

    @Override
    public String toString() {
        return message;
    }
}
