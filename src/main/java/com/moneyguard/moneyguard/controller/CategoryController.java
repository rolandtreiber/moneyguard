package com.moneyguard.moneyguard.controller;

import com.moneyguard.moneyguard.dao.CategoryDAO;
import com.moneyguard.moneyguard.repository.CategoryRepository;
import com.moneyguard.moneyguard.request.CreateCategoryRequest;
import com.moneyguard.moneyguard.request.UpdateCategoryRequest;
import com.moneyguard.moneyguard.response.CategoryDetailResponse;
import com.moneyguard.moneyguard.response.DropdownListResource;
import com.moneyguard.moneyguard.response.MessageResponse;
import com.moneyguard.moneyguard.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("categories")
@CrossOrigin
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;

    @PostMapping("")
    public CategoryDAO save(@Valid @RequestBody CreateCategoryRequest request) {
        return categoryService.create(request);
    }

    @PutMapping("/{id}")
    public CategoryDAO update(@Valid @PathVariable UUID id, @RequestBody UpdateCategoryRequest request) {
        return categoryService.update(categoryRepository.findById(id), request);
    }

    @DeleteMapping("/{id}")
    public MessageResponse delete(@Valid @PathVariable UUID id) {
        if (categoryService.delete(categoryRepository.findById(id))) {
            return new MessageResponse("Deleted");
        }
        return new MessageResponse("Error. Please try again");
    }

    @GetMapping("/{id}")
    public CategoryDetailResponse view(@Valid @PathVariable UUID id) {
        return categoryService.get(categoryRepository.findById(id));
    }

    @GetMapping("")
    public List<CategoryDAO> list(@Valid @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "search", defaultValue = "") String search, @RequestParam(value = "type", defaultValue = "1") String type) {
        return categoryService.list(page, search, Short.parseShort(type));
    }

    @GetMapping("/select")
    public List<DropdownListResource> list(@RequestParam(value = "search", defaultValue = "") String search, @RequestParam(value = "type", defaultValue = "1") String type) {
        return categoryService.getSelectData(search, Short.parseShort(type));
    }

}
