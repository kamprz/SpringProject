package wat.semestr8.tim.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import wat.semestr8.tim.security.user.AppUser;
import wat.semestr8.tim.security.UserService;
import wat.semestr8.tim.security.SecurityConfiguration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private UserService userService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException
    {
        try
        {
            AppUser appUser = new ObjectMapper().readValue(request.getInputStream(), AppUser.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(appUser.getEmail(),
                            appUser.getPassword(),
                            userService.getAuthorities(appUser.getEmail()))
            );
        }
        catch (IOException e) { throw new RuntimeException(e); }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain filterChain,
                                            Authentication authentication) throws IOException, ServletException
    {
        String token = JWT.create()
                .withSubject(((User) authentication.getPrincipal()).getUsername())
                .sign(Algorithm.HMAC512(SecurityConfiguration.SEED.getBytes()));
        response.addHeader(SecurityConfiguration.AUTHORIZATION_HEADER_STRING, SecurityConfiguration.TOKEN_PREFIX + token);
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Headers","Authorization");
        response.addHeader("Access-Control-Expose-Headers","Authorization");
    }
}
