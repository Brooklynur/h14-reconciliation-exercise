package riconciliazionetitoli;

import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;
import riconciliazionetitoli.exception.RiconciliazioneTitoliException;

@org.springframework.web.bind.annotation.ControllerAdvice
@Slf4j
public class ControllerAdvice {
	
	@ExceptionHandler(RiconciliazioneTitoliException.class)
	BodyBuilder wagxExceptionHandler(RiconciliazioneTitoliException ex) {
		log.error(ex.getMessage(), ex);
		return ResponseEntity.internalServerError();
	}
}
