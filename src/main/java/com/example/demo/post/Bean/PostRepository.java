package com.example.demo.post.Bean;

import com.example.demo.post.Dto.PostsModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.data.domain.Pageable;
import java.util.List;
@SuppressWarnings("unchecked")
@Repository
public interface PostRepository extends JpaRepository<PostsModel, Long> {
    //3x3 조회
    public List<PostsModel> findTop9ByOrderByDateDesc();
    //카테고리별 조회(페이징)
    Page<PostsModel> findByCatOrderByDateDesc(String cat, Pageable pageable);
    //카테고리별 전체갯수
    int countByCat(String cat);
    //상세조회
    public PostsModel findById(int id);
    //삭제
    public void deleteById(int id);
    //저장
    public PostsModel save(PostsModel model);

    //검색(타이틀,내용)
    @Query(value = "select id, title, content, img, Date, Uid, Cat from posts where title LIKE %?1% or content LIKE %?1%  order by Date desc limit ?2,?3", nativeQuery = true)
    List<PostsModel> postSearch(@Param("search") String search, @Param("nextPage") Integer nextPage, @Param("size") Integer size);

    @Query(value = "select count(*) from posts where title LIKE %?1% or content LIKE %?1%", nativeQuery = true)
    int postSearchCount(@Param("search") String search);



}
