package com.example.demo.post.Controller;

import com.example.demo.common.Bean.CustomException;
import com.example.demo.common.Bean.ErrorCode;
import com.example.demo.post.Bean.CategoryRepository;
import com.example.demo.post.Dto.CategoryModel;
import com.example.demo.post.Dto.MainPageModel;
import com.example.demo.post.Dto.PagingPostModel;
import com.example.demo.post.Dto.PostsModel;
import com.example.demo.post.Bean.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Pageable;
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
    public PagingPostModel CategoryDataSelect(@PathVariable String cat, @RequestParam(value = "nextPage" , required = false) Integer nextPage) {
        if(nextPage == null) {
            System.out.println("nextPage is null ");
            nextPage = 0;
        }
        System.out.println("nextPage is ::: "+nextPage);
        PageRequest pageRequest = PageRequest.of(nextPage, 10);

        List<PostsModel> data = postsRepository.findByCatOrderByDateDesc(cat, pageRequest).getContent();

        PagingPostModel result = new PagingPostModel();
        result.setAllCnt(postsRepository.countByCat(cat));
        result.setDataList(data);
        return result;
    }

    //전체목록
    @RequestMapping(value = "/postList", method = RequestMethod.GET)
    public MainPageModel[] PostList() {
        List<PostsModel> postList = postsRepository.findTop9ByOrderByDateDesc();
        if(postList.size() == 0){
            return null;
        }

        int mock = postList.size() / 3;
        int lest = postList.size() % 3;

        int size = mock;
        if (lest > 0)
            size += 1;

        MainPageModel[] models = new MainPageModel[size];

        for (int i = 0; i < size; i++) {
            models[i] = new MainPageModel();
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
    public boolean PostDelete(@PathVariable Integer id) {

        try{
            postsRepository.deleteById(id);
        }catch (Exception ex){
            throw new CustomException(ErrorCode.UNSUPPORTED_MEDIA_TYPE);
        }
        return true;

    }

    //등록
    @Transactional
    @RequestMapping(value = "/insert", method = { RequestMethod.POST, RequestMethod.GET })
    public void PostInsert(@RequestBody PostsModel model) {

        try{
            if(model.getTitle()==null || model.getContent()==null || model.getCat()==null|| model.getImg()==null)
                throw new CustomException(ErrorCode.UNSUPPORTED_MEDIA_TYPE);
            Date now = new Date();
            model.setDate(now);

            postsRepository.save(model);

        }catch (Exception ex){
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    //수정
    @Transactional
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PATCH)
    public boolean PostUpdate(@PathVariable Integer id, @RequestBody PostsModel model) {

        try{
            //validation Check
            PostsModel data = postsRepository.findById(id);

            if(data == null){
                throw new CustomException(ErrorCode.UNSUPPORTED_MEDIA_TYPE);
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
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return true;
    }

    //검색
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public PagingPostModel SearchPost(@RequestParam("keyword") String search, @RequestParam(value = "nextPage" , required = false) Integer nextPage) {
        int size = 10;
        System.out.println("nextPage is ::: "+nextPage);
        if(nextPage == null) {
            System.out.println("nextPage is null ");
            nextPage = 0;
        }else{
            nextPage = size * nextPage;
        }

        //페이징 native Query
        List<PostsModel> searchList = postsRepository.postSearch(search, nextPage, size);
        System.out.println("searchList cnt :: " + searchList.size());

        //html 제거
        for (PostsModel data : searchList)
            data.setContent(data.getContent().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", ""));

        //결과셋팅
        PagingPostModel result = new PagingPostModel();
        result.setAllCnt(postsRepository.postSearchCount(search));
        result.setDataList(searchList);
        return result;
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

