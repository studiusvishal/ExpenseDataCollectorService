package com.bhavsar.vishal.service.datacollector.security.services.impl;

import com.bhavsar.vishal.service.datacollector.db.entity.expense.ExpenseRecord;
import com.bhavsar.vishal.service.datacollector.repository.ExpenseRepository;
import com.bhavsar.vishal.service.datacollector.security.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    @Override
    public ExpenseRecord save(final ExpenseRecord expenseRecord) {
        return expenseRepository.save(expenseRecord);
    }

    @Override
    public Optional<ExpenseRecord> findById(final Long id) {
        return expenseRepository.findById(id);
    }

    @Override
    public List<ExpenseRecord> findAll() {
        return expenseRepository.findAll();
    }

    @Override
    public void deleteById(final Long id) {
        expenseRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        expenseRepository.deleteAll();
    }
}
