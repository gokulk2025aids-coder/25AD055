package com.wagetrack.wagetrack.Repository;

import com.wagetrack.wagetrack.Model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
}