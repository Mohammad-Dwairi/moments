package com.mdwairy.momentsapi.app.post;

import com.mdwairy.momentsapi.users.User;
import com.mdwairy.momentsapi.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            return optionalPost.get();
        }
        throw new RuntimeException("Post not found");
    }

    @Override
    public List<Post> findAll() {
        User securityContextUser = userService.getUserFromSecurityContext();
        return postRepository.findAllByUser(securityContextUser);
    }
}
