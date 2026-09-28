package com.wagetrack.wagetrack.Controller;

import com.wagetrack.wagetrack.Model.Attendance;
import com.wagetrack.wagetrack.Service.AttendanceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    // Create Attendance
    @PostMapping
    public Attendance createAttendance(
            @RequestParam(name = "workerId") Long workerId,
            @RequestParam(name = "worksiteId") Long worksiteId,
            @Valid @RequestBody Attendance attendance) {

        return attendanceService.createAttendance(
                workerId,
                worksiteId,
                attendance
        );
    }

    // Get all attendance records
    @GetMapping
    public List<Attendance> getAllAttendance() {
        return attendanceService.getAllAttendance();
    }

    // Get attendance by ID
    @GetMapping("/{id}")
    public Attendance getAttendanceById(@PathVariable Long id) {
        return attendanceService.getAttendanceById(id);
    }

    // Get attendance by worker
    @GetMapping("/worker/{workerId}")
    public List<Attendance> getAttendanceByWorker(
            @PathVariable Long workerId) {

        return attendanceService.getAttendanceByWorker(workerId);
    }

    // Get attendance by worksite
    @GetMapping("/worksite/{worksiteId}")
    public List<Attendance> getAttendanceByWorksite(
            @PathVariable Long worksiteId) {

        return attendanceService.getAttendanceByWorksite(worksiteId);
    }

    // Update attendance
    @PutMapping("/{id}")
    public Attendance updateAttendance(
            @PathVariable Long id,
            @RequestParam(name = "workerId") Long workerId,
            @RequestParam(name = "worksiteId") Long worksiteId,
            @Valid @RequestBody Attendance attendance) {

        return attendanceService.updateAttendance(
                id,
                workerId,
                worksiteId,
                attendance
        );
    }

    // Delete attendance
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAttendance(
            @PathVariable Long id) {

        attendanceService.deleteAttendance(id);

        return ResponseEntity.ok("Attendance deleted successfully");
    }
}