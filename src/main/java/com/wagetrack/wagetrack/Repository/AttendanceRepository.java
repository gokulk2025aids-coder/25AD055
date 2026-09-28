package com.wagetrack.wagetrack.Repository;

import com.wagetrack.wagetrack.Model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
}