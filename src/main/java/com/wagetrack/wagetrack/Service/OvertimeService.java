package com.wagetrack.wagetrack.Service;

import com.wagetrack.wagetrack.Model.Overtime;
import com.wagetrack.wagetrack.Model.Worker;
import com.wagetrack.wagetrack.Repository.OvertimeRepository;
import com.wagetrack.wagetrack.Repository.WorkerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OvertimeService {

    private static final double STANDARD_WORKING_HOURS = 8.0;
    private static final double OVERTIME_MULTIPLIER = 1.5;

    private final OvertimeRepository overtimeRepository;
    private final WorkerRepository workerRepository;

    public OvertimeService(
            OvertimeRepository overtimeRepository,
            WorkerRepository workerRepository) {

        this.overtimeRepository = overtimeRepository;
        this.workerRepository = workerRepository;
    }

    public Overtime createOvertime(
            Long workerId,
            Overtime overtime) {

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        double totalHours = overtime.getTotalHoursWorked();

        double overtimeHours =
                Math.max(0, totalHours - STANDARD_WORKING_HOURS);

        double hourlyWage =
                worker.getDailyWage() / STANDARD_WORKING_HOURS;

        double overtimePay =
                overtimeHours * hourlyWage * OVERTIME_MULTIPLIER;

        overtime.setWorker(worker);
        overtime.setOvertimeHours(overtimeHours);
        overtime.setOvertimePay(overtimePay);

        return overtimeRepository.save(overtime);
    }

    public List<Overtime> getAllOvertime() {
        return overtimeRepository.findAll();
    }

    public Overtime getOvertimeById(Long id) {
        return overtimeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Overtime record not found"));
    }

    public List<Overtime> getOvertimeByWorker(Long workerId) {

        if (!workerRepository.existsById(workerId)) {
            throw new RuntimeException("Worker not found");
        }

        return overtimeRepository.findByWorkerId(workerId);
    }

    public Overtime updateOvertime(
            Long id,
            Long workerId,
            Overtime overtime) {

        Overtime existingOvertime = getOvertimeById(id);

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        double totalHours = overtime.getTotalHoursWorked();

        double overtimeHours =
                Math.max(0, totalHours - STANDARD_WORKING_HOURS);

        double hourlyWage =
                worker.getDailyWage() / STANDARD_WORKING_HOURS;

        double overtimePay =
                overtimeHours * hourlyWage * OVERTIME_MULTIPLIER;

        existingOvertime.setWorker(worker);
        existingOvertime.setDate(overtime.getDate());
        existingOvertime.setTotalHoursWorked(totalHours);
        existingOvertime.setOvertimeHours(overtimeHours);
        existingOvertime.setOvertimePay(overtimePay);

        return overtimeRepository.save(existingOvertime);
    }

    public void deleteOvertime(Long id) {

        Overtime overtime = getOvertimeById(id);

        overtimeRepository.delete(overtime);
    }
}