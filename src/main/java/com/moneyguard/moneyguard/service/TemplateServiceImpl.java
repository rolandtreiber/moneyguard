package com.moneyguard.moneyguard.service;

import com.moneyguard.moneyguard.dao.TemplateDAO;
import com.moneyguard.moneyguard.model.*;
import com.moneyguard.moneyguard.repository.*;
import com.moneyguard.moneyguard.request.CreateTemplateRequest;
import com.moneyguard.moneyguard.request.UpdateTemplateRequest;
import com.moneyguard.moneyguard.response.DropdownListResource;
import com.moneyguard.moneyguard.response.TemplateDetailResponse;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("templateService")
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    TemplateRepository templateRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    AuthService authService;

    @Autowired
    ImportanceLevelRepository importanceLevelRepository;

    @Override
    public TemplateDAO create(CreateTemplateRequest request) throws NotFoundException {
        Template template = new Template();
        BeanUtils.copyProperties(request, template);
        ImportanceLevel importanceLevel = importanceLevelRepository.findById(UUID.fromString(request.getImportanceLevel()));
        if (request.getType() == 2) {
            if (importanceLevel == null) {
                throw new NotFoundException("Invalid importance level");
            }
            template.setImportanceLevel(importanceLevel);
        } else {
            template.setImportanceLevel(null);
        }
        template.setUser(authService.getAuthUser());
        template.setCreatedAt(new Date());
        template.setUpdatedAt(new Date());
        templateRepository.save(template);
        for (String uuid: request.getCategories()) {
            Category category = categoryRepository.findById(UUID.fromString(uuid));
            if (category != null) {
                template.addCategory(category);
            }
        }
        for (String uuid: request.getTags()) {
            Tag tag = tagRepository.findById(UUID.fromString(uuid));
            if (tag != null) {
                template.addTag(tag);
            }
        }
        templateRepository.save(template);
        TemplateDAO templateDAO = new TemplateDAO();
        BeanUtils.copyProperties(template, templateDAO);
        return templateDAO;
    }

    @Override
    public TemplateDetailResponse update(Template template, UpdateTemplateRequest request) throws NotFoundException {
        BeanUtils.copyProperties(request, template);
        template.setUpdatedAt(new Date());
        Set<Category> categories = template.getCategories();
        ImportanceLevel importanceLevel = importanceLevelRepository.findById(UUID.fromString(request.getImportanceLevel()));
        if (request.getType() == 2) {
            if (importanceLevel == null) {
                throw new NotFoundException("Invalid importance level");
            }
            template.setImportanceLevel(importanceLevel);
        } else {
            template.setImportanceLevel(null);
        }
        for (String uuid: request.getCategories()) {
            Category category = categoryRepository.findById(UUID.fromString(uuid));
            if (category != null && !categories.contains(category)) {
                template.addCategory(category);
            }
        }
        Set<Tag> tags = template.getTags();
        for (String uuid: request.getTags()) {
            Tag tag = tagRepository.findById(UUID.fromString(uuid));
            if (tag != null && !tags.contains(tag)) {
                template.addTag(tag);
            }
        }
        templateRepository.saveAndFlush(template);
        categories = new HashSet<>(template.getCategories());
        tags = new HashSet<>(template.getTags());
        for (Category category: categories) {
            if (Arrays.stream(request.getCategories()).noneMatch(category.getId().toString()::equals)) {
                template.removeCategory(category.getId());
            }
        }
        for (Tag tag: tags) {
            if (Arrays.stream(request.getTags()).noneMatch(tag.getId().toString()::equals)) {
                template.removeTag(tag.getId());
            }
        }
        templateRepository.save(template);
        return new TemplateDetailResponse(template);
    }

    @Override
    public Boolean delete(Template template) {
        templateRepository.delete(template);
        return true;
    }

    @Override
    public TemplateDetailResponse get(Template template) {
        return new TemplateDetailResponse(template);
    }

    @Override
    public List<TemplateDAO> list(Integer page, String search) {
        Pageable pageable = PageRequest.of(page, 25);
        Page<Template> pageData = templateRepository.findAllByNameContainsIgnoreCaseAndUserIdOrderByNameAsc(search, authService.getAuthUser().getId(), pageable);
        List<Template> templates = pageData.getContent();

        List<TemplateDAO> daoList = new ArrayList<>();
        templates.forEach(g -> {
            TemplateDAO templateDAO = new TemplateDAO();
            BeanUtils.copyProperties(g, templateDAO);
            daoList.add(templateDAO);
        });
        return daoList;
    }

    @Override
    public List<DropdownListResource> getSelectData(String search) {
        List<Template> guards = templateRepository.findAllByNameContainsIgnoreCaseAndUserIdOrderByNameAsc(search, authService.getAuthUser().getId());
        List<DropdownListResource> dropdownListData = new ArrayList<>();
        guards.forEach(c -> {
            DropdownListResource listItem = new DropdownListResource();
            listItem.setText(c.getName());
            listItem.setValue(c.getId().toString());
            dropdownListData.add(listItem);
        });
        return dropdownListData;
    }
}
