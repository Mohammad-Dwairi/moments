package com.mdwairy.momentsapi.friendship;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends CrudRepository<Friendship, FriendshipId> {

    @Query("SELECT f FROM Friendship f WHERE f.username1 = ?1 AND " +
            "f.status = com.mdwairy.momentsapi.friendship.FriendshipStatus.PENDING")
    List<Friendship> findAllSentAndPendingFriendships(String username);

    @Query("SELECT f FROM Friendship f WHERE f.username2 = ?1 AND " +
            "f.status = com.mdwairy.momentsapi.friendship.FriendshipStatus.PENDING")
    List<Friendship> findAllReceivedAndPendingRequests(String username);

    @Query("SELECT f FROM Friendship f WHERE (f.username1 = ?1 OR f.username2 = ?1) AND f.status = com.mdwairy.momentsapi.friendship.FriendshipStatus.ACCEPTED")
    List<Friendship> findAllFriends(String username);

    @Query("SELECT f FROM Friendship f WHERE (f.username1 = ?1 AND f.username2 = ?2) OR (f.username1 = ?2 AND f.username2 = ?1)")
    Optional<Friendship> findByIdNoOrder(String username1, String username2);
}
