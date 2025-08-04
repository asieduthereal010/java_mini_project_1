package com.example.demo.services.user;

import com.example.demo.dtos.UserDto;
import com.example.demo.exceptions.AlreadyExistsException;
import com.example.demo.exceptions.IncorrectPasswordException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.models.Users;
import com.example.demo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public UserDto authenticateUser(String email, String password) {
        if(StringUtils.isBlank(email) || StringUtils.isBlank(password)){
            throw new ResourceNotFoundException("Username or password is empty");
        }

        Users user = userRepository.findByEmail(email);

        if (user == null){
            throw new UserNotFoundException("User does not exists");
        }

        if (passwordEncoder.matches(password, user.getPassword())){
            return modelMapper.map(user, UserDto.class);
        }

        throw new IncorrectPasswordException("Password is incorrect");
    }

    @Override
    public Object login(String email, String password) {
        UserDto authenticatedUser = authenticateUser(email, password);
        
        // Return a login response object with user info and token (mock)
        return new Object() {
            public final String token = "mock-jwt-token-" + System.currentTimeMillis();
            public final UserDto user = authenticatedUser;
            public final String message = "Login successful";
        };
    }

    @Override
    public UserDto saveUser(String username, String email, String password) {
        boolean usernameTaken = userRepository.existsByUsername(username);
        if (usernameTaken){
            throw new AlreadyExistsException("Username has already been taken!");
        }
        boolean emailTaken = userRepository.existsByEmail(email);
        if (emailTaken){
            throw new AlreadyExistsException("Email has already been taken!");
        }

        if (StringUtils.isBlank(username) || StringUtils.isBlank(email) || StringUtils.isBlank(password)){
            throw new ResourceNotFoundException("Username or Email or Password is missing");
        }

        Users user = new Users(username, email);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        return modelMapper.map(user, UserDto.class);
    }
}
