package com.mdwairy.momentsapi.app.comment;

import com.mdwairy.momentsapi.app.post.Post;
import com.mdwairy.momentsapi.app.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceJPA implements CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;

    @Override
    public Comment add(Comment comment, Long postId) {
        Post post = postService.findById(postId);
        post.addComment(comment);
        postService.save(post);
        return comment;
    }
}
