package com.moneyguard.moneyguard.request;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class RetrieveTransactionsRequest {

    @NotNull(message = "Page is mandatory")
    private Integer page;
    @NotNull(message = "Search is mandatory")
    private String search;
    private String[] tags;
    private String[] categories;
    private String[] importanceLevels;
    @NotNull(message = "Types is mandatory")
    private String[] types;
    @NotNull(message = "Start date is mandatory")
    private Date startDate;

    private Date endDate = new Date();

    private String orderBy = "name";
    private String order = "asc";


    public RetrieveTransactionsRequest(Integer page,
                                       String search,
                                       @NotNull(message = "Tags is mandatory")
                                               String[] tags,
                                       @NotNull(message = "Categories is mandatory")
                                               String[] categories,
                                       @NotNull(message = "Importance levels is mandatory")
                                               String[] importanceLevels,
                                       @NotNull(message = "Types is mandatory")
                                               String[] types,
                                       Date startDate,
                                       Date endDate,
                                       String orderBy,
                                       String order
    ) {
        this.page = page;
        this.search = search;
        this.tags = tags;
        this.categories = categories;
        this.importanceLevels = importanceLevels;
        this.types = types;
        this.startDate = startDate;
        this.endDate = endDate;
        this.orderBy = orderBy;
        this.order = order;
    }

    public RetrieveTransactionsRequest() {
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public String[] getImportanceLevels() {
        return importanceLevels;
    }

    public void setImportanceLevels(String[] importanceLevels) {
        this.importanceLevels = importanceLevels;
    }

    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }

}
