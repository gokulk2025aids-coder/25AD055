package com.wagetrack.wagetrack.Controller;

import com.wagetrack.wagetrack.Model.Worker;
import com.wagetrack.wagetrack.Service.WorkerService;
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
    public Worker createWorker(@RequestBody Worker worker) {
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
            @RequestBody Worker worker) {

        return workerService.updateWorker(id, worker);
    }

    @DeleteMapping("/{id}")
    public String deleteWorker(@PathVariable Long id) {
        boolean deleted = workerService.deleteWorker(id);

        if (!deleted) {
            return "Worker with ID " + id + " not found";
        }

        return "Worker deleted successfully";
    }
}