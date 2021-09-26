package com.mdwairy.momentsapi.friendship;

import org.springframework.security.access.prepost.PreAuthorize;

import javax.validation.constraints.NotBlank;
import java.util.List;

public interface FriendshipService {

    List<Friendship> findAllFriends(String username);

    @PreAuthorize("@userSecurity.checkOwnership(#username)")
    List<Friendship> findAllSentAndPendingFriendships(@NotBlank String username);

    @PreAuthorize("@userSecurity.checkOwnership(#username)")
    List<Friendship> findAllReceivedAndPendingFriendships(@NotBlank String username);

    @PreAuthorize("@userSecurity.checkOwnership(#username)")
    Friendship createNewFriendship(@NotBlank String username, @NotBlank String receiverUsername);

    @PreAuthorize("@userSecurity.checkOwnership(#username)")
    Friendship acceptFriendship(@NotBlank String username, @NotBlank String senderUsername);

    @PreAuthorize("@userSecurity.checkOwnership(#username)")
    void deleteFriendship(@NotBlank String username, @NotBlank String friendUsername);

    boolean checkIfFriends(String username1, String username2);
}
