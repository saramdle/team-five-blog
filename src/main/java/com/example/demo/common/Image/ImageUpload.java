package com.example.demo.common.Image;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

//이미지 공통업로드 기능을 수행한다
public class ImageUpload {

    @Value("${part4.upload.path}")
    private static String uploadPath;

    //이미지 업로드기능
    public static String UploadImg(MultipartFile uploadFile){
        String url = ImageUpload.UploadImg(uploadFile);
        String originalName = uploadFile.getOriginalFilename();//파일명:모든 경로를 포함한 파일이름
        String fileName = originalName.substring(originalName.lastIndexOf("//") + 1);
        //예를 들어 getOriginalFileName()을 해서 나온 값이 /Users/Document/bootEx 이라고 한다면
        //"마지막으로온 "/"부분으로부터 +1 해준 부분부터 출력하겠습니다." 라는 뜻입니다.따라서 bootEx가 됩니다.

        System.out.println("fileName :: " + fileName);

        //UUID
        String uuid = UUID.randomUUID().toString();
        //저장할 파일 이름 중간에 "_"를 이용하여 구분
        String saveName = uploadPath + File.separator + File.separator + uuid + "_" + fileName;

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
