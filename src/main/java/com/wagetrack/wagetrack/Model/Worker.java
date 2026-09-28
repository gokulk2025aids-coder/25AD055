package com.wagetrack.wagetrack.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "workers")
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private double dailyWage;

    private String phone;

    public Worker() {
    }

    public Worker(String name, double dailyWage, String phone) {
        this.name = name;
        this.dailyWage = dailyWage;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDailyWage() {
        return dailyWage;
    }

    public void setDailyWage(double dailyWage) {
        this.dailyWage = dailyWage;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}