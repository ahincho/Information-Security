package unsa.information.security.certificates.controllers;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import unsa.information.security.certificates.dtos.LoginRequest;
import unsa.information.security.certificates.dtos.RegisterRequest;
import unsa.information.security.certificates.services.AuthService;

import java.net.URI;
import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/register")
    public ResponseEntity<HashMap<String, String>> register(@RequestBody @Valid RegisterRequest registerRequest, UriComponentsBuilder uriComponentsBuilder) {
        HashMap<String, String> response = this.authService.register(registerRequest);
        URI uri = uriComponentsBuilder.path("api/auth/register/{id}").buildAndExpand(response.get("Id")).toUri();
        return ResponseEntity.created(uri).body(response);
    }
    @PostMapping("/login")
    public ResponseEntity<HashMap<String, String>> login(@RequestBody @Valid LoginRequest loginRequest) {
        HashMap<String, String> response = this.authService.login(loginRequest);
        if (response.containsKey("jwt")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}
