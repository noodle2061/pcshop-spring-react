package com.id.akn.controller;

import java.util.List;
import com.id.akn.model.User;
import com.id.akn.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
public class AdminUserController {
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(@RequestHeader("Authorization") String jwt) {
        System.out.println("/api/admin/users");
        List<User> user=userService.findAllUsers();
        return new ResponseEntity<>(user,HttpStatus.ACCEPTED);
    }
}
