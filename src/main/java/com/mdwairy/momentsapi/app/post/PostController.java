package com.mdwairy.momentsapi.app.post;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/posts", produces = APPLICATION_JSON_VALUE)
public class PostController {

    private final PostService postService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public Post save(@RequestBody Post post) {
        return postService.save(post);
    }

    @GetMapping("/{id}")
    public Post findById(@PathVariable Long id) {
        return postService.findById(id);
    }

    @GetMapping
    public List<Post> findAll() {
        return postService.findAll();
    }
}
