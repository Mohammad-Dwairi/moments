package com.mdwairy.momentsapi.app.friend;

import com.mdwairy.momentsapi.exception.FriendRequestException;
import com.mdwairy.momentsapi.users.UserServiceJPA;
import com.mdwairy.momentsapi.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static com.mdwairy.momentsapi.app.friend.FriendRequestStatus.ACCEPTED;
import static com.mdwairy.momentsapi.app.friend.FriendRequestStatus.PENDING;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
@ContextConfiguration
class FriendRequestServiceJPATest {

    FriendRequestService friendRequestService;

    @Mock
    FriendRequestRepository friendRequestRepository;

    @Mock
    UserServiceJPA userService;

    @BeforeEach
    void setUp() {
        friendRequestService = new FriendRequestServiceJPA(friendRequestRepository, userService);
    }

    @Test
    void addFriend() {

        // given
        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);

        //when
        when(userService.getUserFromSecurityContext()).thenReturn(user1);
        when(userService.findById(anyLong())).thenReturn(user2);
        friendRequestService.addFriend(user2.getId());

        // then
        verify(friendRequestRepository, times(1)).save(any());
    }

    @Test
    void selfFriendRequest() {

        // given
        User user = new User();
        user.setId(1L);

        //when
        when(userService.getUserFromSecurityContext()).thenReturn(user);
        when(userService.findById(anyLong())).thenReturn(user);

        // then
        assertThrows(FriendRequestException.class, () -> friendRequestService.addFriend(user.getId()));
    }

    @Test
    void sendRequestWhenAlreadyFriends() {

        // given
        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSenderId(user1.getId());
        friendRequest.setReceiverId(user2.getId());
        friendRequest.setStatus(ACCEPTED);

        //when
        when(userService.getUserFromSecurityContext()).thenReturn(user1);
        when(userService.findById(anyLong())).thenReturn(user2);
        when(friendRequestRepository.getRequest(user1.getId(), user2.getId())).thenReturn(Optional.of(friendRequest));

        // then
        assertThrows(FriendRequestException.class, () -> friendRequestService.addFriend(user2.getId()));
    }

    @Test
    void sendRequestWhenRequestIsPending() {

        // given
        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSenderId(user1.getId());
        friendRequest.setReceiverId(user2.getId());
        friendRequest.setStatus(PENDING);

        //when
        when(userService.getUserFromSecurityContext()).thenReturn(user1);
        when(userService.findById(anyLong())).thenReturn(user2);
        when(friendRequestRepository.getRequest(user1.getId(), user2.getId())).thenReturn(Optional.of(friendRequest));

        // then
        assertThrows(FriendRequestException.class, () -> friendRequestService.addFriend(user2.getId()));
    }


    @Test
    void accept() {
        // given
        User acceptor = new User();
        acceptor.setId(1L);

        User accepted = new User();
        accepted.setId(2L);

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSenderId(accepted.getId());
        friendRequest.setReceiverId(acceptor.getId());
        friendRequest.setStatus(PENDING);

        // when
        when(userService.getUserFromSecurityContext()).thenReturn(acceptor);
        when(userService.findById(accepted.getId())).thenReturn(accepted);
        when(friendRequestRepository.getRequest(accepted.getId(), acceptor.getId())).thenReturn(Optional.of(friendRequest));
        friendRequestService.accept(accepted.getId());

        // then
        verify(friendRequestRepository, times(1)).accept(acceptor.getId(), accepted.getId());
    }

    @Test
    void acceptNotExistingRequest() {
        // given
        User acceptor = new User();
        acceptor.setId(1L);

        User accepted = new User();
        accepted.setId(2L);

        // when
        when(userService.getUserFromSecurityContext()).thenReturn(acceptor);
        when(userService.findById(accepted.getId())).thenReturn(accepted);
        when(friendRequestRepository.getRequest(accepted.getId(), acceptor.getId())).thenReturn(Optional.empty());

        // then
        assertThrows(FriendRequestException.class, () -> friendRequestService.accept(accepted.getId()));
        verify(friendRequestRepository, times(1)).getRequest(anyLong(), anyLong());
        verify(friendRequestRepository, times(0)).accept(anyLong(), anyLong());
    }

    @Test
    void acceptAlreadyAcceptedRequest() {
        // given
        User acceptor = new User();
        acceptor.setId(1L);

        User accepted = new User();
        accepted.setId(2L);

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSenderId(accepted.getId());
        friendRequest.setReceiverId(acceptor.getId());
        friendRequest.setStatus(ACCEPTED);

        // when
        when(userService.getUserFromSecurityContext()).thenReturn(acceptor);
        when(userService.findById(accepted.getId())).thenReturn(accepted);
        when(friendRequestRepository.getRequest(accepted.getId(), acceptor.getId())).thenReturn(Optional.of(friendRequest));

        // then
        assertThrows(FriendRequestException.class, () -> friendRequestService.accept(accepted.getId()));
        verify(friendRequestRepository, times(1)).getRequest(anyLong(), anyLong());
        verify(friendRequestRepository, times(0)).accept(anyLong(), anyLong());
    }

    @Test
    void reject() {
        // given
        User rejector = new User();
        rejector.setId(1L);

        User rejected = new User();
        rejected.setId(2L);

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSenderId(rejected.getId());
        friendRequest.setReceiverId(rejector.getId());
        friendRequest.setStatus(PENDING);

        // when
        when(userService.getUserFromSecurityContext()).thenReturn(rejector);
        when(userService.findById(rejected.getId())).thenReturn(rejected);
        friendRequestService.reject(rejected.getId());

        // then
        verify(friendRequestRepository, times(1)).reject(anyLong(), anyLong());

    }

    @Test
    void rejectAlreadyAcceptedRequest() {
        // given
        User rejector = new User();
        rejector.setId(1L);

        User rejected = new User();
        rejected.setId(2L);

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSenderId(rejected.getId());
        friendRequest.setReceiverId(rejector.getId());
        friendRequest.setStatus(ACCEPTED);

        // when
        when(userService.getUserFromSecurityContext()).thenReturn(rejector);
        when(userService.findById(rejected.getId())).thenReturn(rejected);
        when(friendRequestRepository.getRequest(rejected.getId(), rejector.getId())).thenReturn(Optional.of(friendRequest));

        // then
        verify(friendRequestRepository, times(0)).reject(anyLong(), anyLong());
        assertThrows(FriendRequestException.class, () -> friendRequestService.reject(rejected.getId()));

    }
}