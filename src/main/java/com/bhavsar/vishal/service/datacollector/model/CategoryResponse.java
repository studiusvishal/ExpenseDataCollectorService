package com.bhavsar.vishal.service.datacollector.model;

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
