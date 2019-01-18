package fr.univ.lorraine.houseSkipper.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT, reason="User already valide")  // 409
public class UserValidationAlreadyExists extends RuntimeException {

    public UserValidationAlreadyExists() {
        super();
    }

}
