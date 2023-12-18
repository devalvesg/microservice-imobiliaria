package com.glimoveis.Imob_back.services;

import com.glimoveis.Imob_back.entities.User;
import com.glimoveis.Imob_back.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void newUser(User user){
        userRepository.save(user);
    }
}
