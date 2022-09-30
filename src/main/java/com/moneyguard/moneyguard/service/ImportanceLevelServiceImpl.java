package com.moneyguard.moneyguard.service;

import com.moneyguard.moneyguard.dao.ImportanceLevelDAO;
import com.moneyguard.moneyguard.model.ImportanceLevel;
import com.moneyguard.moneyguard.repository.ImportanceLevelRepository;
import com.moneyguard.moneyguard.repository.TransactionRepository;
import com.moneyguard.moneyguard.request.CreateImportanceLevelRequest;
import com.moneyguard.moneyguard.request.UpdateImportanceLevelRequest;
import com.moneyguard.moneyguard.response.DropdownListResource;
import com.moneyguard.moneyguard.response.ImportanceLevelDetailResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("importanceLevelService")
public class ImportanceLevelServiceImpl implements ImportanceLevelService {

    @Autowired
    ImportanceLevelRepository importanceLevelRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AuthService authService;

    @Override
    public ImportanceLevelDAO create(CreateImportanceLevelRequest request) {
        ImportanceLevel importanceLevel = new ImportanceLevel();
        BeanUtils.copyProperties(request, importanceLevel);
        importanceLevel.setUser(authService.getAuthUser());
        importanceLevel.setCreatedAt(new Date());
        importanceLevel.setUpdatedAt(new Date());
        importanceLevelRepository.save(importanceLevel);

        ImportanceLevelDAO importanceLevelDAO = new ImportanceLevelDAO();
        BeanUtils.copyProperties(importanceLevel, importanceLevelDAO);
        return importanceLevelDAO;
    }

    @Override
    public ImportanceLevelDAO update(ImportanceLevel importanceLevel, UpdateImportanceLevelRequest request) {
        BeanUtils.copyProperties(request, importanceLevel);
        importanceLevel.setUpdatedAt(new Date());
        importanceLevelRepository.save(importanceLevel);

        ImportanceLevelDAO importanceLevelDAO = new ImportanceLevelDAO();
        BeanUtils.copyProperties(importanceLevel, importanceLevelDAO);
        return importanceLevelDAO;
    }

    @Override
    public Boolean delete(ImportanceLevel importanceLevel) {
        importanceLevelRepository.delete(importanceLevel);
        return true;
    }

    @Override
    public ImportanceLevelDetailResponse get(ImportanceLevel importanceLevel) {
        return new ImportanceLevelDetailResponse(importanceLevel, transactionRepository);
    }

    @Override
    public List<ImportanceLevelDAO> list(Integer page, String search) {
        Pageable pageable = PageRequest.of(page, 25);
        Page<ImportanceLevel> pageData = importanceLevelRepository.findAllByNameContainsIgnoreCaseAndUserIdOrderByLevelDesc(search, authService.getAuthUser().getId(), pageable);
        List<ImportanceLevel> importanceLevels = pageData.getContent();

        List<ImportanceLevelDAO> daoList = new ArrayList<>();
        importanceLevels.forEach(c -> {
            ImportanceLevelDAO importanceLevelDAO = new ImportanceLevelDAO();
            importanceLevelDAO.setName(c.getName());
            importanceLevelDAO.setCreatedAt(c.getCreatedAt());
            importanceLevelDAO.setUpdatedAt(c.getUpdatedAt());
            importanceLevelDAO.setId(c.getId());
            importanceLevelDAO.setLevel(c.getLevel());
            daoList.add(importanceLevelDAO);
        });
        return daoList;
    }

    @Override
    public List<DropdownListResource> getSelectData(String search) {
        List<ImportanceLevel> importanceLevels = importanceLevelRepository.findAllByNameContainsIgnoreCaseAndUserIdOrderByLevelDesc(search, authService.getAuthUser().getId());
        List<DropdownListResource> dropdownListData = new ArrayList<>();
        importanceLevels.forEach(c -> {
            DropdownListResource listItem = new DropdownListResource();
            listItem.setText(c.getName());
            listItem.setValue(c.getId().toString());
            dropdownListData.add(listItem);
        });
        return dropdownListData;
    }
}
