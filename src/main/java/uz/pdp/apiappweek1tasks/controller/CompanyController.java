package uz.pdp.apiappweek1tasks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.apiappweek1tasks.entity.Company;
import uz.pdp.apiappweek1tasks.paylad.ApiResponse;
import uz.pdp.apiappweek1tasks.paylad.CompanyDTO;
import uz.pdp.apiappweek1tasks.service.CompanyService;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    @GetMapping
    public HttpEntity<List<Company>> getCompanies(){
        return ResponseEntity.ok(companyService.getCompanies());
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getCompany(@PathVariable Integer id){
        Company company = companyService.getCompany(id);
        if (company==null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new  ResponseEntity<>(company, HttpStatus.OK);
    }

    @PostMapping
    public HttpEntity<ApiResponse> addCompany(@RequestBody CompanyDTO companyDTO){
        ApiResponse apiResponse = companyService.addCompany(companyDTO);
        if (!apiResponse.isSuccess())
            return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
        return ResponseEntity.status(201).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editCompany(@RequestBody CompanyDTO companyDTO, @PathVariable Integer id){
        ApiResponse apiResponse = companyService.editCompany(companyDTO, id);
        if (!apiResponse.isSuccess())
            return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteCompany(@PathVariable Integer id){
        ApiResponse apiResponse = companyService.deleteCompany(id);
        if (!apiResponse.isSuccess())
            return ResponseEntity.status(409).body(apiResponse);
        return ResponseEntity.status(202).body(apiResponse);
    }

}
