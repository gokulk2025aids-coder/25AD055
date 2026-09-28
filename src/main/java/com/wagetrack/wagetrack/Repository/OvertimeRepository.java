package com.wagetrack.wagetrack.Repository;

import com.wagetrack.wagetrack.Model.Overtime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OvertimeRepository extends JpaRepository<Overtime, Long> {

    List<Overtime> findByWorkerId(Long workerId);
}