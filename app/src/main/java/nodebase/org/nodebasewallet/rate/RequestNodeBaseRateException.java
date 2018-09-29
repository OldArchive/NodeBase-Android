package nodebase.org.nodebasewallet.rate;

/**
 * Created by akshaynexus on 7/5/17.
 */
public class RequestNodeBaseRateException extends Exception {
    public RequestNodeBaseRateException(String message) {
        super(message);
    }

    public RequestNodeBaseRateException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestNodeBaseRateException(Exception e) {
        super(e);
    }
}
