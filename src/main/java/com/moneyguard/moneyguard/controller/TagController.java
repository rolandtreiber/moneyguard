package com.moneyguard.moneyguard.controller;

import com.moneyguard.moneyguard.dao.TagDAO;
import com.moneyguard.moneyguard.repository.TagRepository;
import com.moneyguard.moneyguard.request.CreateTagRequest;
import com.moneyguard.moneyguard.request.UpdateCategoryRequest;
import com.moneyguard.moneyguard.response.DropdownListResource;
import com.moneyguard.moneyguard.response.MessageResponse;
import com.moneyguard.moneyguard.response.TagDetailResponse;
import com.moneyguard.moneyguard.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("tags")
@CrossOrigin
public class TagController {

    @Autowired
    TagService tagService;

    @Autowired
    TagRepository tagRepository;

    @PostMapping("")
    public TagDAO save(@Valid @RequestBody CreateTagRequest request) {
        return tagService.create(request);
    }

    @PutMapping("/{id}")
    public TagDAO update(@Valid @PathVariable UUID id, @RequestBody UpdateCategoryRequest request) {
        return tagService.update(tagRepository.findById(id), request);
    }

    @DeleteMapping("/{id}")
    public MessageResponse delete(@Valid @PathVariable UUID id) {
        if (tagService.delete(tagRepository.findById(id))) {
            return new MessageResponse("Deleted");
        }
        return new MessageResponse("Error. Please try again");
    }

    @GetMapping("/{id}")
    public TagDetailResponse view(@Valid @PathVariable UUID id) {
        return tagService.get(tagRepository.findById(id));
    }

    @GetMapping("")
    public List<TagDAO> list(@Valid @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "search", defaultValue = "") String search, @RequestParam(value = "type", defaultValue = "1") String type) {
        return tagService.list(page, search, Short.parseShort(type));
    }

    @GetMapping("/select")
    public List<DropdownListResource> list(@RequestParam(value = "search", defaultValue = "") String search, @RequestParam(value = "type", defaultValue = "1") String type) {
        return tagService.getSelectData(search, Short.parseShort(type));
    }

}
