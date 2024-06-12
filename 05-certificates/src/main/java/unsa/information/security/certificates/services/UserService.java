package unsa.information.security.certificates.services;

import org.springframework.stereotype.Service;

import unsa.information.security.certificates.dtos.UserResponse;
import unsa.information.security.certificates.persistence.entities.UserEntity;
import unsa.information.security.certificates.persistence.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public List<UserResponse> getAllUsers() {
        return this.userRepository.findAll().stream().map(UserService::entityToResponse).toList();
    }
    private static UserResponse entityToResponse(UserEntity userEntity) {
        return new UserResponse (
                userEntity.getFirstname(),
                userEntity.getLastname(),
                userEntity.getEmail()
        );
    }
}
