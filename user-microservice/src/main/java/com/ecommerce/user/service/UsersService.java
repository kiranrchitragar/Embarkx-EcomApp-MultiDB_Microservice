package com.ecommerce.user.service;


import com.ecommerce.user.entities.Users;
import com.ecommerce.user.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<Users> getAllUsersDetails() {
        return usersRepository.findAll();
    }

    public Users createUserDetails(Users users) {
        return usersRepository.save(users);
    }

    public Optional<Users> findUserById(String id) {
        return usersRepository.findById(id);
    }

    public Optional<Users> updateUserDetails(String id, Users reqUsers) {
        System.out.println("reqUserDetails:: " + reqUsers);
       return usersRepository.findById(id).map(exis->{
            reqUsers.setId(exis.getId());
           reqUsers.setCreatedAt(exis.getCreatedAt());
           reqUsers.setCreatedBy(exis.getCreatedBy());
            Users usersResponse =  usersRepository.save(reqUsers);
            System.out.println("userDetailsResponse:: " + usersResponse);
            return usersResponse;
        });
    }
}
