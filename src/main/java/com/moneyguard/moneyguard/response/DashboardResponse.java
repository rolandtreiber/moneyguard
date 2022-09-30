package com.moneyguard.moneyguard.response;

import com.moneyguard.moneyguard.resource.PieChartData;
import com.moneyguard.moneyguard.resource.Targetable;

import java.util.Set;

public class DashboardResponse {

    private double weekTotalIn;
    private double weekTotalOut;
    private double weekTotalSaving;
    private double monthTotalIn;
    private double monthTotalOut;
    private double monthTotalSaving;
    private Set<Targetable> guards;
    private Set<Targetable> savingGoals;
    private PieChartData importanceBreakdownChartData;
    private UserDetailResponse user;

    public double getWeekTotalSaving() {
        return weekTotalSaving;
    }

    public void setWeekTotalSaving(double weekTotalSaving) {
        this.weekTotalSaving = weekTotalSaving;
    }

    public double getMonthTotalSaving() {
        return monthTotalSaving;
    }

    public void setMonthTotalSaving(double monthTotalSaving) {
        this.monthTotalSaving = monthTotalSaving;
    }

    public UserDetailResponse getUser() {
        return user;
    }

    public void setUser(UserDetailResponse user) {
        this.user = user;
    }

    public double getWeekTotalIn() {
        return weekTotalIn;
    }

    public void setWeekTotalIn(double weekTotalIn) {
        this.weekTotalIn = weekTotalIn;
    }

    public double getWeekTotalOut() {
        return weekTotalOut;
    }

    public void setWeekTotalOut(double weekTotalOut) {
        this.weekTotalOut = weekTotalOut;
    }

    public double getMonthTotalIn() {
        return monthTotalIn;
    }

    public void setMonthTotalIn(double monthTotalIn) {
        this.monthTotalIn = monthTotalIn;
    }

    public double getMonthTotalOut() {
        return monthTotalOut;
    }

    public void setMonthTotalOut(double monthTotalOut) {
        this.monthTotalOut = monthTotalOut;
    }

    public Set<Targetable> getGuards() {
        return guards;
    }

    public void setGuards(Set<Targetable> guards) {
        this.guards = guards;
    }

    public Set<Targetable> getSavingGoals() {
        return savingGoals;
    }

    public void setSavingGoals(Set<Targetable> savingGoals) {
        this.savingGoals = savingGoals;
    }

    public PieChartData getImportanceBreakdownChartData() {
        return importanceBreakdownChartData;
    }

    public void setImportanceBreakdownChartData(PieChartData importanceBreakdownChartData) {
        this.importanceBreakdownChartData = importanceBreakdownChartData;
    }
}
