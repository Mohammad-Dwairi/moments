package com.mdwairy.momentsapi.userinfo.friendship;

import com.mdwairy.momentsapi.exception.FriendshipException;
import com.mdwairy.momentsapi.exception.ResourceNotFoundException;
import com.mdwairy.momentsapi.userinfo.infoentity.InfoEntityVisibility;
import com.mdwairy.momentsapi.users.User;
import com.mdwairy.momentsapi.users.UserSecurity;
import com.mdwairy.momentsapi.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mdwairy.momentsapi.constant.AppExceptionMessage.*;
import static com.mdwairy.momentsapi.userinfo.friendship.FriendshipStatus.*;
import static com.mdwairy.momentsapi.userinfo.infoentity.InfoEntityVisibility.*;

@Service
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {

    private final UserSecurity userSecurity;
    private final FriendshipRepository friendshipRepository;
    private final UserService userService;

    @Override
    public List<Friendship> findAllFriends(@NotBlank String username) {

        String authUsername = userSecurity.getUserPrinciple().getUsername();
        InfoEntityVisibility visibility = userService.findByUsername(username).getUserInfo().getFriendsVisibility();

        List<Friendship> friends = friendshipRepository.findAllFriends(username);
        Set<String> friendsUsernames = extractFriendsUsernames(username, friends);
        List<User> friendsAsUsers = userService.findAllByUsernameIn(friendsUsernames);

        if (authUsername.equals(username)) {
            return friends;
        }
        if (visibility == PUBLIC) {
            return filterFriendsByVisibility(friendsAsUsers, friends);
        }
        if (visibility == FRIENDS) {
            if (checkIfFriends(authUsername, username)) {
                return filterFriendsByVisibility(friendsAsUsers, friends);
            }
        }
        return Collections.emptyList();
    }

    @Override
    public List<Friendship> findAllSentAndPendingFriendships(String username) {
        return friendshipRepository.findAllSentAndPendingFriendships(username);
    }

    @Override
    public List<Friendship> findAllReceivedAndPendingFriendships(String username) {
        return friendshipRepository.findAllReceivedAndPendingRequests(username);
    }

    @Override
    public Friendship createNewFriendship(String username, String receiverUsername) {
        receiverUsername = userService.findByUsername(receiverUsername).getUsername(); // throws exception if user not found
        preFriendshipCreation(username, receiverUsername);
        Friendship friendship = new Friendship(username, receiverUsername, PENDING);
        return friendshipRepository.save(friendship);
    }

    @Override
    public Friendship acceptFriendship(String username, String senderUsername) {
        Friendship friendship = getFriendshipOrdered(senderUsername, username);
        preFriendshipAccept(friendship);
        friendship.setStatus(ACCEPTED);
        return friendshipRepository.save(friendship);
    }

    @Override
    public void deleteFriendship(String username, String friendUsername) {
        Friendship friendship = getFriendshipNoOrder(username, friendUsername);
        friendshipRepository.delete(friendship);
    }

    public boolean checkIfFriends(String username1, String username2) {
        try {
            Friendship friendship = getFriendshipNoOrder(username1, username2);
            return friendship.getStatus() == ACCEPTED;
        } catch (ResourceNotFoundException e) {
            return false; // TODO: TEST
        }
    }

    private void preFriendshipCreation(String username, String friendUsername) {
        if (username.equals(friendUsername)) {
            throw new FriendshipException(INVALID_FRIEND_REQUEST);
        }
        Optional<Friendship> friendshipOptional = friendshipRepository.findByIdNoOrder(username, friendUsername);
        if (friendshipOptional.isPresent()) {
            Friendship friendship = friendshipOptional.get();
            if (friendship.getStatus() == ACCEPTED) {
                throw new FriendshipException(ALREADY_FRIENDS);
            }
            if (friendship.getStatus() == PENDING) {
                throw new FriendshipException(PENDING_FRIEND_REQUEST);
            }
            if (friendship.getStatus() == BLOCKED) {
                throw new ResourceNotFoundException(RESOURCE_NOT_FOUND);
            }
        }
    }

    private void preFriendshipAccept(Friendship friendship) {
        if (friendship.getStatus() == ACCEPTED) {
            throw new FriendshipException(ALREADY_FRIENDS);
        }
        if (friendship.getStatus() == BLOCKED) {
            throw new ResourceNotFoundException(RESOURCE_NOT_FOUND);
        }
    }

    private List<Friendship> filterFriendsByVisibility(List<User> friendsAsUsers, List<Friendship> friends) {
        for (User user : friendsAsUsers) {
            InfoEntityVisibility visibility = user.getUserInfo().getFriendsVisibility();
            if (visibility == FRIENDS) {
                String authUsername = userSecurity.getUserPrinciple().getUsername();
                boolean isFriend = checkIfFriends(user.getUsername(), authUsername);
                if (!isFriend) {
                    friends.removeIf(friendship -> isUsernameInFriendship(user.getUsername(), friendship));
                }
            } else if (visibility == PRIVATE) {
                friends.removeIf(friendship -> isUsernameInFriendship(user.getUsername(), friendship));
            }
        }
        return friends;
    }

    private Set<String> extractFriendsUsernames(String username, List<Friendship> friends) {
        return friends.stream().map(friend -> {
            if (friend.getUsername1().equals(username))
                return friend.getUsername2();
            else return friend.getUsername1();
        }).collect(Collectors.toSet());
    }

    private boolean isUsernameInFriendship(String username, Friendship friendship) {
        return friendship.getUsername1().equals(username) || friendship.getUsername2().equals(username);
    }

    private Friendship getFriendshipOrdered(String senderUsername, String receiverUsername) {
        Optional<Friendship> friendshipOptional = friendshipRepository.findById(new FriendshipId(senderUsername, receiverUsername));
        return friendshipOptional.orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));
    }

    private Friendship getFriendshipNoOrder(String username1, String username2) {
        Optional<Friendship> friendshipOptional = friendshipRepository.findByIdNoOrder(username1, username2);
        return friendshipOptional.orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));
    }
}
