package com.wagetrack.wagetrack.Service;

import com.wagetrack.wagetrack.Model.Attendance;
import com.wagetrack.wagetrack.Model.Worker;
import com.wagetrack.wagetrack.Model.Worksite;
import com.wagetrack.wagetrack.Repository.AttendanceRepository;
import com.wagetrack.wagetrack.Repository.WorkerRepository;
import com.wagetrack.wagetrack.Repository.WorksiteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final WorkerRepository workerRepository;
    private final WorksiteRepository worksiteRepository;

    public AttendanceService(
            AttendanceRepository attendanceRepository,
            WorkerRepository workerRepository,
            WorksiteRepository worksiteRepository) {

        this.attendanceRepository = attendanceRepository;
        this.workerRepository = workerRepository;
        this.worksiteRepository = worksiteRepository;
    }

    public Attendance createAttendance(
            Long workerId,
            Long worksiteId,
            Attendance attendance) {

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        Worksite worksite = worksiteRepository.findById(worksiteId)
                .orElseThrow(() -> new RuntimeException("Worksite not found"));

        if (attendanceRepository.existsByWorkerIdAndWorksiteIdAndDate(
                workerId,
                worksiteId,
                attendance.getDate())) {

            throw new RuntimeException(
                    "Attendance already exists for this worker on this date"
            );
        }

        attendance.setWorker(worker);
        attendance.setWorksite(worksite);

        return attendanceRepository.save(attendance);
    }

    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    public Attendance getAttendanceById(Long id) {
        return attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));
    }

    public List<Attendance> getAttendanceByWorker(Long workerId) {

        if (!workerRepository.existsById(workerId)) {
            throw new RuntimeException("Worker not found");
        }

        return attendanceRepository.findByWorkerId(workerId);
    }

    public List<Attendance> getAttendanceByWorksite(Long worksiteId) {

        if (!worksiteRepository.existsById(worksiteId)) {
            throw new RuntimeException("Worksite not found");
        }

        return attendanceRepository.findByWorksiteId(worksiteId);
    }

    public Attendance updateAttendance(
            Long id,
            Long workerId,
            Long worksiteId,
            Attendance attendance) {

        Attendance existingAttendance = getAttendanceById(id);

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        Worksite worksite = worksiteRepository.findById(worksiteId)
                .orElseThrow(() -> new RuntimeException("Worksite not found"));

        existingAttendance.setWorker(worker);
        existingAttendance.setWorksite(worksite);
        existingAttendance.setDate(attendance.getDate());
        existingAttendance.setStatus(attendance.getStatus());

        return attendanceRepository.save(existingAttendance);
    }

    public void deleteAttendance(Long id) {

        Attendance attendance = getAttendanceById(id);

        attendanceRepository.delete(attendance);
    }
}