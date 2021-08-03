package com.mdwairy.momentsapi.app.friend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.mdwairy.momentsapi.app.friend.FriendRequestStatus.ACCEPTED;
import static com.mdwairy.momentsapi.app.friend.FriendRequestStatus.PENDING;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class FriendRequestRepositoryTest {

    @Autowired
    FriendRequestRepository underTestFriendRepository;
    FriendRequest friendRequest;

    @BeforeEach
    void setUp() {
        underTestFriendRepository.deleteAll();
        friendRequest = new FriendRequest();
        friendRequest.setSenderId(1L);
        friendRequest.setReceiverId(2L);
        friendRequest.setStatus(PENDING);
        underTestFriendRepository.save(friendRequest);
    }

    @Test
    void accept() {
        // given
        Long senderId = friendRequest.getSenderId();
        Long receiverId = friendRequest.getReceiverId();

        // when
        underTestFriendRepository.accept(receiverId, senderId);

        // then
        FriendRequest expected = underTestFriendRepository.getRequest(senderId, receiverId).get();
        assertThat(expected.getStatus()).isEqualTo(ACCEPTED);
        assertThat(expected.getSenderId()).isEqualTo(senderId);
        assertThat(expected.getReceiverId()).isEqualTo(receiverId);
    }


    @Test
    void reject() {
        // given
        Long senderId = friendRequest.getSenderId();
        Long receiverId = friendRequest.getReceiverId();

        // when
        underTestFriendRepository.reject(receiverId, senderId);

        // then
        Optional<FriendRequest> expectedOptional = underTestFriendRepository.getRequest(senderId, receiverId);
        assertThat(expectedOptional).isEmpty();
    }

    @Test
    void rejectAlreadyAcceptedRequest() {
        // given
        Long senderId = friendRequest.getSenderId();
        Long receiverId = friendRequest.getReceiverId();

        friendRequest.setStatus(ACCEPTED);
        underTestFriendRepository.save(friendRequest);

        // when
        underTestFriendRepository.reject(receiverId, senderId);

        // then
        Optional<FriendRequest> expectedOptional = underTestFriendRepository.getRequest(senderId, receiverId);
        assertThat(expectedOptional).isPresent();
    }

    @Test
    void checkRequestExists() {

        // given
        Long senderId = friendRequest.getSenderId();
        Long receiverId = friendRequest.getReceiverId();

        // when
        boolean expected = underTestFriendRepository.getRequest(senderId, receiverId).isPresent();

        // then
        assertThat(expected).isTrue();

    }

    @Test
    void checkRequestNotExists() {

        // given
        Long senderId = friendRequest.getSenderId();
        Long receiverId = friendRequest.getReceiverId();
        underTestFriendRepository.reject(receiverId, senderId);

        // when
        boolean expected = underTestFriendRepository.getRequest(senderId, receiverId).isPresent();

        // then
        assertThat(expected).isFalse();

    }

    @Test
    void checkRequestAccepted() {

        // given
        Long senderId = friendRequest.getSenderId();
        Long receiverId = friendRequest.getReceiverId();
        underTestFriendRepository.accept(receiverId, senderId);

        // when
        FriendRequestStatus expected = underTestFriendRepository.getRequest(senderId, receiverId).get().getStatus();

        // then
        assertThat(expected).isEqualTo(ACCEPTED);

    }

    @Test
    void checkRequestPending() {
        // given
        Long senderId = friendRequest.getSenderId();
        Long receiverId = friendRequest.getReceiverId();

        // when
        FriendRequestStatus expected = underTestFriendRepository.getRequest(senderId, receiverId).get().getStatus();

        // then
        assertThat(expected).isEqualTo(PENDING);
    }
}