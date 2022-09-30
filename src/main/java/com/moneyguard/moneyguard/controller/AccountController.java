package com.moneyguard.moneyguard.controller;

import com.moneyguard.moneyguard.request.DashboardRequest;
import com.moneyguard.moneyguard.request.UpdateAccountRequest;
import com.moneyguard.moneyguard.response.DashboardResponse;
import com.moneyguard.moneyguard.response.GenericSuccessResponse;
import com.moneyguard.moneyguard.response.UserDetailResponse;
import com.moneyguard.moneyguard.service.AuthService;
import com.moneyguard.moneyguard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.ws.rs.Produces;
import java.text.ParseException;

@RestController
@RequestMapping("account")
@CrossOrigin
public class AccountController {

    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    @GetMapping("")
    public UserDetailResponse getAccountDetails() {
        return new UserDetailResponse(authService.getAuthUser());
    }

    @PutMapping("")
    public UserDetailResponse save(@Valid @RequestBody UpdateAccountRequest updateAccountRequest) {
        return userService.updateAccount(updateAccountRequest, authService.getAuthUser());
    }

    @PutMapping("/avatar")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public GenericSuccessResponse updateAvatar(@RequestParam("file") MultipartFile file) {
        return new GenericSuccessResponse("File uploaded");
    }

    @PostMapping("/dashboard")
    public DashboardResponse dashboard(@Valid @RequestBody DashboardRequest request) throws ParseException {
        return userService.getDashboard(authService.getAuthUser(), request);
    }
}
