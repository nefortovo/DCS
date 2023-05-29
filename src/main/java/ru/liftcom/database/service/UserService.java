package ru.liftcom.database.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liftcom.database.entity.CustomUser;
import ru.liftcom.database.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public CustomUser findByName(String name){
        CustomUser customUser = userRepository.findByName(name);
        return customUser;
    }

    public List<CustomUser> getAll(){
        List<CustomUser> customUsers = userRepository.findAll();
        return customUsers;
    }

    public void saveUser(CustomUser customUser){
        userRepository.save(customUser);
    }
}
