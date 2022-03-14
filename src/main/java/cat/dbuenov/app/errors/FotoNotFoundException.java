package cat.dbuenov.app.errors;

public class FotoNotFoundException extends RuntimeException{
	
	private static final String DESCRIPCIO = "Not FOUND Exception (404)";
		
	private static final long serialVersionUID = 1L;

	public FotoNotFoundException(int id) {
		super(DESCRIPCIO+" L'empleat "+id+" no te foto");
	}

}
