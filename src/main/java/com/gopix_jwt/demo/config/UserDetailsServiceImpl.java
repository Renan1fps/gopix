package com.gopix_jwt.demo.config;

import com.gopix_jwt.demo.entity.User;
import com.gopix_jwt.demo.errors.BadRequestException;
import com.gopix_jwt.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new BadRequestException("Usuário não encontrado."));
        return new UserDetailsImpl(user);
    }

}