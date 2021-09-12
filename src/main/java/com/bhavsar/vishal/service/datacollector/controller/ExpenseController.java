package com.bhavsar.vishal.service.datacollector.controller;

import com.bhavsar.vishal.service.datacollector.model.CategoryRecord;
import com.bhavsar.vishal.service.datacollector.model.CategoryResponse;
import com.bhavsar.vishal.service.datacollector.model.ExpenseRecord;
import com.bhavsar.vishal.service.datacollector.model.expense.ExpenseResponse;
import com.bhavsar.vishal.service.datacollector.repository.CategoryRepository;
import com.bhavsar.vishal.service.datacollector.repository.ExpenseRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping(value = "/expense")
public class ExpenseController {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @RequestMapping(value = "/addExpenseCategory", method = RequestMethod.POST)
    public ResponseEntity<CategoryResponse> addExpenseCategory(@RequestBody final CategoryRecord categoryRecord) {
        try {
            log.info("Saving new category with name '{}'", categoryRecord.getName());
            final var record = categoryRepository.save(categoryRecord);
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
        final var records = categoryRepository.findAll(Sort.by(Sort.Order.asc("name")));
        log.debug("Category size = {}", records.size());
        final var response = CategoryResponse.builder()
                .records(records)
                .size(records.size())
                .build();
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/saveExpenseRecord", method = RequestMethod.POST)
    @ResponseBody
    public ExpenseRecord saveExpenseRecord(@RequestBody final ExpenseRecord expenseRecord) {
        log.info("Saving expense record...");
        final var savedRecord = expenseRepository.save(expenseRecord);
        log.debug("Saved record: {}", savedRecord);
        return savedRecord;
    }

    @GetMapping("/getExpenseRecord")
    public ResponseEntity<?> getExpenseRecord(@RequestParam(value = "id") final Long id){
        log.info("Getting expense record with id={}", id);
        final Optional<ExpenseRecord> optionalExpenseRecord = expenseRepository.findById(id);
        if (optionalExpenseRecord.isPresent()) {
            return ResponseEntity.ok(optionalExpenseRecord.get());
        } else {
            log.debug("No expense record with id={} found.", id);
            return ResponseEntity.badRequest().body("No record with specified id " + id + " found");
        }
    }

    @GetMapping("/getAllExpenseRecords")
    public ResponseEntity<ExpenseResponse> getAllExpenseRecords() {
        log.info("Getting all expense records...");
        final var expenseRecordsList = expenseRepository.findAll();
        final var size = expenseRecordsList.size();
        log.debug("Records length: {}", size);
        final var expenseResponse = ExpenseResponse.builder()
                .records(expenseRecordsList)
                .size(size)
                .message(size == 0 ? "No records found" : "")
                .build();
        return ResponseEntity.ok(expenseResponse);
    }

    @DeleteMapping(value = "/deleteExpenseRecord")
    public ResponseEntity<?> deleteExpenseRecord(@RequestParam(value="id") final Long id) {
        log.info("Deleting expense record with id={}", id);
        expenseRepository.deleteById(id);
        return ResponseEntity.ok("Record with id "+ id + " deleted successfully");
    }

    @DeleteMapping(value = "/deleteAllExpenseRecords")
    public ResponseEntity<?> deleteAllExpenseRecords() {
        log.info("Deleting all expense records...");
        expenseRepository.deleteAll();
        return ResponseEntity.ok("All records deleted successfully");
    }
}
