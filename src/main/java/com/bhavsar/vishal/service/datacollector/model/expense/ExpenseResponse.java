package com.bhavsar.vishal.service.datacollector.model.expense;

import com.bhavsar.vishal.service.datacollector.model.ExpenseRecord;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExpenseResponse {
    private int size;
    private List<ExpenseRecord> records;
    private String message;
}
