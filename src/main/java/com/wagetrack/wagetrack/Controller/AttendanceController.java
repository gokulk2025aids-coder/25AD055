package com.wagetrack.wagetrack.Controller;

import com.wagetrack.wagetrack.Model.Attendance;
import com.wagetrack.wagetrack.Service.AttendanceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping
    public Attendance createAttendance(
            @RequestBody Attendance attendance) {

        return attendanceService.createAttendance(attendance);
    }

    @GetMapping
    public List<Attendance> getAllAttendance() {
        return attendanceService.getAllAttendance();
    }

    @GetMapping("/{id}")
    public Attendance getAttendanceById(@PathVariable Long id) {
        return attendanceService.getAttendanceById(id);
    }

    @PutMapping("/{id}")
    public Attendance updateAttendance(
            @PathVariable Long id,
            @RequestBody Attendance attendance) {

        return attendanceService.updateAttendance(id, attendance);
    }

    @DeleteMapping("/{id}")
    public String deleteAttendance(@PathVariable Long id) {

        attendanceService.deleteAttendance(id);

        return "Attendance deleted successfully";
    }
}