package com.mdwairy.momentsapi.userinfo.friendship;

import com.mdwairy.momentsapi.users.UserSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.mdwairy.momentsapi.userinfo.friendship.FriendshipStatus.ACCEPTED;
import static com.mdwairy.momentsapi.userinfo.friendship.FriendshipStatus.PENDING;

@Service
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {

    private final UserSecurity userSecurity;
    private final FriendshipRepository friendshipRepository;

    @Override
    public List<Friendship> findAll() {
        String username = userSecurity.getUserPrinciple().getUsername();
        return friendshipRepository.findAll(username);
    }

    @Override
    public List<Friendship> findAllSentAndPendingFriendships() {
        String username = userSecurity.getUserPrinciple().getUsername();
        return friendshipRepository.findAllSentAndPendingFriendships(username);
    }

    @Override
    public List<Friendship> findAllReceivedAndPendingFriendships() {
        String username = userSecurity.getUserPrinciple().getUsername();
        return friendshipRepository.findAllReceivedAndPendingRequests(username);
    }

    @Override
    public List<Friendship> findAllFriends() {
        String username = userSecurity.getUserPrinciple().getUsername();
        return friendshipRepository.findAllFriends(username);
    }

    @Override
    public Friendship createNewFriendship(String receiverUsername) {
        String username = userSecurity.getUserPrinciple().getUsername();
        Friendship friendship = new Friendship(username, receiverUsername, PENDING);
        return friendshipRepository.save(friendship);
    }

    @Override
    public Friendship acceptFriendship(String senderUsername) {
        String username = userSecurity.getUserPrinciple().getUsername();
        Optional<Friendship> friendshipOptional = friendshipRepository.findById(new FriendshipId(senderUsername, username));
        if (friendshipOptional.isPresent()) {
            Friendship friendship = friendshipOptional.get();
            friendship.setStatus(ACCEPTED);
            return friendshipRepository.save(friendship);
        }
        return null;
    }

    @Override
    public void rejectFriendship(String senderUsername) {
        String username = userSecurity.getUserPrinciple().getUsername();
        Optional<Friendship> friendshipOptional = friendshipRepository.findById(new FriendshipId(senderUsername, username));
        friendshipOptional.ifPresent(friendshipRepository::delete);
    }
}
