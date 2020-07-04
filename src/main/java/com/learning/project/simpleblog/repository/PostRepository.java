package com.learning.project.simpleblog.repository;

import com.learning.project.simpleblog.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
