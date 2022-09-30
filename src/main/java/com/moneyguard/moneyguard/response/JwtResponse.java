package com.moneyguard.moneyguard.response;

import java.util.List;
import java.util.UUID;

public class JwtResponse {

    private String jwt;
    private String username;
    private String email;
    private List<String> roles;

    public JwtResponse(String jwt, UUID id, String username, String email, List<String> roles) {
        this.jwt = jwt;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
