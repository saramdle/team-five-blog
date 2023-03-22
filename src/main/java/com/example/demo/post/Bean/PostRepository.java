package com.example.demo.post.Bean;

import com.example.demo.post.Dto.PostsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@SuppressWarnings("unchecked")
@Repository
public interface PostRepository extends JpaRepository<PostsModel, Long> {
    //전체조회
    public List<PostsModel> findAll();
    //카테고리별 조회
    public List<PostsModel> findByCat(String cat);
    public PostsModel findById(int id);
    public void deleteById(int id);
    public PostsModel save(PostsModel model);
}
