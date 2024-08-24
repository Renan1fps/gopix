package com.gopix_jwt.demo.service;

import com.gopix_jwt.demo.config.JwtTokenService;
import com.gopix_jwt.demo.config.SecurityConfiguration;
import com.gopix_jwt.demo.config.UserDetailsImpl;
import com.gopix_jwt.demo.dto.CreateUserDto;
import com.gopix_jwt.demo.dto.LoginUserDto;
import com.gopix_jwt.demo.dto.RecoveryJwtTokenDto;
import com.gopix_jwt.demo.entity.Role;
import com.gopix_jwt.demo.entity.User;
import com.gopix_jwt.demo.errors.BadRequestException;
import com.gopix_jwt.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    public RecoveryJwtTokenDto authenticateUser(LoginUserDto loginUserDto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));
    }

    public void createUser(CreateUserDto createUserDto) {
        Optional<User> user = this.userRepository.findByEmail(createUserDto.email());

        if (user.isPresent()) {
            throw new BadRequestException("Usuário já cadastrado!");
        }
        User newUser = new User();
        newUser.setEmail(createUserDto.email());
        newUser.setPassword(securityConfiguration.passwordEncoder().encode(createUserDto.password()));
        newUser.setDocument(createUserDto.document());
        Role role = new Role();
        role.setName(createUserDto.role());
        newUser.setRoles(List.of(role));
        userRepository.save(newUser);
    }
}