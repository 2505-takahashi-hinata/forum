package com.example.forum.controller;

import com.example.forum.controller.form.CommentForm;
import com.example.forum.controller.form.ReportForm;
import com.example.forum.service.CommentService;
import com.example.forum.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@Controller
public class ForumController {
    @Autowired
    ReportService reportService;
    @Autowired
    CommentService commentService;

    /*
     * 投稿内容表示処理
     */
    //@RequestParamはデフォルトではnullを受け付けないため、required=falseを追記。
    @GetMapping
    public ModelAndView top(@RequestParam(name="start", required=false) String start,
                            @RequestParam(name="end", required=false) String end) {
        ModelAndView mav = new ModelAndView();
        // 投稿を全件取得
        // 応用課題４日付で投稿絞込　引数に日付を設定
        List<ReportForm> contentData = reportService.findAllReport(start, end);
        //応用課題１コメントを全件取得、コメント用空のForm準備
        List<CommentForm> commentData = commentService.findAllComments();
        CommentForm commentForm = new CommentForm();
        // 画面遷移先を指定
        mav.setViewName("/top");
        // 投稿データオブジェクトを保管
        mav.addObject("contents", contentData);
        //応用課題１コメントデータオブジェクトを保管、空のFormを保管
        mav.addObject("comments", commentData);
        mav.addObject("commentForm", commentForm);
        //応用課題４入力した日付をセット（絞込押下後も表示されるように）
        mav.addObject("start", start);
        mav.addObject("end", end);
        return mav;
    }
    /*
     * 新規投稿画面表示
     */
    @GetMapping("/new")
    public ModelAndView newContent() {
        ModelAndView mav = new ModelAndView();
        // form用の空のentityを準備
        ReportForm reportForm = new ReportForm();
        // 画面遷移先を指定
        mav.setViewName("/new");
        // 準備した空のFormを保管
        mav.addObject("formModel", reportForm);
        return mav;
    }
    /*
     * 新規投稿処理
     */
    @PostMapping("/add")
    public ModelAndView addContent(@ModelAttribute("formModel") ReportForm reportForm){
        // 投稿をテーブルに格納
        reportService.saveReport(reportForm);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }
    /*
     * 投稿削除機能追加
     */
    @DeleteMapping("/delete/{id}")
    //引数@PathVariableは form タグ内の action 属性の{ } 内で指定されたURLパラメータを取得できる
    public ModelAndView deleteContent(@PathVariable Integer id) {
        // 投稿をテーブルに格納
        reportService.deleteReport(id);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

    //編集画面表示
    @GetMapping("/edit/{id}")
    public ModelAndView editContent(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView();
        // 編集する投稿を取得
        ReportForm report = reportService.editReport(id);
        // 編集する投稿をセット
        mav.addObject("formModel", report);
        // 画面遷移先を指定
        mav.setViewName("/edit");
        return mav;
    }
    //編集処理の実行
    @PutMapping("/update/{id}")
    public ModelAndView updateContent (@PathVariable Integer id,
                                       @ModelAttribute("formModel") ReportForm report) {
        // UrlParameterのidを更新するentityにセット
        report.setId(id);
        // 編集した投稿を更新
        reportService.saveReport(report);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

    //応用課題１コメント機能追加
    @PostMapping("/addComment")
    public ModelAndView addComment(@ModelAttribute("commentForm") CommentForm commentForm){
        // コメントをサービスに渡す
        commentService.saveComment(commentForm);

       //投稿のupdatedateを更新
        reportService.updateReport(commentForm);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

    //応用課題２コメントの編集
    //画面遷移
    @GetMapping("/edit-comment/{id}")
    public ModelAndView editComment(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView();
        // 編集するコメントを取得
        CommentForm comment = commentService.editComment(id);
        // 編集するコメントをセット
        mav.addObject("formModel", comment);
        // 画面遷移先を指定
        mav.setViewName("/edit-comment");
        return mav;
    }
    //応用課題３コメント編集処理
    @PutMapping("/updateComment/{id}")
    public ModelAndView updateComment (@PathVariable Integer id,
                                       @ModelAttribute("formModel") CommentForm commentForm) {
        // UrlParameterのidを更新するentityにセット
        commentForm.setId(id);
        // 編集した投稿を更新
        commentService.saveComment(commentForm);
        //投稿のupdatedateを更新
        reportService.updateReport(commentForm);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }
    //応用課題３コメント削除
    @DeleteMapping("/deleteComment/{id}")
    public ModelAndView deleteComment(@PathVariable Integer id) {
        // 投稿をテーブルに格納
        commentService.deleteComment(id);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

}

