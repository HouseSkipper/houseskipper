package fr.univ.lorraine.houseSkipper.auth;

public class SecurityConstants {
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/sign-up";
    public static final String SIGN_UP_P_URL = "/add/prestataire";
    public static final String VALID_EMAIL = "/users/validateAccount/{email_token}";
    public static final String USER_EXISTS = "/users/exists";
}
