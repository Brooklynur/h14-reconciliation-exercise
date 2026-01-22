package riconciliazionetitoli.exception;

public class RiconciliazioneTitoliException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public RiconciliazioneTitoliException(String message) {
		super(message);
	}
	
	public RiconciliazioneTitoliException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
