package com.moneyguard.moneyguard.controller;

import com.moneyguard.moneyguard.dao.GuardDAO;
import com.moneyguard.moneyguard.repository.GuardRepository;
import com.moneyguard.moneyguard.request.CreateGuardRequest;
import com.moneyguard.moneyguard.request.UpdateGuardRequest;
import com.moneyguard.moneyguard.response.DropdownListResource;
import com.moneyguard.moneyguard.response.GuardDetailResponse;
import com.moneyguard.moneyguard.response.MessageResponse;
import com.moneyguard.moneyguard.service.AuthService;
import com.moneyguard.moneyguard.service.GuardService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("guards")
@CrossOrigin
public class GuardController {

    @Autowired
    GuardService guardService;

    @Autowired
    AuthService authService;

    @Autowired
    GuardRepository guardRepository;

    @PostMapping("")
    public GuardDAO save(@Valid @RequestBody CreateGuardRequest request) throws NotFoundException {
        return guardService.create(request);
    }

    @PutMapping("/{id}")
    public GuardDetailResponse update(@Valid @PathVariable UUID id, @RequestBody UpdateGuardRequest request) throws NotFoundException {
        return guardService.update(guardRepository.findById(id), request);
    }

    @DeleteMapping("/{id}")
    public MessageResponse delete(@Valid @PathVariable UUID id) {
        if (guardService.delete(guardRepository.findById(id))) {
            return new MessageResponse("Deleted");
        }
        return new MessageResponse("Error. Please try again");
    }

    @GetMapping("/{id}")
    public GuardDetailResponse view(@Valid @PathVariable UUID id) {
        return guardService.get(guardRepository.findById(id));
    }

    @GetMapping("")
    public List<GuardDAO> list(@Valid @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "search", defaultValue = "") String search) {
        return guardService.list(page, search);
    }

    @GetMapping("/select")
    public List<DropdownListResource> list(@RequestParam(value = "search", defaultValue = "") String search) {
        return guardService.getSelectData(search);
    }
}
