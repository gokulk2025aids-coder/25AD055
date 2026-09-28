package com.wagetrack.wagetrack.Model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long workerId;

    private Long worksiteId;

    private LocalDate date;

    private String status;

    public Attendance() {
    }

    public Attendance(Long workerId, Long worksiteId,
                      LocalDate date, String status) {
        this.workerId = workerId;
        this.worksiteId = worksiteId;
        this.date = date;
        this.status = status;
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

    public Long getWorksiteId() {
        return worksiteId;
    }

    public void setWorksiteId(Long worksiteId) {
        this.worksiteId = worksiteId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}