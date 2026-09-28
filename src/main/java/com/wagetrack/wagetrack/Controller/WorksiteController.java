package com.wagetrack.wagetrack.Controller;

import com.wagetrack.wagetrack.Model.Worksite;
import com.wagetrack.wagetrack.Service.WorksiteService;
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
    public Worksite createWorksite(@RequestBody Worksite worksite) {
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
            @RequestBody Worksite worksite) {

        return worksiteService.updateWorksite(id, worksite);
    }

    @DeleteMapping("/{id}")
    public String deleteWorksite(@PathVariable Long id) {

        worksiteService.deleteWorksite(id);

        return "Worksite deleted successfully";
    }
}