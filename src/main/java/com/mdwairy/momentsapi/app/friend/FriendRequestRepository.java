package com.mdwairy.momentsapi.app.friend;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface FriendRequestRepository extends CrudRepository<FriendRequest, Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE FriendRequest f SET " +
            "f.status=com.mdwairy.momentsapi.app.friend.FriendRequestStatus.ACCEPTED WHERE " +
            "f.receiverId = ?1 AND " +
            "f.senderId = ?2 AND " +
            "f.status=com.mdwairy.momentsapi.app.friend.FriendRequestStatus.PENDING")
    void accept(Long userId, Long acceptedUserId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM FriendRequest f WHERE " +
            "f.status = com.mdwairy.momentsapi.app.friend.FriendRequestStatus.PENDING AND" +
            "((f.receiverId = ?1 AND f.senderId = ?2) OR" +
            "(f.senderId = ?1 AND f.receiverId = ?2))")
    void reject(Long userId, Long rejectedUserId);

    @Query("SELECT f FROM FriendRequest f WHERE f.senderId = ?1 AND f.receiverId = ?2")
    Optional<FriendRequest> getRequest(Long senderId, Long receiverId);

}
