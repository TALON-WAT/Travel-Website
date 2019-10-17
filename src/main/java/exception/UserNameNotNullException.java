package exception;

/**
 * @author TALON WAT
 * @date 2019-10-17 20:48
 */
public class UserNameNotNullException extends Exception{
    public UserNameNotNullException(){}
    public UserNameNotNullException(String msg){
        super(msg);
    }
}
