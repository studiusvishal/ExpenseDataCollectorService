package com.bhavsar.vishal.service.datacollector.controller;

import com.bhavsar.vishal.service.datacollector.model.ExpenseRecord;
import com.bhavsar.vishal.service.datacollector.repository.ExpenseRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Log4j2
@RestController
public class SimpleController {
    @Autowired
    private ExpenseRepository expenseRepository;

    @GetMapping("/getAllRecords")
    public List<ExpenseRecord> getAllRecords() {
        final List<ExpenseRecord> expenseRecordsList = expenseRepository.findAll();
        log.debug("Records length: {}", expenseRecordsList.size());
        return expenseRecordsList;
    }

    @RequestMapping(value = "/saveData", method = RequestMethod.POST)
    @ResponseBody
    public ExpenseRecord saveData(@RequestBody final ExpenseRecord expenseRecord) {
        final ExpenseRecord savedRecord = expenseRepository.save(expenseRecord);
        log.debug("Saved record: {}", savedRecord);
        return savedRecord;
    }
}
