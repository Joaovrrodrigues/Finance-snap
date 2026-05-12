package com.financesnap.service;

import com.financesnap.model.Expense;
import com.financesnap.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository repository;

    public ExpenseService(ExpenseRepository repository) {
        this.repository = repository;
    }

    public Expense addExpense(Expense expense) {
        return repository.save(expense);
    }

    public List<Expense> getAll() {
        return repository.findAllOrderedByDate();
    }

    public Expense getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Expense> getByCategory(String category) {
        return repository.findByCategory(category);
    }

    public List<Expense> getByAmountRange(BigDecimal min, BigDecimal max) {
        return repository.findByAmountBetween(min, max);
    }

    public BigDecimal sumByCategory(String category) {
        return repository.sumByCategory(category);
    }

    public void updateCategory(Long id, String category) {
        repository.updateCategory(id, category);
    }

    public void deleteExpense(Long id) {
        repository.deleteExpenseById(id);
    }
}