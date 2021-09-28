package com.bhavsar.vishal.service.datacollector.security.services;

import com.bhavsar.vishal.service.datacollector.db.entity.expense.ExpenseRecord;

import java.util.List;
import java.util.Optional;

public interface ExpenseService {
    ExpenseRecord save(ExpenseRecord expenseRecord);

    Optional<ExpenseRecord> findById(Long id);

    List<ExpenseRecord> findAll();

    void deleteById(Long id);

    void deleteAll();
}
