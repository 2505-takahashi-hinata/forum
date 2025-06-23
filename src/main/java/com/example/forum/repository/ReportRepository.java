package com.example.forum.repository;

import com.example.forum.repository.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
    //応用課題４日付で絞込
    //応用課題５降順に変更ORDER BY Updated Date Desc追加
    public List<Report> findByUpdatedDateBetweenOrderByUpdatedDateDesc(Date startDate, Date endDate);

    //応用課題５降順に表示　IDに紐づくupdateDateのみ更新
    @Modifying
    @Query("update report t set t.updatedDate = :updatedDate where t.id = :id")
    public void updateUpdatedDateById(@Param("id") Integer id, @Param("updatedDate") Date updatedDate);
}

