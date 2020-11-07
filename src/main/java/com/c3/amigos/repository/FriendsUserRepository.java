package com.c3.amigos.repository;

import com.c3.amigos.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendsUserRepository extends MongoRepository<User, Long> {

}
