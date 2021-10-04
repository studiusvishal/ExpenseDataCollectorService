package com.bhavsar.vishal.service.datacollector.controller;

import com.bhavsar.vishal.service.datacollector.db.entity.expense.CategoryRecord;
import com.bhavsar.vishal.service.datacollector.payload.response.expense.CategoryResponse;
import com.bhavsar.vishal.service.datacollector.security.services.ExpenseCategoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Log4j2
@RestController
@RequestMapping("/api/expense/category")
public class ExpenseCategoryController {
    @Autowired
    private ExpenseCategoryService expenseCategoryService;

    @PostMapping(value = "/addExpenseCategory")
    public ResponseEntity<CategoryResponse> addExpenseCategory(@RequestBody final CategoryRecord categoryRecord) {
        try {
            log.info("Saving new category with name '{}'", categoryRecord.getName());
            final var record = expenseCategoryService.save(categoryRecord);
            final var response = CategoryResponse.builder()
                    .records(Collections.singletonList(record))
                    .msg("Expense category saved successfully.")
                    .size(1)
                    .build();
            log.info("New category added with id={}", record.getId());
            return ResponseEntity.ok(response);
        } catch (final DataIntegrityViolationException e) {
            final var msg = String.format("Expense category '%s' already exists.", categoryRecord.getName());
            throw new DataIntegrityViolationException(msg);
        }
    }

    @RequestMapping(value = "/getAllExpenseCategories", method = RequestMethod.GET)
    public ResponseEntity<CategoryResponse> getAllExpenseCategories() {
        log.info("Getting all categories...");
        final var records = expenseCategoryService.findAll(Sort.by(Sort.Order.asc("name")));
        log.debug("Category size = {}", records.size());
        final var response = CategoryResponse.builder()
                .records(records)
                .size(records.size())
                .build();
        return ResponseEntity.ok(response);
    }
}
