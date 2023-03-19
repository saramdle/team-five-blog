package com.example.demo.post.Dto;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "posts")
public class PostsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    //타이틀
    @Column(name = "Title")
    private String title;

    //내용
    @Column(name = "Content")
    private String content;

    @Column(name = "Img")
    private String img;

    @Column(name = "Date")
    private Date date;

    @Column(name = "Uid")
    private int uid;
    @Column(name = "Cat")
    private String cat;

}
