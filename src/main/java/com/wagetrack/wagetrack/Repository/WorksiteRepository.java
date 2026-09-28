package com.wagetrack.wagetrack.Repository;

import com.wagetrack.wagetrack.Model.Worksite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorksiteRepository extends JpaRepository<Worksite, Long> {
}