package com.wagetrack.wagetrack.Controller;

import com.wagetrack.wagetrack.Model.Worksite;
import com.wagetrack.wagetrack.Service.WorksiteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/worksites")
public class WorksiteController {

    private final WorksiteService worksiteService;

    public WorksiteController(WorksiteService worksiteService) {
        this.worksiteService = worksiteService;
    }

    @PostMapping
    public Worksite createWorksite(@Valid @RequestBody Worksite worksite) {
        return worksiteService.createWorksite(worksite);
    }

    @GetMapping
    public List<Worksite> getAllWorksites() {
        return worksiteService.getAllWorksites();
    }

    @GetMapping("/{id}")
    public Worksite getWorksiteById(@PathVariable Long id) {
        return worksiteService.getWorksiteById(id);
    }

    @PutMapping("/{id}")
    public Worksite updateWorksite(
            @PathVariable Long id,
            @Valid @RequestBody Worksite worksite) {

        return worksiteService.updateWorksite(id, worksite);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWorksite(@PathVariable Long id) {

        worksiteService.deleteWorksite(id);

        return ResponseEntity.ok("Worksite deleted successfully");
    }
}