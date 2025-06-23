package com.example.forum.service;

import com.example.forum.controller.form.CommentForm;
import com.example.forum.controller.form.ReportForm;
import com.example.forum.repository.CommentRepository;
import com.example.forum.repository.ReportRepository;
import com.example.forum.repository.entity.Comment;
import com.example.forum.repository.entity.Comment;
import com.example.forum.repository.entity.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//応用課題１コメント機能追加
@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    public void saveComment(CommentForm commentForm) {
        Comment saveComment = setCommentEntity(commentForm);
        commentRepository.save(saveComment);
    }
    //リクエストから取得した情報をEntityに設定
    private Comment setCommentEntity(CommentForm commentForm) {
        Comment comment = new Comment();
        comment.setId(commentForm.getId());
        comment.setText(commentForm.getText());
        comment.setReportId(commentForm.getReportId());
        comment.setUpdatedDate(new Date());
        return comment;
    }
    //コメント全件取得
    public List<CommentForm> findAllComments() {
        List<Comment> results = commentRepository.findAllByOrderByUpdatedDateDesc();
        List<CommentForm> comments = setCommentForm(results);
        return comments;
    }

    //応用課題２コメント編集　該当コメント１件取得
    public CommentForm editComment(Integer id) {
        List<Comment> results = new ArrayList<>();
        results.add((Comment) commentRepository.findById(id).orElse(null));
        List<CommentForm> comments = setCommentForm(results);
        return comments.get(0);
    }

    //DBから取得したデータ（entity）をFormに設定
    private List<CommentForm> setCommentForm(List<Comment> results) {
        List<CommentForm> comments = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            CommentForm comment = new CommentForm();
            Comment result = results.get(i);
            comment.setId(result.getId());
            comment.setText(result.getText());
            comment.setReportId(result.getReportId());
            comments.add(comment);
        }
        return comments;
    }

    //応用課題３コメントの削除
    public void deleteComment(int id) {
        commentRepository.deleteById(id);
    }
}