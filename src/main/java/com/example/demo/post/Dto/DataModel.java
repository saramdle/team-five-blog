package com.example.demo.post.Dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Getter
public class DataModel {
    public int id = 0;
    public List<PostsModel> hot =  new ArrayList<PostsModel>();
}
