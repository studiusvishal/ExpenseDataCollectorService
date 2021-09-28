package com.bhavsar.vishal.service.datacollector.payload.response.expense;

import com.bhavsar.vishal.service.datacollector.db.entity.expense.ExpenseRecord;
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
