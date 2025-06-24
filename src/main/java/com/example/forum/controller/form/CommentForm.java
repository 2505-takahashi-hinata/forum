package com.example.forum.controller.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentForm {

    private int id;
    //応用課題６バリデーション
    @NotBlank //(message = "投稿内容を入力してください")
    private String text;
    private int reportId;
    private Date createdDate;
    private Date updatedDate;
}
