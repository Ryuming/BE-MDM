package com.ticket.user.controllers;

import com.ticket.user.DTOs.UserDTO;
import com.ticket.user.entities.PagedResponse;
import com.ticket.user.entities.User;
import com.ticket.user.repositories.UserRepository;
import com.ticket.user.services.DTOHandler;
import com.ticket.user.services.UserService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/user-service")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Environment env;

    @GetMapping
    public String home() {
        return "User Service running at port " + env.getProperty("local.server.port");
    }

    @PostMapping(value = "/user")
    public ResponseEntity<UserDTO> createUser(
        @RequestBody User user
    )
    {
        try {
            User _user = userRepository.save(user);
            return new ResponseEntity<>(new DTOHandler().convertUserToDTO(_user), HttpStatus.CREATED);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/user")
    public ResponseEntity<PagedResponse<UserDTO>> getAllUser()
    {
        try {
            PagedResponse<UserDTO> users = userService.userFilterHandler(
                "", 
                "", 
                "", 
                "", 
                0, 
                12);
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value="/user/search")
    public ResponseEntity<PagedResponse<UserDTO>> getUserByFilter(
        @RequestParam(value= "userId", required = false) String userId,
        @RequestParam(value= "phoneNumber", required = false)String phoneNumber,
        @RequestParam(value= "residentName", required = false)String residentName,
        @RequestParam(value= "residentId", required = false)String residentId,
        @RequestParam(value= "page", required = false)int page,
        @RequestParam(value= "size", required = false)int size
    )
    {
        try {
            PagedResponse<UserDTO> users = userService.userFilterHandler(
                userId, 
                phoneNumber, 
                residentName, 
                residentId, 
                page, 
                size);
            if (users.getTotalItems()==0)
            {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            else 
            {
                return new ResponseEntity<>(users, HttpStatus.OK);
            }
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping
//    public ResponseTemplateVO getUser(
//            @RequestHeader(value = "id") String userId,
//            @RequestHeader(value = "role") String role) {
//        return userService.getUserWithDepartment(userId);
//    }

    @GetMapping(value = "/user/verifyPhone")
    public boolean verifyPhoneExistedOrNot(
        @RequestParam(value = "phoneNumber") String phoneNumber
    )
    {
        try {
            if(userRepository.findByPhoneNumber(phoneNumber)!=null)
            return true;
            else return false;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }


    @GetMapping(value = "/secure")
    public String getSecure() {
        return "Secure endpoint available";
    }
}
