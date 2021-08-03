package com.mdwairy.momentsapi.app.friend;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/friend_request", produces = APPLICATION_JSON_VALUE)
public class FriendRequestController {

    private final FriendRequestService friendRequestService;

    @PostMapping("{id}")
    public void sendFriendRequest(@PathVariable("id") Long receiverId) {
        friendRequestService.addFriend(receiverId);
    }

    @PatchMapping("{id}")
    public void acceptFriendRequest(@PathVariable("id") Long acceptedUserId) {
        friendRequestService.accept(acceptedUserId);
    }

    @DeleteMapping("{id}")
    public void deleteFriendRequest(@PathVariable("id") Long rejectedUserId) {
        friendRequestService.reject(rejectedUserId);
    }
}
