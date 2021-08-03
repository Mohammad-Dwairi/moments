package com.mdwairy.momentsapi.app.friend;

public interface FriendRequestService {
    void addFriend(Long receiverId);
    void accept(Long acceptedUserId);
    void reject(Long rejectedUserId);
}
