package com.mdwairy.momentsapi.friendship;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;

import static javax.persistence.EnumType.STRING;

@Entity
@Getter
@Setter
@IdClass(FriendshipId.class)
@AllArgsConstructor
@NoArgsConstructor
public class Friendship {

    @Id
    private String username1;

    @Id
    private String username2;

    @Enumerated(STRING)
    private FriendshipStatus status;
}
