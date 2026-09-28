package com.wagetrack.wagetrack.Controller;

import com.wagetrack.wagetrack.Model.Record;
import com.wagetrack.wagetrack.Service.RecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/records")
public class RecordController {

    private final RecordService recordService;

    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @PostMapping
    public Record createRecord(@RequestBody Record record) {
        return recordService.createRecord(record);
    }

    @GetMapping
    public List<Record> getAllRecords() {
        return recordService.getAllRecords();
    }

    @GetMapping("/{id}")
    public Record getRecordById(@PathVariable Long id) {
        return recordService.getRecordById(id);
    }

    @PutMapping("/{id}")
    public Record updateRecord(
            @PathVariable Long id,
            @RequestBody Record record) {

        return recordService.updateRecord(id, record);
    }

    @DeleteMapping("/{id}")
    public String deleteRecord(@PathVariable Long id) {

        recordService.deleteRecord(id);

        return "Record deleted successfully";
    }
}