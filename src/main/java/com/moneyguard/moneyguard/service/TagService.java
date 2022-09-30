package com.moneyguard.moneyguard.service;

import com.moneyguard.moneyguard.dao.TagDAO;
import com.moneyguard.moneyguard.model.Tag;
import com.moneyguard.moneyguard.request.CreateTagRequest;
import com.moneyguard.moneyguard.request.UpdateCategoryRequest;
import com.moneyguard.moneyguard.response.DropdownListResource;
import com.moneyguard.moneyguard.response.TagDetailResponse;

import java.util.List;

public interface TagService {

    TagDAO create(CreateTagRequest request);
    TagDAO update(Tag tag, UpdateCategoryRequest request);
    Boolean delete(Tag tag);
    TagDetailResponse get(Tag tag);
    List<TagDAO> list(Integer page, String search, Short type);
    List<DropdownListResource> getSelectData(String search, Short type);

}
