package com.moneyguard.moneyguard.service;

import com.moneyguard.moneyguard.dao.CategoryDAO;
import com.moneyguard.moneyguard.model.Category;
import com.moneyguard.moneyguard.request.CreateCategoryRequest;
import com.moneyguard.moneyguard.request.UpdateCategoryRequest;
import com.moneyguard.moneyguard.response.CategoryDetailResponse;
import com.moneyguard.moneyguard.response.DropdownListResource;

import java.util.List;

public interface CategoryService {

    CategoryDAO create(CreateCategoryRequest request);
    CategoryDAO update(Category category, UpdateCategoryRequest request);
    Boolean delete(Category category);
    CategoryDetailResponse get(Category category);
    List<CategoryDAO> list(Integer page, String search, Short type);
    List<DropdownListResource> getSelectData(String search, Short type);

}
