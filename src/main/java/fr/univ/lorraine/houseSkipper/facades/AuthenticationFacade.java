package fr.univ.lorraine.houseSkipper.facades;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {


    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
/*
    public static String getAuthenticatedUser() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }*/
}
