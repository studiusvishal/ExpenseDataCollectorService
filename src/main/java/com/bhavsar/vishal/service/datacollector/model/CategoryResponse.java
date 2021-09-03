package com.bhavsar.vishal.service.datacollector.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CategoryResponse {
    private CategoryRecord record;
    private String msg;
}
