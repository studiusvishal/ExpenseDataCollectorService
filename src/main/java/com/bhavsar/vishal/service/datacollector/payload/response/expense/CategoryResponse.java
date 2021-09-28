package com.bhavsar.vishal.service.datacollector.payload.response.expense;

import com.bhavsar.vishal.service.datacollector.db.entity.expense.CategoryRecord;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CategoryResponse {
    private List<CategoryRecord> records;
    private String msg;
    private int size;
}
