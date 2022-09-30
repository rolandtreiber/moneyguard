package com.moneyguard.moneyguard.controller;

import com.moneyguard.moneyguard.model.ERole;
import com.moneyguard.moneyguard.model.Role;
import com.moneyguard.moneyguard.model.User;
import com.moneyguard.moneyguard.repository.RoleRepository;
import com.moneyguard.moneyguard.repository.UserRepository;
import com.moneyguard.moneyguard.request.LoginRequest;
import com.moneyguard.moneyguard.request.RegisterRequest;
import com.moneyguard.moneyguard.response.JwtResponse;
import com.moneyguard.moneyguard.response.MessageResponse;
import com.moneyguard.moneyguard.security.jwt.JwtUtils;
import com.moneyguard.moneyguard.security.jwt.services.UserDetailsImpl;
import com.moneyguard.moneyguard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("auth")
@CrossOrigin
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already taken!"));
        }
        // Create new user's account
        User user = new User();
        user.setCurrencySymbol("£");
        user.setCurrencyPlacement(true);
        user.setEmail(signUpRequest.getEmail());
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setPrefix(signUpRequest.getPrefix());
        user.setName(signUpRequest.getPrefix()+" "+signUpRequest.getFirstName()+" "+signUpRequest.getLastName());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        Set<String> strRoles = Collections.singleton("ROLE_USER");
        Set<Role> roles = new HashSet<>();
        Optional<Role> role = roleRepository.findByName(ERole.ROLE_USER);
        if (role.isEmpty()) {
            Role newRole = new Role();
            newRole.setName(ERole.ROLE_USER);
            roleRepository.save(newRole);
        }

        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
        userService.generateDefaultImportanceLevels(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

}
