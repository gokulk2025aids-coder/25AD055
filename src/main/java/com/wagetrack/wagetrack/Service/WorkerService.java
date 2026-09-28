package com.wagetrack.wagetrack.Service;

import com.wagetrack.wagetrack.Model.Worker;
import com.wagetrack.wagetrack.Repository.WorkerRepository;
import com.wagetrack.wagetrack.Model.Worker;
import com.wagetrack.wagetrack.Repository.WorkerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkerService {

    private final WorkerRepository workerRepository;

    public WorkerService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    public Worker createWorker(Worker worker) {
        return workerRepository.save(worker);
    }

    public List<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }

    public Worker getWorkerById(Long id) {
        return workerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Worker not found"));
    }

    public Worker updateWorker(Long id, Worker worker) {
        Worker existingWorker = getWorkerById(id);

        existingWorker.setName(worker.getName());
        existingWorker.setDailyWage(worker.getDailyWage());
        existingWorker.setPhone(worker.getPhone());

        return workerRepository.save(existingWorker);
    }

    public void deleteWorker(Long id) {
        Worker worker = getWorkerById(id);
        workerRepository.delete(worker);
    }
}