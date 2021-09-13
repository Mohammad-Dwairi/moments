package com.mdwairy.momentsapi.userinfo.friendship;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FriendshipRepository extends CrudRepository<Friendship, FriendshipId> {

    @Query("SELECT f FROM Friendship f WHERE f.username1 = ?1 OR f.username2 = ?1")
    List<Friendship> findAll(String username);

    @Query("SELECT f FROM Friendship f WHERE f.username1 = ?1 AND " +
            "f.status = com.mdwairy.momentsapi.userinfo.friendship.FriendshipStatus.PENDING")
    List<Friendship> findAllSentAndPendingFriendships(String username);

    @Query("SELECT f FROM Friendship f WHERE f.username2 = ?1 AND " +
            "f.status = com.mdwairy.momentsapi.userinfo.friendship.FriendshipStatus.PENDING")
    List<Friendship> findAllReceivedAndPendingRequests(String username);

    @Query("SELECT f FROM Friendship f WHERE (f.username1 = ?1 OR f.username2 = ?1) AND f.status = com.mdwairy.momentsapi.userinfo.friendship.FriendshipStatus.ACCEPTED")
    List<Friendship> findAllFriends(String username);
}
