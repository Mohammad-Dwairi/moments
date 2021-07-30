package com.mdwairy.momentsapi.app.comment;

import com.mdwairy.momentsapi.app.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;

    @PostMapping
    public Comment add(@PathVariable Long postId, @RequestBody Comment comment) {
        return commentService.add(comment, postId);
    }
}
