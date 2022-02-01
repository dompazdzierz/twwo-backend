package pl.pwr.dissertation.api.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pwr.dissertation.api.controller.AuthController;
import pl.pwr.dissertation.config.security.JwtTokenUtil;
import pl.pwr.dissertation.logic.domain.User;
import pl.pwr.dissertation.persistance.entity.Role;
import pl.pwr.dissertation.persistance.entity.UserEntity;

import java.util.stream.Collectors;


@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthControllerImpl extends BaseController implements AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public AuthUserResponse login(AuthRequest authRequest) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        var user = getUser(authenticate);
        AuthUserResponse response = new AuthUserResponse();
        response.setId(user.getId());
        response.setJwtToken(jwtTokenUtil.generateAccessToken(user));
        response.setUsername(user.getUsername());
        response.setFullName(user.getFirstName() + " " + user.getLastName());
        response.setRoles(user.getRoles());
        return response;
    }
}
