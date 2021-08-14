package com.bhavsar.vishal.service.datacollector.controller;

import com.bhavsar.vishal.service.datacollector.model.ExpenseRecord;
import com.bhavsar.vishal.service.datacollector.repository.ExpenseRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
public class SimpleController {
    @Autowired
    private ExpenseRepository expenseRepository;

    @GetMapping("/getRecord")
    public ResponseEntity<?> getRecord(@RequestParam(value = "id") final Long id){
        final Optional<ExpenseRecord> optionalExpenseRecord = expenseRepository.findById(id);
        if (optionalExpenseRecord.isPresent()) {
            return ResponseEntity.ok(optionalExpenseRecord.get());
        } else {
            return ResponseEntity.badRequest().body("No record with specified id " + id + " found");
        }
    }

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

    @DeleteMapping(value = "/deleteRecord")
    public ResponseEntity<?> deleteRecord(@RequestParam(value="id") final Long id) {
        expenseRepository.deleteById(id);
        return ResponseEntity.ok("Record with id "+ id + " deleted successfully");
    }

    @DeleteMapping(value = "/deleteAllRecords")
    public ResponseEntity<?> deleteAllRecords() {
        expenseRepository.deleteAll();
        return ResponseEntity.ok("All records deleted successfully");
    }
}
