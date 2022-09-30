package com.moneyguard.moneyguard.service;

import com.moneyguard.moneyguard.dao.CategoryDAO;
import com.moneyguard.moneyguard.model.Category;
import com.moneyguard.moneyguard.repository.CategoryRepository;
import com.moneyguard.moneyguard.repository.TransactionRepository;
import com.moneyguard.moneyguard.request.CreateCategoryRequest;
import com.moneyguard.moneyguard.request.UpdateCategoryRequest;
import com.moneyguard.moneyguard.response.CategoryDetailResponse;
import com.moneyguard.moneyguard.response.DropdownListResource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AuthService authService;

    public CategoryDAO create(CreateCategoryRequest request)
    {
        Category category = new Category();
        BeanUtils.copyProperties(request, category);
        category.setUser(authService.getAuthUser());
        category.setCreatedAt(new Date());
        category.setUpdatedAt(new Date());
        categoryRepository.save(category);

        CategoryDAO categoryDAO = new CategoryDAO();
        BeanUtils.copyProperties(category, categoryDAO);
        return categoryDAO;
    }

    @Override
    public CategoryDAO update(Category category, UpdateCategoryRequest request) {
        category.setUpdatedAt(new Date());
        category.setName(request.getName());
        categoryRepository.save(category);

        CategoryDAO categoryDAO = new CategoryDAO();
        BeanUtils.copyProperties(category, categoryDAO);
        return categoryDAO;
    }

    @Override
    public Boolean delete(Category category) {
        categoryRepository.delete(category);
        return true;
    }

    @Override
    public CategoryDetailResponse get(Category category) {
        return new CategoryDetailResponse(category, transactionRepository);
    }

    @Override
    public List<CategoryDAO> list(Integer page, String search, Short type) {
        Pageable pageable = PageRequest.of(page, 25);
        Page<Category> pageData = categoryRepository.findAllByNameContainsIgnoreCaseAndUserIdAndTypeOrderByNameAsc(search, authService.getAuthUser().getId(), pageable, type);
        List<Category> categories = pageData.getContent();

        List<CategoryDAO> daoList = new ArrayList<>();
        categories.forEach(c -> {
            CategoryDAO categoryDAO = new CategoryDAO();
            categoryDAO.setType(c.getType());
            categoryDAO.setName(c.getName());
            categoryDAO.setCreatedAt(c.getCreatedAt());
            categoryDAO.setUpdatedAt(c.getUpdatedAt());
            categoryDAO.setId(c.getId());
            daoList.add(categoryDAO);
        });
        return daoList;
    }

    @Override
    public List<DropdownListResource> getSelectData(String search, Short type) {
        List<Category> categories = categoryRepository.findAllByNameContainsIgnoreCaseAndUserIdAndTypeOrderByNameAsc(search, authService.getAuthUser().getId(), type);
        List<DropdownListResource> dropdownListData = new ArrayList<>();
        categories.forEach(c -> {
            DropdownListResource listItem = new DropdownListResource();
            listItem.setText(c.getName());
            listItem.setValue(c.getId().toString());
            dropdownListData.add(listItem);
        });
        return dropdownListData;
    }
}
