package com.example.forum.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReportForm {

    private int id;
    private String content;
    private Date createdDate;
    private Date updatedDate;

//getter,setter自動生成してくれるため省略　Lombok
//    public int getId() {
//        return id;
//    }
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getContent() {
//        return content;
//    }
//    public void setContent(String content) {
//        this.content = content;
//    }
}
