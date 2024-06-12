package unsa.information.security.certificates.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import unsa.information.security.certificates.dtos.LoginRequest;
import unsa.information.security.certificates.dtos.RegisterRequest;
import unsa.information.security.certificates.persistence.entities.UserEntity;
import unsa.information.security.certificates.persistence.repositories.UserRepository;

import java.util.HashMap;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    public AuthService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }
    public HashMap<String, String> login(LoginRequest loginRequest) {
        HashMap<String, String> jwtResponse = new HashMap<>();
        try {
            Optional<UserEntity> userEntity = this.userRepository.findByEmail(loginRequest.getEmail());
            if (userEntity.isEmpty()) {
                jwtResponse.put("error", "Email not found");
                return jwtResponse;
            }
            if (verifyPassword(loginRequest.getPassword(), userEntity.get().getPassword())) {
                jwtResponse.put("jwt", this.jwtService.generateJwt(userEntity.get().getFirstname(), userEntity.get().getLastname()));
            } else {
                jwtResponse.put("error", "Authentication failed");
            }
            return jwtResponse;
        } catch (Exception exception) {
            jwtResponse.put("exception", exception.getMessage());
            return jwtResponse;
        }
    }
    public HashMap<String, String> register(RegisterRequest registerRequest) {
        HashMap<String, String> response = new HashMap<>();
        try {
            Optional<UserEntity> verifyUserEntity = this.userRepository.findByEmail(registerRequest.getEmail());
            if (verifyUserEntity.isPresent()) {
                response.put("error", "Email has been previously registered");
                return response;
            }
            registerRequest.setPassword(this.passwordEncoder.encode(registerRequest.getPassword()));
            UserEntity userEntity = this.userRepository.save(requestToEntity(registerRequest));
            return entityToResponse(userEntity);
        } catch (Exception exception) {
            response.put("exception", exception.getMessage());
            return response;
        }
    }
    private boolean verifyPassword(String sendPassword, String storedPassword) {
        return this.passwordEncoder.matches(sendPassword, storedPassword);
    }
    private UserEntity requestToEntity(RegisterRequest registerRequest) {
        return new UserEntity (
                registerRequest.getFirstname(),
                registerRequest.getLastname(),
                registerRequest.getEmail(),
                registerRequest.getPassword()
        );
    }
    private HashMap<String, String> entityToResponse(UserEntity userEntity) {
        HashMap<String, String> response = new HashMap<>();
        response.put("id", userEntity.getId().toString());
        response.put("firstname", userEntity.getFirstname());
        response.put("lastname", userEntity.getLastname());
        response.put("email", userEntity.getEmail());
        return response;
    }
}
