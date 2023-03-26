package com.example.demo.post.Controller;

import com.example.demo.common.Image.ImageUpload;
import com.example.demo.post.Bean.CategoryRepository;
import com.example.demo.post.Dto.CategoryModel;
import com.example.demo.post.Dto.DataModel;
import com.example.demo.post.Dto.PostsModel;
import com.example.demo.post.Bean.PostRepository;
import com.example.demo.common.Bean.ResultModel;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@CrossOrigin
@RestController
public class PostConroller {

    @Value("${part4.upload.path}")
    private static String uploadPath;
    @Autowired
    private PostRepository postsRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    //전체 카테고리
    @RequestMapping(value = "/categoryList", method = RequestMethod.GET)
    public List<CategoryModel> CategoryList() {
        List<CategoryModel> category = categoryRepository.findAll();
        System.out.println("category cnt :: " + category.size());
        return category;
    }


    //카테고리별 전체목록
    @RequestMapping(value = "/categoryDataSelect/{cat}", method = RequestMethod.GET)
    public List<PostsModel> CategoryDataSelect(@PathVariable String cat) {
        return postsRepository.findByCat(cat);
    }

    //전체목록
    @RequestMapping(value = "/postList", method = RequestMethod.GET)
    public DataModel[] PostList() {
        List<PostsModel> postList = postsRepository.findAll();


        System.out.println("postList cnt :: " + postList.size());
        if(postList.size() == 0){
            return null;
        }

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
                    postList.get(i).setContent(postList.get(i).getContent().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", ""));
                    models[temp2].hot.add(postList.get(i));
                    c = 1;
                }
            } else {
                if (models[temp2].id == 0)
                    models[temp2].id = temp;
                if (i != postList.size()){
                    postList.get(i).setContent(postList.get(i).getContent().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", ""));
                    models[temp2].hot.add(postList.get(i));
                }
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
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResultModel PostDelete(@PathVariable Integer id) {
        ResultModel result = new ResultModel();
        result.setErrorCode(200);

        try{
            postsRepository.deleteById(id);
        }catch (Exception ex){
            result.setErrorCode(500);
            result.setMessage(ex.getMessage());
        }
        return result;

    }

    //등록
    @Transactional
    @RequestMapping(value = "/insert", method = { RequestMethod.POST, RequestMethod.GET })
    public ResultModel PostInsert(@RequestBody PostsModel model) {
        ResultModel result = new ResultModel();
        result.setErrorCode(200);

        try{
            if(model.getTitle()==null || model.getContent()==null || model.getCat()==null|| model.getImg()==null)
            {
                result.setErrorCode(415);
                result.setMessage("title or content or img or cat is null");
                return result;
            }
            Date now = new Date();
            model.setDate(now);

            postsRepository.save(model);

        }catch (Exception ex){
            result.setErrorCode(500);
            result.setMessage(ex.getMessage());
        }
        return result;
    }

    //수정
    @Transactional
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PATCH)
    public ResultModel PostUpdate(@PathVariable Integer id, @RequestBody PostsModel model) {
        ResultModel result = new ResultModel();
        result.setErrorCode(200);
        try{
            //validation Check
            PostsModel data = postsRepository.findById(id);

            if(data == null){
                result.setErrorCode(500);
                result.setMessage(id+" no data.");
            }

            if(model.getTitle() != null)
                data.setTitle(model.getTitle());
            if(model.getContent() != null)
                data.setContent(model.getContent());
            if(model.getImg() != null)
                data.setImg(model.getImg());
            if(model.getCat() != null)
                data.setCat(model.getCat());

            postsRepository.save(data);
        }catch (Exception ex){
            result.setErrorCode(500);
            result.setMessage(ex.getMessage());
        }
        return result;
    }

    //검색
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public List<PostsModel> SearchPost(@RequestParam("search") String search) {
        List<PostsModel> searchList = postsRepository.postSearch(search);
        System.out.println("searchList cnt :: " + searchList.size());
        for (PostsModel data : searchList)
            data.setContent(data.getContent().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", ""));
        return searchList;
    }

    //이미지 업로드처리
    @Transactional
    @RequestMapping(value = "/imageUpload", method = RequestMethod.POST)
    public String ImageUpload(@RequestParam MultipartFile uploadFile) throws IOException {

        String originalName = uploadFile.getOriginalFilename();//파일명:모든 경로를 포함한 파일이름
        String fileName = originalName.substring(originalName.lastIndexOf("//") + 1);
        //예를 들어 getOriginalFileName()을 해서 나온 값이 /Users/Document/bootEx 이라고 한다면
        //"마지막으로온 "/"부분으로부터 +1 해준 부분부터 출력하겠습니다." 라는 뜻입니다.따라서 bootEx가 됩니다.

        System.out.println("fileName :: " + fileName);

        //UUID
        String uuid = UUID.randomUUID().toString();
        //저장할 파일 이름 중간에 "_"를 이용하여 구분
        //String saveName = uploadPath + File.separator + File.separator + uuid + "_" + fileName;
        String saveName = "/Users/kimdw/OneDrive/testDataCenter/" + uuid + "_" + fileName;

        System.out.println("saveName :: " + saveName);
        Path savePath = Paths.get(saveName);
        //Paths.get() 메서드는 특정 경로의 파일 정보를 가져옵니다.(경로 정의하기)

        try {
            uploadFile.transferTo(savePath);
            //uploadFile에 파일을 업로드 하는 메서드 transferTo(file)
        } catch (IOException e) {
            e.printStackTrace();
            //printStackTrace()를 호출하면 로그에 Stack trace가 출력됩니다.
        }
        return "/resources/user/" + uuid + "_" + fileName;
    }
}

