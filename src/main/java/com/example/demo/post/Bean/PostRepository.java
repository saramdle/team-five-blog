package com.example.demo.post.Bean;

import com.example.demo.post.Dto.PostsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostsModel, Long> {
    public List<PostsModel> findAll();
    public PostsModel findById(int id);
}
