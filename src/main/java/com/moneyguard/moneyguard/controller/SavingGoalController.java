package com.moneyguard.moneyguard.controller;

import com.moneyguard.moneyguard.dao.SavingGoalDAO;
import com.moneyguard.moneyguard.repository.SavingGoalRepository;
import com.moneyguard.moneyguard.request.CreateSavingGoalRequest;
import com.moneyguard.moneyguard.request.UpdateSavingGoalRequest;
import com.moneyguard.moneyguard.response.DropdownListResource;
import com.moneyguard.moneyguard.response.MessageResponse;
import com.moneyguard.moneyguard.response.SavingGoalDetailResponse;
import com.moneyguard.moneyguard.service.SavingGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("saving-goals")
@CrossOrigin
public class SavingGoalController {

    @Autowired
    SavingGoalService savingGoalService;

    @Autowired
    SavingGoalRepository savingGoalRepository;

    @PostMapping("")
    public SavingGoalDAO save(@Valid @RequestBody CreateSavingGoalRequest request) {
        return savingGoalService.create(request);
    }

    @PutMapping("/{id}")
    public SavingGoalDetailResponse update(@Valid @PathVariable UUID id, @RequestBody UpdateSavingGoalRequest request) {
        return savingGoalService.update(savingGoalRepository.findById(id), request);
    }

    @DeleteMapping("/{id}")
    public MessageResponse delete(@Valid @PathVariable UUID id) {
        if (savingGoalService.delete(savingGoalRepository.findById(id))) {
            return new MessageResponse("Deleted");
        }
        return new MessageResponse("Error. Please try again");
    }

    @GetMapping("/{id}")
    public SavingGoalDetailResponse view(@Valid @PathVariable UUID id) {
        return savingGoalService.get(savingGoalRepository.findById(id));
    }

    @GetMapping("")
    public List<SavingGoalDAO> list(@Valid @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "search", defaultValue = "") String search) {
        return savingGoalService.list(page, search);
    }

    @GetMapping("/select")
    public List<DropdownListResource> list(@RequestParam(value = "search", defaultValue = "") String search) {
        return savingGoalService.getSelectData(search);
    }
}
