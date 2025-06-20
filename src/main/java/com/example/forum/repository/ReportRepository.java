package com.example.forum.repository;

import com.example.forum.repository.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
//    public List<Report> findAllByOrderByIdDesc();
    //応用課題４日付で絞込
    public List<Report> findByCreatedDateBetween(Date startDate, Date endDate);
}

