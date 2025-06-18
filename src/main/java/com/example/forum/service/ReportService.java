package com.example.forum.service;

import com.example.forum.controller.form.ReportForm;
import com.example.forum.repository.ReportRepository;
import com.example.forum.repository.entity.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    ReportRepository reportRepository;

    /*
     * レコード全件取得処理
     */
    public List<ReportForm> findAllReport() {
        List<Report> results = reportRepository.findAllByOrderByIdDesc();
        List<ReportForm> reports = setReportForm(results);
        return reports;
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
}

