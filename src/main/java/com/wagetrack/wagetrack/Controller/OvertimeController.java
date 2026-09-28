package com.wagetrack.wagetrack.Controller;

import com.wagetrack.wagetrack.Model.Overtime;
import com.wagetrack.wagetrack.Service.OvertimeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/overtime")
public class OvertimeController {

    private final OvertimeService overtimeService;

    public OvertimeController(OvertimeService overtimeService) {
        this.overtimeService = overtimeService;
    }

    @PostMapping
    public Overtime createOvertime(
            @RequestParam(name = "workerId") Long workerId,
            @Valid @RequestBody Overtime overtime) {

        return overtimeService.createOvertime(workerId, overtime);
    }

    @GetMapping
    public List<Overtime> getAllOvertime() {
        return overtimeService.getAllOvertime();
    }

    @GetMapping("/{id}")
    public Overtime getOvertimeById(@PathVariable Long id) {
        return overtimeService.getOvertimeById(id);
    }

    @GetMapping("/worker/{workerId}")
    public List<Overtime> getOvertimeByWorker(
            @PathVariable Long workerId) {

        return overtimeService.getOvertimeByWorker(workerId);
    }

    @PutMapping("/{id}")
    public Overtime updateOvertime(
            @PathVariable Long id,
            @RequestParam(name = "workerId") Long workerId,
            @Valid @RequestBody Overtime overtime) {

        return overtimeService.updateOvertime(
                id,
                workerId,
                overtime
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOvertime(
            @PathVariable Long id) {

        overtimeService.deleteOvertime(id);

        return ResponseEntity.ok(
                "Overtime record deleted successfully"
        );
    }
}