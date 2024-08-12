package com.eldar.utils.mappers;

import com.eldar.api.models.requests.UserRequest;
import com.eldar.api.models.responses.UserResponse;
import com.eldar.persistence.entities.UserEntity;

public class UserMapper {

    /**
     * Method to map a UserRequest to a UserEntity
     * @param request
     * @return UserEntity
     */
    public static UserEntity mapToEntity(UserRequest request){
        return UserEntity.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .telephone(request.getTelephone())
                .address(request.getAddress())
                .password(request.getPassword())
                .build();
    }

    /**
     * Method to map a UserEntity to a UserResponse
     * @param user
     * @return UserResponse
     */
    public static UserResponse mapToDto(UserEntity user){
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .telephone(user.getTelephone())
                .address(user.getAddress())
                .roles(user.getRoles())
                .build();
    }
}
