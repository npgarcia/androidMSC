package nadia.com.user;

/**
 * Created by ngarcia on 5/2/15.
 */
public class SignIn {

    private boolean success;
    private String cause;

    public SignIn(){

    }

    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public String getCause() {
        return cause;
    }
    public void setCause(String cause) {
        this.cause = cause;
    }

}
