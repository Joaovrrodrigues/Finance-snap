package com.financesnap.controller;

import com.financesnap.model.Expense;
import com.financesnap.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ResponseEntity<Expense> createExpense(@RequestBody Expense expense) {
        return ResponseEntity.status(HttpStatus.CREATED).body(expenseService.addExpense(expense));
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getAll() {
        return ResponseEntity.ok(expenseService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> getById(@PathVariable Long id) {
        Expense expense = expenseService.getById(id);
        return expense != null ? ResponseEntity.ok(expense) : ResponseEntity.notFound().build();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Expense>> getByCategory(@PathVariable String category) {
        return ResponseEntity.ok(expenseService.getByCategory(category));
    }

    @GetMapping("/range")
    public ResponseEntity<List<Expense>> getByRange(@RequestParam BigDecimal min,
                                                    @RequestParam BigDecimal max) {
        return ResponseEntity.ok(expenseService.getByAmountRange(min, max));
    }

    @GetMapping("/sum/{category}")
    public ResponseEntity<BigDecimal> sumByCategory(@PathVariable String category) {
        return ResponseEntity.ok(expenseService.sumByCategory(category));
    }

    @PatchMapping("/{id}/category")
    public ResponseEntity<Void> updateCategory(@PathVariable Long id,
                                               @RequestParam String category) {
        expenseService.updateCategory(id, category);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }
}