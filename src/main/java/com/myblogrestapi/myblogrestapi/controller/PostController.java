package com.myblogrestapi.myblogrestapi.controller;


import com.myblogrestapi.myblogrestapi.payload.PostDto;
import com.myblogrestapi.myblogrestapi.payload.PostResponse;
import com.myblogrestapi.myblogrestapi.service.impl.PostService;
import com.myblogrestapi.myblogrestapi.utils.AppConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Object> createPost(@Valid @RequestBody PostDto postDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){

            return new ResponseEntity<>(bindingResult.getFieldError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }
    @GetMapping()
    public PostResponse getAllPosts(
        @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required= false)  int pageNo,
        @RequestParam(value = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
        @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
        @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir
    ){

        return postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok((PostDto) postService.getPostById(id));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable(name = "id") long id){

        PostDto postResponse = postService.updatePost(postDto, id);

        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id){

        postService.deletePostById(id);

        return new ResponseEntity<>("Post entity deleted successfully.", HttpStatus.OK);
    }






}


