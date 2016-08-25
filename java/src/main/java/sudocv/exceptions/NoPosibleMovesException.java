package sudocv.exceptions;

// we know we are not serializing this class otherwise generate an id
@SuppressWarnings("serial")	
public class NoPosibleMovesException extends Exception {
	public int x;
	public int y;
	public NoPosibleMovesException(String message, int x, int y) {
        super(message);
        this.x = x;
        this.y = y;
    }
}
