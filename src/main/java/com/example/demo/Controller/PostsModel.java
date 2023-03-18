package com.example.demo.Controller;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.grammars.hql.HqlParser;

import java.util.Date;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Getter
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
    private String Title;

    //내용
    @Column(name = "Content")
    private String Content;

    @Column(name = "Img")
    private String Img;

    @Column(name = "Date")
    private Date UpdateDt;

    @Column(name = "Uid")
    private int Uid;
    @Column(name = "Cat")
    private String Cat;

}
