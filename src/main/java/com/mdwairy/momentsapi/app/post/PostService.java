package com.mdwairy.momentsapi.app.post;

import java.util.List;

public interface PostService {
    Post save(Post post);
    Post findById(Long id);
    List<Post> findAll();
}
