package br.com.github.williiansilva51.todolist.controller;

import br.com.github.williiansilva51.todolist.dto.user.CreateUserDTO;
import br.com.github.williiansilva51.todolist.dto.user.LoginResponseDTO;
import br.com.github.williiansilva51.todolist.dto.user.LoginUserDTO;
import br.com.github.williiansilva51.todolist.dto.user.UserDTO;
import br.com.github.williiansilva51.todolist.entity.User;
import br.com.github.williiansilva51.todolist.service.CustomUserDetailsService;
import br.com.github.williiansilva51.todolist.service.TokenService;
import br.com.github.williiansilva51.todolist.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
        User createdUser = userService.createUser(createUserDTO);
        UserDTO userResponse = new UserDTO(createdUser.getUsername(), createdUser.getEmail());

        return ResponseEntity.created(URI.create("/users/" + createdUser.getId())).body(userResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@Valid @RequestBody LoginUserDTO loginUserDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginUserDTO.username(), loginUserDTO.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        var user = (User) auth.getPrincipal();
        var token = tokenService.generateToken(user);
        
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
