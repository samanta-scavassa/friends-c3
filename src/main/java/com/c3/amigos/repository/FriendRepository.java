package com.c3.amigos.repository;

import com.c3.amigos.model.Friend;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends MongoRepository<Friend, Long> {
}
