package com.example.demo.post.Bean;

import com.example.demo.post.Dto.PostsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@SuppressWarnings("unchecked")
@Repository
public interface PostRepository extends JpaRepository<PostsModel, Long> {
    //전체조회
    public List<PostsModel> findAll();
    //카테고리별 조회
    public List<PostsModel> findByCat(String cat);
    //상세1건찾기
    public PostsModel findById(int id);
    //삭제
    public void deleteById(int id);
    //저장
    public PostsModel save(PostsModel model);

    //타이틀 검색
    @Query(value = "select id, title, content, img, Date, Uid, Cat from posts where title LIKE %?1% or content LIKE %?1%", nativeQuery = true)
    List<PostsModel> postSearch(@Param("search") String search);

}
