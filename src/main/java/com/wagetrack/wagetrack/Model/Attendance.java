package com.wagetrack.wagetrack.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @ManyToOne
    @JoinColumn(name = "worksite_id", nullable = false)
    private Worksite worksite;

    @NotNull(message = "Attendance date is required")
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Attendance status is required")
    private AttendanceStatus status;

    public Attendance() {
    }

    public Attendance(
            Worker worker,
            Worksite worksite,
            LocalDate date,
            AttendanceStatus status) {

        this.worker = worker;
        this.worksite = worksite;
        this.date = date;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Worksite getWorksite() {
        return worksite;
    }

    public void setWorksite(Worksite worksite) {
        this.worksite = worksite;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public void setStatus(AttendanceStatus status) {
        this.status = status;
    }
}