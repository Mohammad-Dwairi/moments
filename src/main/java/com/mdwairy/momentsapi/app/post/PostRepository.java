package com.mdwairy.momentsapi.app.post;

import com.mdwairy.momentsapi.users.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {
    List<Post> findAllByUser(User user);
}
