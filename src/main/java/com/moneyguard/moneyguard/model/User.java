package com.moneyguard.moneyguard.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(	name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
public class User {

    public User() {
    }

    @Id
    @Type(type = "uuid-char")
    private final UUID id = UUID.randomUUID();
    private String prefix;
    private String name;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String currencySymbol;
    private Boolean currencyPlacement;
    private Date createdAt;
    private Date updatedAt;

    @OneToMany(mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            fetch=FetchType.LAZY)
    private Set<Transaction> transactions;

    @OneToMany(mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            fetch=FetchType.LAZY)
    private Set<Template> templates;

    @OneToMany(mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            fetch=FetchType.LAZY)
    private Set<ImportanceLevel> importanceLevels;

    @OneToMany(mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            fetch=FetchType.LAZY)
    private Set<RecurringTransaction> recurringTransactions;

    @OneToMany(mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            fetch=FetchType.LAZY)
    private Set<SavingGoal> savingGoals;

    @OneToMany(mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            fetch=FetchType.LAZY)
    private Set<Category> categories;

    @OneToMany(mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            fetch=FetchType.LAZY)
    private Set<Tag> tags;

    @OneToMany(mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            fetch=FetchType.LAZY)
    private Set<Guard> guards;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public UUID getId() {
        return id;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public Boolean getCurrencyPlacement() {
        return currencyPlacement;
    }

    public void setCurrencyPlacement(Boolean currencyPlacement) {
        this.currencyPlacement = currencyPlacement;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Set<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(Set<Template> templates) {
        this.templates = templates;
    }

    public Set<ImportanceLevel> getImportanceLevels() {
        return importanceLevels;
    }

    public void setImportanceLevels(Set<ImportanceLevel> importanceLevels) {
        this.importanceLevels = importanceLevels;
    }

    public Set<RecurringTransaction> getRecurringTransactions() {
        return recurringTransactions;
    }

    public void setRecurringTransactions(Set<RecurringTransaction> recurringTransactions) {
        this.recurringTransactions = recurringTransactions;
    }

    public Set<SavingGoal> getSavingGoals() {
        return savingGoals;
    }

    public void setSavingGoals(Set<SavingGoal> savingGoals) {
        this.savingGoals = savingGoals;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<Guard> getGuards() {
        return guards;
    }

    public void setGuards(Set<Guard> guards) {
        this.guards = guards;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", prefix='" + prefix + '\'' +
                ", name='" + name + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", currencySymbol='" + currencySymbol + '\'' +
                ", currencyPlacement=" + currencyPlacement +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
