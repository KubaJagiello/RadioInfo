package Model;

/**
 * Class that extends Exception
 */
public class LoadingXMLException extends Exception{

    /**
     * @param cause case of Exception
     */
    public LoadingXMLException(String cause){
        super(cause);
    }
}
