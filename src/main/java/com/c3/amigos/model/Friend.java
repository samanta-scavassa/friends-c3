package com.c3.amigos.model;

import com.c3.amigos.dto.UserDTO;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Friend {

    @Id
    private Long id;
    private String name;
    private String img;


    public Friend() {

    }

    public Friend(UserDTO userDto) {
        id = userDto.getId();
        name = userDto.getNome() + userDto.getSobrenome();
        img = userDto.getPerfilImg();

    }

}
