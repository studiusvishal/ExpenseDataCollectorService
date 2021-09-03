package com.bhavsar.vishal.service.datacollector.repository;

import com.bhavsar.vishal.service.datacollector.model.ExpenseRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseRecord, Long> {
}
