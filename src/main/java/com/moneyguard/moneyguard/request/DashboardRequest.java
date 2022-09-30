package com.moneyguard.moneyguard.request;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class DashboardRequest {

    @NotNull(message = "Start date field is mandatory")
    private Date startDate;

    @NotNull(message = "End date field is mandatory")
    private Date endDate;

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
}
