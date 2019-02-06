package fr.univ.lorraine.houseSkipper.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT, reason="Code de vérification inconnu")  // 409
public class InvalidValidationTokenException extends RuntimeException {

    public InvalidValidationTokenException() {
        super();
    }

}
