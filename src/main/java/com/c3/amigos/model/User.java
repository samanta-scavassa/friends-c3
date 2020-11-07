package com.c3.amigos.model;

import com.c3.amigos.dto.UserDTO;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
public class User {

    @Id
    private Long id;

    private List<Friend> friends;

    public User() {

    }

    public User(UserDTO userDTO) {
        id = userDTO.getId();
    }

}
