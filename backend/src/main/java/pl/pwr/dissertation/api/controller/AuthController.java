package pl.pwr.dissertation.api.controller;

import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;

public interface AuthController {

    @PostMapping("/login")
    AuthUserResponse login (@RequestBody AuthRequest authRequest);

    @Data
    class AuthRequest{
        private String username;
        private String password;
    }



    @Data
    class AuthUserResponse{
        private int id;
        private String username;
        private String jwtToken;
        private Set<String> roles;
        private String fullName;
    }
}
