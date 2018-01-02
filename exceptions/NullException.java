package exceptions;

public class NullException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NullException() {
		System.out.println("Errore: Il campo non può essere vuoto");
	}

}
