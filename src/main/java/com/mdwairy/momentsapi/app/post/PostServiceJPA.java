package com.mdwairy.momentsapi.app.post;

import com.mdwairy.momentsapi.users.User;
import com.mdwairy.momentsapi.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceJPA implements PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    @Override
    public Post save(Post post) {
        User securityContextUser = userService.getUserFromSecurityContext();
        post.setUser(securityContextUser);
        post.setPostedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    @Override
    public Post findById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    @Override
    public List<Post> findAll() {
        User securityContextUser = userService.getUserFromSecurityContext();
        return postRepository.findAllByUser(securityContextUser);
    }
}
