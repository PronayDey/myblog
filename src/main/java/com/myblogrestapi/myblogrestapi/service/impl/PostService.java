package com.myblogrestapi.myblogrestapi.service.impl;

import com.myblogrestapi.myblogrestapi.payload.PostDto;
import com.myblogrestapi.myblogrestapi.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy,String sortDir);

    PostDto getPostById(long id);

    PostDto updatePost(PostDto postDto, long id);

    void deletePostById(long id);
}
