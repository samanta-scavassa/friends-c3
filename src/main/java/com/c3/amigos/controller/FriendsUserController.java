package com.c3.amigos.controller;

import com.c3.amigos.model.Friend;
import com.c3.amigos.model.User;
import com.c3.amigos.service.FriendsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/friends")
public class FriendsUserController {

    @Autowired
    private FriendsUserService service;

    @GetMapping("friends/{id}")
    public ResponseEntity<List<Friend>> getFriendsByUserId(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.findFriendsByUserId(id));
    }

    @GetMapping("user/{id}")
    public User findUserById(Long id){
        return service.findUserById(id);
    }

    @PostMapping("user/{userId}/friend/{friendId}")
    public ResponseEntity<Friend> addFriend(@RequestHeader("Authorization") String token,
                                            @PathVariable("userId") Long userId,
                                            @PathVariable("friendId") Long friendId){

        try {
            return ResponseEntity.ok()
                    .body(service.addFriend(token, userId, friendId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{userId}/{friendId}")
    public ResponseEntity<Friend> deleteFriend(@PathVariable("userId") Long userId,
                                               @PathVariable("friendId") Long friendId){
        return
                ResponseEntity.ok()
                        .body(service.deleteFriend(userId, friendId));
    }
}
