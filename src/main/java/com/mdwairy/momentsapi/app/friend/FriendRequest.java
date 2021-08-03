package com.mdwairy.momentsapi.app.friend;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@IdClass(FriendId.class)
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequest {

    @Id
    private Long senderId;

    @Id
    private Long receiverId;

    @Enumerated(EnumType.STRING)
    private FriendRequestStatus status;
}

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
class FriendId implements Serializable {
    private Long senderId;
    private Long receiverId;
}



