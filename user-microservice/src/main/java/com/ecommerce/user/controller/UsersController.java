package com.ecommerce.user.controller;


import com.ecommerce.user.entities.Users;
import com.ecommerce.user.service.UsersService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
@RefreshScope
public class UsersController {

    private final UsersService usersService;
    private final String refreshScopeTestValue;
    public UsersController(UsersService usersService,
                           @Value("${embarkx.check.refreshScope.value}") String refreshScopeTestValue) {
        this.usersService = usersService;
        this.refreshScopeTestValue = refreshScopeTestValue;
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<Users>> getAllUserDetails(){
        System.out.println("To Enable to refresh Scope : run -> " +
                "POST -> https://localhost:<port_no>/actuator/refresh");
        System.out.println("refreshScopeTestValue:: " + refreshScopeTestValue);
        return new ResponseEntity<>(usersService.getAllUsersDetails(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Users>> findUserById(@PathVariable String id){
        return new ResponseEntity<>(usersService.findUserById(id), HttpStatus.OK);
    }

    @PostMapping("/createUserDetails")
    public ResponseEntity<Users> createUserDetails(@RequestBody Users users){
        return new ResponseEntity<>(usersService.createUserDetails(users), HttpStatus.CREATED);

    }
    @PutMapping("/{id}/updateUserDetails")
    public ResponseEntity<Users> updateUserDetails(
            @PathVariable String id, @RequestBody Users users){
        return usersService.updateUserDetails(id, users).map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }
}
