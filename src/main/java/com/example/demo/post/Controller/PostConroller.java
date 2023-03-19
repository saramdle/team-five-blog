package com.example.demo.post.Controller;

import com.example.demo.post.Dto.DataModel;
import com.example.demo.post.Dto.PostsModel;
import com.example.demo.post.Bean.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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

    //삭제
    @Transactional
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public void PostDelete(@PathVariable Integer id) {
        postsRepository.deleteById(id);
    }

    //등록
    @Transactional
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public void PostInsert(PostsModel model) {
        //validation Check
        if(model.getTitle().equals("") || model.getContent().equals("") || model.getImg().equals("")|| model.getImg().equals(""))
            return;

        Date now = new Date();
        model.setDate(now);

        postsRepository.save(model);
    }
    
    //수정
    @Transactional
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public boolean PostUpdate(@PathVariable Integer id, PostsModel model) {

        //validation Check
        PostsModel data = postsRepository.findById(id);

        if(data == null)
            return false;
        if(model.getTitle() != null)
            data.setTitle(model.getTitle());
        if(model.getContent() != null)
            data.setContent(model.getContent());
        if(model.getImg() != null)
            data.setImg(model.getImg());
        if(model.getCat() != null)
            data.setCat(model.getCat());

        postsRepository.save(data);

        return true;
    }

}