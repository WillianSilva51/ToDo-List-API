package br.com.github.williiansilva51.todolist.service;

import br.com.github.williiansilva51.todolist.dto.user.CreateUserDTO;
import br.com.github.williiansilva51.todolist.entity.User;
import br.com.github.williiansilva51.todolist.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Service;

@Service
@Builder
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(CreateUserDTO createUserDTO) {
        if (userRepository.findByUsername(createUserDTO.username()).isPresent() || userRepository.findByEmail(createUserDTO.email()).isPresent()) {
            throw new IllegalArgumentException("Username or Email already exists");
        }

        User newUser = User.builder()
                .username(createUserDTO.username())
                .email(createUserDTO.email())
                .password(createUserDTO.password())
                .build();

        return userRepository.save(newUser);
    }

}
