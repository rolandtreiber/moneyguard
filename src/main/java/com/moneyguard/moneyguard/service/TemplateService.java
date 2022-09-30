package com.moneyguard.moneyguard.service;

import com.moneyguard.moneyguard.dao.TemplateDAO;
import com.moneyguard.moneyguard.model.Template;
import com.moneyguard.moneyguard.request.CreateTemplateRequest;
import com.moneyguard.moneyguard.request.UpdateTemplateRequest;
import com.moneyguard.moneyguard.response.DropdownListResource;
import com.moneyguard.moneyguard.response.TemplateDetailResponse;
import javassist.NotFoundException;

import java.util.List;

public interface TemplateService {

    TemplateDAO create(CreateTemplateRequest request) throws NotFoundException;
    TemplateDetailResponse update(Template template, UpdateTemplateRequest request) throws NotFoundException;
    Boolean delete(Template template);
    TemplateDetailResponse get(Template template);
    List<TemplateDAO> list(Integer page, String search);
    List<DropdownListResource> getSelectData(String search);

}
