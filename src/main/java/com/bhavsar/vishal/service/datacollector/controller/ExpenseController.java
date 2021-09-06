package com.bhavsar.vishal.service.datacollector.controller;

import com.bhavsar.vishal.service.datacollector.model.CategoryRecord;
import com.bhavsar.vishal.service.datacollector.model.CategoryResponse;
import com.bhavsar.vishal.service.datacollector.model.ExpenseRecord;
import com.bhavsar.vishal.service.datacollector.repository.CategoryRepository;
import com.bhavsar.vishal.service.datacollector.repository.ExpenseRepository;

import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
        CategoryRecord record;
        val response = CategoryResponse.builder();
        try {
            log.info("Saving new category with name '{}'", categoryRecord.getName());
            record = categoryRepository.save(categoryRecord);
            response.record(record).msg("Expense category saved successfully.");
            log.info("New category added with id={}", record.getId());
        } catch (final DataIntegrityViolationException e) {
            val msg = String.format("Expense category '%s' already exists.", categoryRecord.getName());
            log.error(msg, e);
            response.msg(msg);
        }
        return ResponseEntity.ok(response.build());
    }

    @RequestMapping(value = "/getAllExpenseCategories", method = RequestMethod.GET)
    public ResponseEntity<List<CategoryRecord>> getAllExpenseCategories() {
        log.info("Getting all categories...");
        val records = categoryRepository.findAll(Sort.by(Sort.Order.asc("name")));
        log.debug("Category size = {}", records.size());
        return ResponseEntity.ok(records);
    }

    @RequestMapping(value = "/saveExpenseRecord", method = RequestMethod.POST)
    @ResponseBody
    public ExpenseRecord saveExpenseRecord(@RequestBody final ExpenseRecord expenseRecord) {
        log.info("Saving expense record...");
        val savedRecord = expenseRepository.save(expenseRecord);
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
    public List<ExpenseRecord> getAllExpenseRecords() {
        log.info("Getting all expense records...");
        final List<ExpenseRecord> expenseRecordsList = expenseRepository.findAll();
        log.debug("Records length: {}", expenseRecordsList.size());
        return expenseRecordsList;
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
