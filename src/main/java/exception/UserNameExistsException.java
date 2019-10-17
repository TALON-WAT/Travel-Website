package exception;

/**
 * @author TALON WAT
 * @date 2019-10-17 20:48
 */
public class UserNameExistsException extends Exception {
    public UserNameExistsException(){}
    public UserNameExistsException(String msg){
        super(msg);
    }
}
