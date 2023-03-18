package com.example.demo.Controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Getter
public class DataModel {

    public int Id;
    public List<PostsModel> Hot =  new ArrayList<PostsModel>();
}
