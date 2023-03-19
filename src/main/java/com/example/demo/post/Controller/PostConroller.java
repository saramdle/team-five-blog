package com.example.demo.post.Controller;

import com.example.demo.post.Dto.DataModel;
import com.example.demo.post.Dto.PostsModel;
import com.example.demo.post.Bean.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class PostConroller {

    @Autowired
    private PostRepository postsRepository;
    
    //전체목록
    @RequestMapping(value = "/postList", method = RequestMethod.GET)
    public DataModel[] PostList() {
        List<PostsModel> postList = postsRepository.findAll();
        System.out.println(" cnt :: " + postList.size());

        int mock = postList.size() / 3;
        int lest = postList.size() % 3;

        int size = mock;
        if (lest > 0)
            size += 1;

        DataModel[] models = new DataModel[size];

        for (int i = 0; i < size; i++) {
            models[i] = new DataModel();
        }

        int temp = 1;
        int temp2 = 0;
        int c = 0;
        for (int i = 0; i <= postList.size(); i++) {
            if (c == 3) {
                if (i != postList.size()) {
                    temp2++;
                    temp++;
                    models[temp2].hot.add(postList.get(i));
                    c = 1;
                }
            } else {
                if (models[temp2].id == 0)
                    models[temp2].id = temp;
                if (i != postList.size())
                    models[temp2].hot.add(postList.get(i));
                c++;
            }
        }
        return models;
    }

    //디테일
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public PostsModel PostDetail(@PathVariable Integer id) {
        return postsRepository.findById(id);
    }


}