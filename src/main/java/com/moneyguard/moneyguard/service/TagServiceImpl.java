package com.moneyguard.moneyguard.service;

import com.moneyguard.moneyguard.dao.TagDAO;
import com.moneyguard.moneyguard.model.Tag;
import com.moneyguard.moneyguard.repository.TagRepository;
import com.moneyguard.moneyguard.repository.TransactionRepository;
import com.moneyguard.moneyguard.request.CreateTagRequest;
import com.moneyguard.moneyguard.request.UpdateCategoryRequest;
import com.moneyguard.moneyguard.response.DropdownListResource;
import com.moneyguard.moneyguard.response.TagDetailResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("tagService")
public class TagServiceImpl implements TagService {

    @Autowired
    AuthService authService;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public TagDAO create(CreateTagRequest request) {
        Tag tag = new Tag();
        tag.setUser(authService.getAuthUser());
        tag.setName(request.getName());
        tag.setType(request.getType());
        tag.setCreatedAt(new Date());
        tag.setUpdatedAt(new Date());
        tagRepository.save(tag);
        TagDAO tagDAO = new TagDAO();
        BeanUtils.copyProperties(tag, tagDAO);
        return tagDAO;
    }

    @Override
    public TagDAO update(Tag tag, UpdateCategoryRequest request) {
        tag.setUpdatedAt(new Date());
        tag.setName(request.getName());
        tagRepository.save(tag);

        TagDAO tagDAO = new TagDAO();
        BeanUtils.copyProperties(tag, tagDAO);
        return tagDAO;
    }

    @Override
    public Boolean delete(Tag tag) {
        tagRepository.delete(tag);
        return true;
    }

    @Override
    public TagDetailResponse get(Tag tag) {
        return new TagDetailResponse(tag, transactionRepository);
    }

    @Override
    public List<TagDAO> list(Integer page, String search, Short type) {
        Pageable pageable = PageRequest.of(page, 25);
        Page<Tag> pageData = tagRepository.findAllByNameContainsIgnoreCaseAndUserIdAndTypeOrderByNameAsc(search, authService.getAuthUser().getId(), pageable, type);
        List<Tag> tags = pageData.getContent();

        List<TagDAO> daoList = new ArrayList<>();
        tags.forEach(c -> {
            TagDAO tagDAO = new TagDAO();
            tagDAO.setName(c.getName());
            tagDAO.setType(c.getType());
            tagDAO.setCreatedAt(c.getCreatedAt());
            tagDAO.setUpdatedAt(c.getUpdatedAt());
            tagDAO.setId(c.getId());
            daoList.add(tagDAO);
        });
        return daoList;
    }

    @Override
    public List<DropdownListResource> getSelectData(String search, Short type) {
        List<Tag> tags = tagRepository.findAllByNameContainsIgnoreCaseAndUserIdAndTypeOrderByNameAsc(search, authService.getAuthUser().getId(), type);
        List<DropdownListResource> dropdownListData = new ArrayList<>();
        tags.forEach(c -> {
            DropdownListResource listItem = new DropdownListResource();
            listItem.setText(c.getName());
            listItem.setValue(c.getId().toString());
            dropdownListData.add(listItem);
        });
        return dropdownListData;
    }
}
