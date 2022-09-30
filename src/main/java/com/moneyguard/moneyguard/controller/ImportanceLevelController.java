package com.moneyguard.moneyguard.controller;

import com.moneyguard.moneyguard.dao.ImportanceLevelDAO;
import com.moneyguard.moneyguard.repository.ImportanceLevelRepository;
import com.moneyguard.moneyguard.request.CreateImportanceLevelRequest;
import com.moneyguard.moneyguard.request.UpdateImportanceLevelRequest;
import com.moneyguard.moneyguard.response.DropdownListResource;
import com.moneyguard.moneyguard.response.ImportanceLevelDetailResponse;
import com.moneyguard.moneyguard.response.MessageResponse;
import com.moneyguard.moneyguard.service.ImportanceLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("importance-levels")
@CrossOrigin
public class ImportanceLevelController {

    @Autowired
    ImportanceLevelService importanceLevelService;

    @Autowired
    ImportanceLevelRepository importanceLevelRepository;

    @PostMapping("")
    public ImportanceLevelDAO save(@Valid @RequestBody CreateImportanceLevelRequest request) {
        return importanceLevelService.create(request);
    }

    @PutMapping("/{id}")
    public ImportanceLevelDAO update(@Valid @PathVariable UUID id, @RequestBody UpdateImportanceLevelRequest request) {
        return importanceLevelService.update(importanceLevelRepository.findById(id), request);
    }

    @DeleteMapping("/{id}")
    public MessageResponse delete(@Valid @PathVariable UUID id) {
        if (importanceLevelService.delete(importanceLevelRepository.findById(id))) {
            return new MessageResponse("Deleted");
        }
        return new MessageResponse("Error. Please try again");
    }

    @GetMapping("/{id}")
    public ImportanceLevelDetailResponse view(@Valid @PathVariable UUID id) {
        return importanceLevelService.get(importanceLevelRepository.findById(id));
    }

    @GetMapping("")
    public List<ImportanceLevelDAO> list(@Valid @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "search", defaultValue = "") String search) {
        return importanceLevelService.list(page, search);
    }

    @GetMapping("/select")
    public List<DropdownListResource> list(@RequestParam(value = "search", defaultValue = "") String search) {
        return importanceLevelService.getSelectData(search);
    }
}
