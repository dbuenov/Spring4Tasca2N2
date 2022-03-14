package cat.dbuenov.app.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AvisErrors {
	
	@ResponseBody
	@ExceptionHandler(EmpleatNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String manipuladorEmpleatNoTrobat(EmpleatNotFoundException ex) {
		return ex.getMessage();
	}
	
	@ResponseBody
	@ExceptionHandler(FeinaNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String manipuladorFeinaNoTrobada(FeinaNotFoundException ex) {
		return ex.getMessage();
	}
	
	@ResponseBody
	@ExceptionHandler(FotoNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String manipuladorFotoNoTrobada(FotoNotFoundException ex) {
		return ex.getMessage();
	}
	

}

