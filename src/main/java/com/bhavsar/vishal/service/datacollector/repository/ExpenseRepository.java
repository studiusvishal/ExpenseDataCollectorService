package com.bhavsar.vishal.service.datacollector.repository;

import com.bhavsar.vishal.service.datacollector.model.ExpenseRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.cache.annotation.Cacheable;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseRecord, Long> {
    // @Cacheable("ExpenseCache")
//    @Query("select m from expense_data_table m")
//    List<ExpenseRecord> findAllRecords();
}
