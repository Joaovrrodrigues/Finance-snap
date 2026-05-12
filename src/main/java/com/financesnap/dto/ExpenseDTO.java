package com.financesnap.finance_snap.dto;

public class ExpenseDTO {
    private String description;
    private Double amount;
    private String category;
    private String date;

    // getters e setters
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}