package com.ticket.user.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.ticket.user.DTOs.UserDTO;
import com.ticket.user.entities.User;

public class DTOHandler {

    @Autowired
    private DTOHandler _DTOHandler;

    public UserDTO convertUserToDTO(User user)
    {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setResidentName(user.getResidentName());
        userDTO.setResidentId(user.getResidentId());
        userDTO.setRole(user.getRole());

        return userDTO;
    }

    public List<UserDTO> convertUserToDTO(List<User> users)
    {
        List<UserDTO> userDTOs = users.stream().map(user->{
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setResidentName(user.getResidentName());
        userDTO.setResidentId(user.getResidentId());
        userDTO.setRole(user.getRole());
        return userDTO;

        }).collect(Collectors.toList());

        return userDTOs;
    }
}
