package com.cursojava.curso.controllers;

import com.cursojava.curso.dao.UserDao;
import com.cursojava.curso.models.User;
import com.cursojava.curso.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "api/users", method = RequestMethod.GET)
    public List<User> getUsers(@RequestHeader(value = "Authorization") String token){
        if(!validateToken(token)){
            return  null;
        }
        return userDao.getUsers();
    }


    private boolean validateToken(String token){
        String userId = jwtUtil.getKey(token);
        return userId != null;
    }


    @RequestMapping(value = "api/users", method = RequestMethod.POST)
    public void createUser(@RequestBody User user){

        String insecurePassword = user.getPassword();


        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String securePassword = argon2.hash(10, 1024, 1,  insecurePassword);

        user.setPassword(securePassword);

        userDao.createUser(user);
    }


    @RequestMapping(value = "api/users/update/{id}", method = RequestMethod.PUT)
    public User updateUser(@PathVariable Long id){
        User user = new User();
        user.setName("alejandro");
        user.setLast_name("suarez");
        user.setEmail("alejandro@gmail.com");
        user.setPhone("3434807355");
        user.setPassword("password");
        return user;
    }

    @RequestMapping(value = "api/users/delete/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@RequestHeader(value = "Authorization") String token, @PathVariable Long id){
        if(validateToken(token)){
            return;
        }
        userDao.delete(id);
    }

}
