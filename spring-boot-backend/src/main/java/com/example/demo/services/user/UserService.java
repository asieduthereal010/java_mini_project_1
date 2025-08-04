package com.example.demo.services.user;

import com.example.demo.dtos.StudentDto;
import com.example.demo.dtos.UserDto;
import com.example.demo.exceptions.*;
import com.example.demo.models.Students;
import com.example.demo.models.Users;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public StudentDto authenticateUser(String id, String password) {
        if(StringUtils.isBlank(id) || StringUtils.isBlank(password)){
            throw new ResourceNotFoundException("Id or password is empty");
        }

        Students student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("The student with ID " + id + " does not exist"));

        Users user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User does not exist"));

        if (passwordEncoder.matches(password, user.getPassword())){
            return modelMapper.map(student, StudentDto.class);
        }

        throw new IncorrectPasswordException("Password is incorrect");
    }

    @Override
    public StudentDto saveUser(String id, String password, String name, LocalDate dob, String email) {
        if (StringUtils.isBlank(id) || StringUtils.isBlank(password)){
            throw new ResourceNotFoundException("Id or Password is missing");
        }

        boolean studentExists = studentRepository.existsById(id);
        if (studentExists){
            throw new AlreadyExistsException("Student with id " + id + " already exists");
        }

        Users user = new Users(id);
        user.setPassword(passwordEncoder.encode(password));
        Students student = new Students(id, name, dob, email);
        studentRepository.save(student);
        userRepository.save(user);

        return modelMapper.map(student, StudentDto.class);
    }
}
