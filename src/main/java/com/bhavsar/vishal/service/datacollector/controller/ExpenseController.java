package com.bhavsar.vishal.service.datacollector.controller;

import com.bhavsar.vishal.service.datacollector.model.CategoryRecord;
import com.bhavsar.vishal.service.datacollector.model.CategoryResponse;
import com.bhavsar.vishal.service.datacollector.repository.CategoryRepository;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequestMapping(value = "/expense")
public class ExpenseController {
    @Autowired
    private CategoryRepository categoryRepository;

    @RequestMapping(value = "/addCategory", method = RequestMethod.POST)
    public ResponseEntity<CategoryResponse> addCategory(@RequestBody final CategoryRecord categoryRecord) {
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

    @RequestMapping(value = "/getAllCategories", method = RequestMethod.GET)
    public ResponseEntity<List<CategoryRecord>> getAllCategories() {
        log.info("Getting all categories...");
        val records = categoryRepository.findAll(Sort.by(Sort.Order.asc("name")));
        log.debug("Category size = {}", records.size());
        return ResponseEntity.ok(records);
    }
}
