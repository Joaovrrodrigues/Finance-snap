package com.financesnap.repository;

import com.financesnap.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.time.LocalDate;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO expenses (description, amount, category, date, receipt_image_path) " +
            "VALUES (:description, :amount, :category, :date, :receiptImagePath)", nativeQuery = true)
    void insertExpense(@Param("description") String description,
                       @Param("amount") BigDecimal amount,
                       @Param("category") String category,
                       @Param("date") LocalDate date,
                       @Param("receiptImagePath") String receiptImagePath);
    @Query(value = "SELECT * FROM expenses WHERE category = :category", nativeQuery = true)
    List<Expense> findByCategory(@Param("category") String category);

    @Query(value = "SELECT * FROM expenses WHERE amount >= :min AND amount <= :max", nativeQuery = true)
    List<Expense> findByAmountBetween(@Param("min") BigDecimal min, @Param("max") BigDecimal max);

    @Query(value = "SELECT * FROM expenses ORDER BY date DESC", nativeQuery = true)
    List<Expense> findAllOrderedByDate();

    @Query(value = "SELECT SUM(amount) FROM expenses WHERE category = :category", nativeQuery = true)
    BigDecimal sumByCategory(@Param("category") String category);

    @Modifying
    @Transactional
    @Query(value = "UPDATE expenses SET category = :category WHERE id = :id", nativeQuery = true)
    void updateCategory(@Param("id") Long id, @Param("category") String category);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM expenses WHERE id = :id", nativeQuery = true)
    void deleteExpenseById(@Param("id") Long id);
}