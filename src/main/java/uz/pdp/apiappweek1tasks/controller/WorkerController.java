package uz.pdp.apiappweek1tasks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.apiappweek1tasks.entity.Department;
import uz.pdp.apiappweek1tasks.entity.Worker;
import uz.pdp.apiappweek1tasks.paylad.ApiResponse;
import uz.pdp.apiappweek1tasks.paylad.DepartmentDTO;
import uz.pdp.apiappweek1tasks.paylad.WorkerDTO;
import uz.pdp.apiappweek1tasks.service.DepartmentService;
import uz.pdp.apiappweek1tasks.service.WorkerService;

@RestController
@RequestMapping("/api/departments")
public class WorkerController {
    @Autowired
    WorkerService workerService;

    @GetMapping
    public HttpEntity<?> getWorkers(){
        return ResponseEntity.ok(workerService.getWorkers());
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getWorker(@PathVariable Integer id){
        Worker worker = workerService.getWorker(id);
        if (worker==null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new  ResponseEntity<>(worker, HttpStatus.OK);
    }

    @PostMapping
    public HttpEntity<?> addWorker(@RequestBody WorkerDTO workerDTO){
        ApiResponse apiResponse = workerService.addWorker(workerDTO);
        if (!apiResponse.isSuccess())
            return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
        return ResponseEntity.status(201).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editWorker(@RequestBody WorkerDTO workerDTO, @PathVariable Integer id){
        ApiResponse apiResponse = workerService.editWorker(workerDTO, id);
        if (!apiResponse.isSuccess())
            return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteWorker(@PathVariable Integer id){
        ApiResponse apiResponse = workerService.deleteWorker(id);
        if (!apiResponse.isSuccess())
            return ResponseEntity.status(409).body(apiResponse);
        return ResponseEntity.status(202).body(apiResponse);
    }
}
