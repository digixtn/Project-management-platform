package com.digix.projectmanagement.Auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(authenticationService.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, Object> > authenticate(
            @RequestBody AuthenticationRequest request
    ){
        AuthenticationResponse authResponse =authenticationService.authenticate(request);
        Map<String, Object> response = new HashMap<>();

        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("id", authResponse.getId());
        userDetails.put("name", authResponse.getUsername());
        userDetails.put("email", authResponse.getEmail());
        userDetails.put("role", authResponse.getRole());

        // ...add other user details

        response.put("user", userDetails);
        response.put("token", authResponse.getToken());
        return ResponseEntity.ok(response);

    }
}
