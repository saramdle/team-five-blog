package com.example.demo.post.Bean;

import com.example.demo.post.Dto.CategoryModel;
import com.example.demo.post.Dto.PostsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unchecked")
@Repository
public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {
    //전체 카테고리
    public List<CategoryModel> findAll();
}
