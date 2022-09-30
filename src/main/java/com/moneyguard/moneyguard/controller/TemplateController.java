package com.moneyguard.moneyguard.controller;

import com.moneyguard.moneyguard.dao.TemplateDAO;
import com.moneyguard.moneyguard.repository.TemplateRepository;
import com.moneyguard.moneyguard.request.CreateTemplateRequest;
import com.moneyguard.moneyguard.request.UpdateTemplateRequest;
import com.moneyguard.moneyguard.response.DropdownListResource;
import com.moneyguard.moneyguard.response.MessageResponse;
import com.moneyguard.moneyguard.response.TemplateDetailResponse;
import com.moneyguard.moneyguard.service.TemplateService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("templates")
@CrossOrigin
public class TemplateController {

    @Autowired
    TemplateService templateService;

    @Autowired
    TemplateRepository templateRepository;

    @PostMapping("")
    public TemplateDAO save(@Valid @RequestBody CreateTemplateRequest request) throws NotFoundException {
        return templateService.create(request);
    }

    @PutMapping("/{id}")
    public TemplateDetailResponse update(@Valid @PathVariable UUID id, @RequestBody UpdateTemplateRequest request) throws NotFoundException {
        return templateService.update(templateRepository.findById(id), request);
    }

    @DeleteMapping("/{id}")
    public MessageResponse delete(@Valid @PathVariable UUID id) {
        if (templateService.delete(templateRepository.findById(id))) {
            return new MessageResponse("Deleted");
        }
        return new MessageResponse("Error. Please try again");
    }

    @GetMapping("/{id}")
    public TemplateDetailResponse view(@Valid @PathVariable UUID id) {
        return templateService.get(templateRepository.findById(id));
    }

    @GetMapping("")
    public List<TemplateDAO> list(@Valid @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "search", defaultValue = "") String search) {
        return templateService.list(page, search);
    }

    @GetMapping("/select")
    public List<DropdownListResource> list(@RequestParam(value = "search", defaultValue = "") String search) {
        return templateService.getSelectData(search);
    }
}
