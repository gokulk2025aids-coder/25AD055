package com.wagetrack.wagetrack.Model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "payment_records")
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long workerId;
    private LocalDate date;

    private Double regularAmount;
    private Double overtimeHours;
    private Double overtimeAmount;
    private Double totalAmount;

    private String paymentStatus;

    public Record() {
    }

    public Record(Long workerId, LocalDate date, Double regularAmount,
                  Double overtimeHours, Double overtimeAmount,
                  Double totalAmount, String paymentStatus) {

        this.workerId = workerId;
        this.date = date;
        this.regularAmount = regularAmount;
        this.overtimeHours = overtimeHours;
        this.overtimeAmount = overtimeAmount;
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Long workerId) {
        this.workerId = workerId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getRegularAmount() {
        return regularAmount;
    }

    public void setRegularAmount(Double regularAmount) {
        this.regularAmount = regularAmount;
    }

    public Double getOvertimeHours() {
        return overtimeHours;
    }

    public void setOvertimeHours(Double overtimeHours) {
        this.overtimeHours = overtimeHours;
    }

    public Double getOvertimeAmount() {
        return overtimeAmount;
    }

    public void setOvertimeAmount(Double overtimeAmount) {
        this.overtimeAmount = overtimeAmount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}