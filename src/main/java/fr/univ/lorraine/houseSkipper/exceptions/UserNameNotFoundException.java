package fr.univ.lorraine.houseSkipper.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT, reason="User's name does not exist")  // 409
public class UserNameNotFoundException extends RuntimeException {

    public UserNameNotFoundException() {
        super();
    }
}
