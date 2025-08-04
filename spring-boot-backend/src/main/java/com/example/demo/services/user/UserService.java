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
    public UserDto authenticateUser(String id, String password) {
        if(StringUtils.isBlank(id) || StringUtils.isBlank(password)){
            throw new ResourceNotFoundException("Id or password is empty");
        }

        Users user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User does not exist"));

        if (passwordEncoder.matches(password, user.getPassword())){
            return modelMapper.map(user, UserDto.class);
        }

        throw new IncorrectPasswordException("Password is incorrect");
    }

    @Override
    public UserDto saveUser(String id, String password) {
        if (StringUtils.isBlank(id) || StringUtils.isBlank(password)){
            throw new ResourceNotFoundException("Id or Password is missing");
        }
        Users user = new Users(id);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        return modelMapper.map(user, UserDto.class);
    }
}
