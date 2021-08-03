package com.mdwairy.momentsapi.app.friend;

import com.mdwairy.momentsapi.exception.FriendRequestException;
import com.mdwairy.momentsapi.users.User;
import com.mdwairy.momentsapi.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static com.mdwairy.momentsapi.app.friend.FriendRequestStatus.ACCEPTED;
import static com.mdwairy.momentsapi.app.friend.FriendRequestStatus.PENDING;

@Service
@RequiredArgsConstructor
public class FriendRequestServiceJPA implements FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;
    private final UserService userService;

    @Override
    public void addFriend(Long receiverId) {
        Long senderUserId = userService.getUserFromSecurityContext().getId();
        userService.findById(receiverId); // Throws an exception if the user is not found.
        checkSenderAndReceiverId(senderUserId, receiverId);
        checkFriendshipExists(senderUserId, receiverId); // Throws an exception if they are already friends
        FriendRequest friendRequest = new FriendRequest(senderUserId, receiverId, PENDING);
        friendRequestRepository.save(friendRequest);
    }

    @Override
    public void accept(Long acceptedUserId) {
        Long acceptorUserId = userService.getUserFromSecurityContext().getId();
        userService.findById(acceptedUserId);
        Optional<FriendRequest> friendRequestOptional = friendRequestRepository.getRequest(acceptedUserId, acceptorUserId);
        if (friendRequestOptional.isPresent()) {
            FriendRequest friendRequest = friendRequestOptional.get();
            if (friendRequest.getStatus() == ACCEPTED) {
                throw new FriendRequestException("Already friends");
            }
            if (friendRequest.getStatus() == PENDING) {
                friendRequestRepository.accept(acceptorUserId, acceptedUserId);
            }
        }
        else {
            throw new FriendRequestException("Friend Request not found");
        }
    }

    @Override
    public void reject(Long rejectedUserId) {
        User rejectorUser = userService.getUserFromSecurityContext();
        User rejectedUser = userService.findById(rejectedUserId);
        Optional<FriendRequest> friendRequestOptional = friendRequestRepository.getRequest(rejectedUser.getId(), rejectorUser.getId());
        if (friendRequestOptional.isPresent()) {
            FriendRequest friendRequest = friendRequestOptional.get();
            if (friendRequest.getStatus() == ACCEPTED) {
                throw new FriendRequestException("Already accepted this friend request");
            }
        }
        friendRequestRepository.reject(rejectorUser.getId(), rejectedUser.getId());
    }

    private void checkFriendshipExists(Long senderId, Long receiverId) {
        Optional<FriendRequest> friendRequestOptional = friendRequestRepository.getRequest(senderId, receiverId);
        Optional<FriendRequest> inverseFriendRequestOptional = friendRequestRepository.getRequest(receiverId, senderId);
        friendRequestOptional.ifPresent(this::alreadyExistsFriendRequestHandler);
        inverseFriendRequestOptional.ifPresent(this::alreadyExistsFriendRequestHandler);
    }

    private void alreadyExistsFriendRequestHandler(FriendRequest friendRequest) {
        if (friendRequest.getStatus() == ACCEPTED) {
            throw new FriendRequestException("Already Friends");
        } else {
            throw new FriendRequestException("Friend Request is pending");
        }
    }

    private void checkSenderAndReceiverId(Long senderId, Long receiverId) {
        if (Objects.equals(senderId, receiverId)) {
            throw new FriendRequestException("You can not send a friend request to your self!");
        }
    }

}
