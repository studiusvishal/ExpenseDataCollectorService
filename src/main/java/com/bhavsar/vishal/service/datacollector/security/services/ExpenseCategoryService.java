package com.bhavsar.vishal.service.datacollector.security.services;

import com.bhavsar.vishal.service.datacollector.db.entity.expense.CategoryRecord;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ExpenseCategoryService {
    CategoryRecord save(final CategoryRecord categoryRecord);

    List<CategoryRecord> findAll(final Sort sortBy);
}
