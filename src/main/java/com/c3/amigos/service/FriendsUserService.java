package com.c3.amigos.service;

import com.c3.amigos.client.UserClient;
import com.c3.amigos.dto.UserDTO;
import com.c3.amigos.exceptions.AddFriendException;
import com.c3.amigos.model.Friend;
import com.c3.amigos.model.User;
import com.c3.amigos.repository.FriendRepository;
import com.c3.amigos.repository.FriendsUserRepository;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FriendsUserService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private FriendsUserRepository repository;

    @Autowired
    private FriendRepository friendRepository;

    public List<Friend> findFriendsByUserId(Long id){

        Optional<User> user = repository.findById(id.toString());
        List<Friend> friends = new ArrayList<>();

        if(!user.isPresent()){
            return friends;
        }

        if(!CollectionUtils.isEmpty(user.get().getFriends())){
            friends = user.get().getFriends();
        }

        return friends;
    }

    public User findUserById(Long id){

        Optional<User> user = repository.findById(id.toString());
        return user.orElse(null);
    }

    public Friend addFriend(String token, Long userId, Long friendId){

        Optional<Friend> buscaAmigo = friendRepository.findById(friendId.toString());
        UserDTO userFriend;
        Friend friend = new Friend();
        User user = new User();

        try {
            Optional<User> buscaUsuario = repository.findById(userId.toString());
            if (!buscaUsuario.isPresent()) {
                UserDTO userOriginal = userClient.getUserById(token, userId);
                user = new User(userOriginal);
                List<Friend> amigos = user.getFriends();
                if (CollectionUtils.isEmpty(amigos)) {
                    amigos = new ArrayList<>();
                }
                repository.save(user);
                return getFriend(token, friendId, buscaAmigo, user, amigos);
            } else {
                User u = buscaUsuario.get();
                List<Friend> amigos = u.getFriends();
                if (CollectionUtils.isEmpty(amigos)) {
                    amigos = new ArrayList<>();
                }
                return getFriend(token, friendId, buscaAmigo, u, amigos);
            }
        } catch (Exception e){
            throw new AddFriendException("Falha ao adicoonar usuário, tente novamente", e.getCause());
        }

    }

    private Friend getFriend(String token, Long friendId, Optional<Friend> buscaAmigo, User user, List<Friend> amigos) {
        UserDTO userFriend;
        Friend friend;
        if(!buscaAmigo.isPresent()) {
            userFriend = userClient.getUserById(token, friendId);
            if(Objects.isNull(userFriend)) {
                throw new AddFriendException("Usuário não encontrado");
            }
            friend = new Friend(userFriend);
            friendRepository.save(friend);
            amigos.add(friend);
            user.setFriends(amigos);
            repository.save(user);
            return friend;

        } else {
            friend = buscaAmigo.get();
            if (!amigos.contains(friend)) {
                amigos.add(friend);
                user.setFriends(amigos);
                repository.save(user);
            }
            return friend;
        }
    }

    public Friend deleteFriend(Long userId, Long friendId){

        Friend friend = friendRepository.findById(friendId.toString()).get();
        User user = findUserById(userId);
        List<Friend> amigos = user.getFriends();
        amigos.remove(friend);
        user.setFriends(amigos);
        repository.save(user);
        return friend;
    }

}
