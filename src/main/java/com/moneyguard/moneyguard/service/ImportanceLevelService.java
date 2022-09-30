package com.moneyguard.moneyguard.service;

import com.moneyguard.moneyguard.dao.ImportanceLevelDAO;
import com.moneyguard.moneyguard.model.ImportanceLevel;
import com.moneyguard.moneyguard.request.CreateImportanceLevelRequest;
import com.moneyguard.moneyguard.request.UpdateImportanceLevelRequest;
import com.moneyguard.moneyguard.response.DropdownListResource;
import com.moneyguard.moneyguard.response.ImportanceLevelDetailResponse;

import java.util.List;

public interface ImportanceLevelService {

    ImportanceLevelDAO create(CreateImportanceLevelRequest request);
    ImportanceLevelDAO update(ImportanceLevel importanceLevel, UpdateImportanceLevelRequest request);
    Boolean delete(ImportanceLevel importanceLevel);
    ImportanceLevelDetailResponse get(ImportanceLevel importanceLevel);
    List<ImportanceLevelDAO> list(Integer page, String search);
    List<DropdownListResource> getSelectData(String search);

}
