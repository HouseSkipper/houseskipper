package fr.univ.lorraine.houseSkipper.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT, reason="User with this email already exists")  // 409
public class UserEmailAlreadyExists extends RuntimeException {

    public UserEmailAlreadyExists() {
        super();
    }

}
