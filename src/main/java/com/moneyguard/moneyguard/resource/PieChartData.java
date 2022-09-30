package com.moneyguard.moneyguard.resource;

import java.util.HashSet;
import java.util.Set;

public class PieChartData {

    private Set<ChartDataElement> elements = new HashSet<>();
    private Double totalValue;

    public Set<ChartDataElement> getElements() {
        return elements;
    }

    public void setElements(Set<ChartDataElement> elements) {
        this.elements = elements;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }
}
