package com.wagetrack.wagetrack.Service;

import com.wagetrack.wagetrack.Model.Worksite;
import com.wagetrack.wagetrack.Repository.WorksiteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorksiteService {

    private final WorksiteRepository worksiteRepository;

    public WorksiteService(WorksiteRepository worksiteRepository) {
        this.worksiteRepository = worksiteRepository;
    }

    public Worksite createWorksite(Worksite worksite) {
        return worksiteRepository.save(worksite);
    }

    public List<Worksite> getAllWorksites() {
        return worksiteRepository.findAll();
    }

    public Worksite getWorksiteById(Long id) {
        return worksiteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Worksite not found"));
    }

    public Worksite updateWorksite(Long id, Worksite worksite) {

        Worksite existingWorksite = getWorksiteById(id);

        existingWorksite.setName(worksite.getName());
        existingWorksite.setLocation(worksite.getLocation());

        return worksiteRepository.save(existingWorksite);
    }

    public void deleteWorksite(Long id) {

        Worksite worksite = getWorksiteById(id);

        worksiteRepository.delete(worksite);
    }
}