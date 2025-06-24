package com.example.forum.service;

import com.example.forum.controller.form.CommentForm;
import com.example.forum.controller.form.ReportForm;
import com.example.forum.repository.ReportRepository;
import com.example.forum.repository.entity.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    ReportRepository reportRepository;

    /*
     * レコード全件取得処理
     */
    public List<ReportForm> findAllReport(String start, String end) {
//        List<Report> results = reportRepository.findAllByOrderByIdDesc();
        //応用課題４日付で投稿絞込 入力あれば時間を追加、無ければデフォルト値を設定
            if (StringUtils.hasText(start)) {
                start = start + " 00:00:00";
            }else {
                start = "2020-01-01 00:00:00";
            }
            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (StringUtils.hasText(end)) {
                end = end + " 23:59:59";
            }else {
                Date nowDate = new Date();
//                SimpleDateFormat now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                end = sdFormat.format(nowDate);
            }
            //String→Data型に変換
        try{
            Date startDate = sdFormat.parse(start);
            Date endDate = sdFormat.parse(end);
            //endのデフォルト値を現在時刻＋30分で設定
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, 30);
            endDate = calendar.getTime();

            //応用課題５降順に変更ORDER BY Updated Date Desc追加
            List<Report> results = reportRepository.findByUpdatedDateBetweenOrderByUpdatedDateDesc(startDate,endDate);
            List<ReportForm> reports = setReportForm(results);
            return reports;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return List.of();
    }
    //DBから取得したデータ（entity）をFormに設定
    private List<ReportForm> setReportForm(List<Report> results) {
        List<ReportForm> reports = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            ReportForm report = new ReportForm();
            Report result = results.get(i);
            report.setId(result.getId());
            report.setContent(result.getContent());
            reports.add(report);
        }
        return reports;
    }

    //レコード追加、編集内容の追加も
    public void saveReport(ReportForm reqReport) {
        Report saveReport = setReportEntity(reqReport);
        reportRepository.save(saveReport);
    }
     //リクエストから取得した情報をEntityに設定
    private Report setReportEntity(ReportForm reqReport) {
        Report report = new Report();
        report.setId(reqReport.getId());
        report.setContent(reqReport.getContent());
        report.setUpdatedDate(new Date());
        return report;
    }

    //削除機能追加 deleteReport(id)戻り値なし
    public void deleteReport(int id) {
        reportRepository.deleteById(id);
    }

    //編集機能追加　レコード1件取得
    public ReportForm editReport(Integer id) {
        List<Report> results = new ArrayList<>();
        //findByIdメソッド：Id が一致するレコードを取得。合致するものがなければnullで返す
        results.add((Report) reportRepository.findById(id).orElse(null));
        //setReportFormメソッドでFormに詰め替え
        List<ReportForm> reports = setReportForm(results);
        return reports.get(0);
    }

    //応用課題５降順に表示　コメントの追加・編集時に投稿のupdateDateも更新
    public void updateReport(CommentForm commentForm){
        //id、updatedDateの取得
        int id = commentForm.getReportId();
        Date updatedDate = new Date();
        reportRepository.save(id, updatedDate);
    }
}

