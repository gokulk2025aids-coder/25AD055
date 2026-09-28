package com.wagetrack.wagetrack.Controller;

import com.wagetrack.wagetrack.Model.Worker;
import com.wagetrack.wagetrack.Service.WorkerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workers")
public class WorkerController {

    private final WorkerService workerService;

    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @PostMapping
    public Worker createWorker(@Valid @RequestBody Worker worker) {
        return workerService.createWorker(worker);
    }

    @GetMapping
    public List<Worker> getAllWorkers() {
        return workerService.getAllWorkers();
    }

    @GetMapping("/{id}")
    public Worker getWorkerById(@PathVariable Long id) {
        return workerService.getWorkerById(id);
    }

    @PutMapping("/{id}")
    public Worker updateWorker(
            @PathVariable Long id,
            @Valid @RequestBody Worker worker) {

        return workerService.updateWorker(id, worker);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWorker(@PathVariable Long id) {

        workerService.deleteWorker(id);

        return ResponseEntity.ok("Worker deleted successfully");
    }
}