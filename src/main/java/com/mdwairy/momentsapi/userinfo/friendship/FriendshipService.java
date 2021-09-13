package com.mdwairy.momentsapi.userinfo.friendship;

import java.util.List;

public interface FriendshipService {
    List<Friendship> findAll();
    List<Friendship> findAllSentAndPendingFriendships();
    List<Friendship> findAllReceivedAndPendingFriendships();
    List<Friendship> findAllFriends();
    Friendship createNewFriendship(String receiverUsername);
    Friendship acceptFriendship(String senderUsername);
    void rejectFriendship(String senderUsername);
}
