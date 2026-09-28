package com.wagetrack.wagetrack.Repository;

import com.wagetrack.wagetrack.Model.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long> {
}