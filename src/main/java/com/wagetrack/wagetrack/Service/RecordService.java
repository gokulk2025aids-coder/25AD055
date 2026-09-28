package com.wagetrack.wagetrack.Service;

import com.wagetrack.wagetrack.Model.Record;
import com.wagetrack.wagetrack.Repository.RecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordService {

    private final RecordRepository recordRepository;

    public RecordService(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public Record createRecord(Record record) {
        return recordRepository.save(record);
    }

    public List<Record> getAllRecords() {
        return recordRepository.findAll();
    }

    public Record getRecordById(Long id) {
        return recordRepository.findById(id).orElse(null);
    }

    public Record updateRecord(Long id, Record record) {

        Record existingRecord = recordRepository.findById(id).orElse(null);

        if (existingRecord == null) {
            return null;
        }

        existingRecord.setWorkerId(record.getWorkerId());
        existingRecord.setDate(record.getDate());
        existingRecord.setRegularAmount(record.getRegularAmount());
        existingRecord.setOvertimeHours(record.getOvertimeHours());
        existingRecord.setOvertimeAmount(record.getOvertimeAmount());
        existingRecord.setTotalAmount(record.getTotalAmount());
        existingRecord.setPaymentStatus(record.getPaymentStatus());

        return recordRepository.save(existingRecord);
    }

    public void deleteRecord(Long id) {
        recordRepository.deleteById(id);
    }
}