package com.mdwairy.momentsapi.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByUsernameIn(Set<String> usernames);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    @Modifying
    @Query("UPDATE User SET isAccountEnabled = TRUE WHERE username = ?1")
    void enableUser(String username);

    @Modifying
    @Query("UPDATE User SET isAccountEnabled = false WHERE username = ?1")
    void disableUser(String username);

    @Modifying
    @Query("UPDATE User SET firstName = ?2 WHERE username = ?1")
    void updateFirstName(String username, String firstName);

    @Modifying
    @Query("UPDATE User SET lastName = ?2 WHERE username = ?1")
    void updateLastName(String username, String lastName);

    @Modifying
    @Query("UPDATE User SET username = ?2 WHERE username = ?1")
    void updateUserName(String username, String newUsername);

    void deleteByUsername(String email);

}
