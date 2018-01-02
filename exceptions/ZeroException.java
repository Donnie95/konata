package exceptions;

public class ZeroException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ZeroException() {
		System.out.println("Errore: Il numero inserito non è Valido");
	}

}
