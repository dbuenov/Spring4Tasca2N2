package cat.dbuenov.app.errors;

public class EmpleatNotFoundException extends RuntimeException{
	
	private static final String DESCRIPCIO = "Not FOUND Exception (404)";
		
	private static final long serialVersionUID = 1L;

	public EmpleatNotFoundException(int id) {
		super(DESCRIPCIO+" No he trobat l'empleat: "+id);
	}

}
