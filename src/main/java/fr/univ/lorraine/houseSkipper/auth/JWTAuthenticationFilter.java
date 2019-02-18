package fr.univ.lorraine.houseSkipper.auth;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univ.lorraine.houseSkipper.model.ApplicationUser;
import fr.univ.lorraine.houseSkipper.repositories.UserRepository;
import fr.univ.lorraine.houseSkipper.service.EmailServiceImpl;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.mail.MessagingException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static fr.univ.lorraine.houseSkipper.auth.SecurityConstants.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    private UserRepository userRepo;
    private EmailServiceImpl notificationService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, UserRepository userRepos, EmailServiceImpl email) {
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepos;
        this.notificationService = email;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            ApplicationUser creds = new ObjectMapper()
                    .readValue(req.getInputStream(), ApplicationUser.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        ApplicationUser currentUser = userRepo.findByUsername(((User) auth.getPrincipal()).getUsername());
        boolean userAgent = currentUser.getUserAgents().contains(req.getHeader("User-Agent"));
        if (currentUser.getIsValid() && userAgent) {
            System.out.println("user ====== : " + currentUser.getUsername());
            currentUser.setToken(token);
            ObjectMapper o = new ObjectMapper();
            o.writeValue(res.getOutputStream(), currentUser);
        } else if(!currentUser.getIsValid()){
            new RestAuthenticationEntryPoint().erreur(req, res, null, "Nouvel endroit");
        } else {
            currentUser.setEmailToken(RandomStringUtils.randomAlphanumeric(8));
            userRepo.save(currentUser);
            System.out.println(currentUser.getEmailToken());
            try {
                notificationService.sendCheckUser(currentUser);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            new RestAuthenticationEntryPoint().erreur(req, res, null, "Nouvel endroit");
        }

    }

    public static String createTokenByUser(ApplicationUser user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
    }
}
