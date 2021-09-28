package com.bhavsar.vishal.service.datacollector.repository;

import com.bhavsar.vishal.service.datacollector.db.entity.expense.ExpenseRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseRecord, Long> {
}
