package com.example.demo.post.Dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Getter
@Setter
public class PagingPostModel {
    //전체갯수
    public int allCnt;
    //데이터 리스트
    public List<PostsModel> dataList;
}

