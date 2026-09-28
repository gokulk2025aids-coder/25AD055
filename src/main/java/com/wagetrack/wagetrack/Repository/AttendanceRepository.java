package com.wagetrack.wagetrack.Repository;

import com.wagetrack.wagetrack.Model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository
        extends JpaRepository<Attendance, Long> {

    boolean existsByWorkerIdAndWorksiteIdAndDate(
            Long workerId,
            Long worksiteId,
            LocalDate date
    );

    List<Attendance> findByWorkerId(Long workerId);

    List<Attendance> findByWorksiteId(Long worksiteId);
}