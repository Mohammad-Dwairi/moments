package com.mdwairy.momentsapi.app.post;

import com.mdwairy.momentsapi.users.User;
import com.mdwairy.momentsapi.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostServiceJPA implements PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    @Override
    public Post save(Post post) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getPrincipal().toString();
        User user = userService.getUserByEmail(username);
        post.setOwner(user);
        post.setPostedAt(LocalDateTime.now());
        return postRepository.save(post);
    }
}
