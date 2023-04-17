package com.myblogrestapi.myblogrestapi.repository;

import com.myblogrestapi.myblogrestapi.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Post,Long> {

}
