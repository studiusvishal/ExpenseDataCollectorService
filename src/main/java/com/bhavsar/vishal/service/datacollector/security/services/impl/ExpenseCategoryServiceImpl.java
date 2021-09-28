package com.bhavsar.vishal.service.datacollector.security.services.impl;

import com.bhavsar.vishal.service.datacollector.db.entity.expense.CategoryRecord;
import com.bhavsar.vishal.service.datacollector.repository.CategoryRepository;
import com.bhavsar.vishal.service.datacollector.security.services.ExpenseCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseCategoryServiceImpl implements ExpenseCategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryRecord save(final CategoryRecord categoryRecord) {
        return categoryRepository.save(categoryRecord);
    }

    @Override
    public List<CategoryRecord> findAll(final Sort sortBy) {
        return categoryRepository.findAll(sortBy);
    }
}
