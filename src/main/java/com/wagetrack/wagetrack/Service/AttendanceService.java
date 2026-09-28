package com.wagetrack.wagetrack.Service;

import com.wagetrack.wagetrack.Model.Attendance;
import com.wagetrack.wagetrack.Repository.AttendanceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    public AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public Attendance createAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    public Attendance getAttendanceById(Long id) {
        return attendanceRepository.findById(id).orElse(null);
    }

    public Attendance updateAttendance(Long id, Attendance attendance) {

        Attendance existingAttendance =
                attendanceRepository.findById(id).orElse(null);

        if (existingAttendance == null) {
            return null;
        }

        existingAttendance.setWorkerId(attendance.getWorkerId());
        existingAttendance.setWorksiteId(attendance.getWorksiteId());
        existingAttendance.setDate(attendance.getDate());
        existingAttendance.setStatus(attendance.getStatus());

        return attendanceRepository.save(existingAttendance);
    }

    public void deleteAttendance(Long id) {
        attendanceRepository.deleteById(id);
    }
}