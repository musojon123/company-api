package uz.pdp.apiappweek1tasks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.apiappweek1tasks.entity.Company;
import uz.pdp.apiappweek1tasks.entity.Department;
import uz.pdp.apiappweek1tasks.paylad.ApiResponse;
import uz.pdp.apiappweek1tasks.paylad.CompanyDTO;
import uz.pdp.apiappweek1tasks.paylad.DepartmentDTO;
import uz.pdp.apiappweek1tasks.service.CompanyService;
import uz.pdp.apiappweek1tasks.service.DepartmentService;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    @GetMapping
    public HttpEntity<?> getDepartments(){
        return ResponseEntity.ok(departmentService.getDepartments());
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getCompany(@PathVariable Integer id){
        Department department = departmentService.getDepartment(id);
        if (department==null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new  ResponseEntity<>(department, HttpStatus.OK);
    }

    @PostMapping
    public HttpEntity<?> addDepartment(@RequestBody DepartmentDTO departmentDTO){
        ApiResponse apiResponse = departmentService.addCompany(departmentDTO);
        if (!apiResponse.isSuccess())
            return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
        return ResponseEntity.status(201).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editDepartment(@RequestBody DepartmentDTO departmentDTO, @PathVariable Integer id){
        ApiResponse apiResponse = departmentService.editCompany(departmentDTO, id);
        if (!apiResponse.isSuccess())
            return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteDepartment(@PathVariable Integer id){
        ApiResponse apiResponse = departmentService.deleteDepartment(id);
        if (!apiResponse.isSuccess())
            return ResponseEntity.status(409).body(apiResponse);
        return ResponseEntity.status(202).body(apiResponse);
    }

}
