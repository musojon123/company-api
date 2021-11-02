package uz.pdp.apiappweek1tasks.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.apiappweek1tasks.entity.Address;
import uz.pdp.apiappweek1tasks.entity.Company;
import uz.pdp.apiappweek1tasks.entity.Department;
import uz.pdp.apiappweek1tasks.paylad.ApiResponse;
import uz.pdp.apiappweek1tasks.paylad.CompanyDTO;
import uz.pdp.apiappweek1tasks.paylad.DepartmentDTO;
import uz.pdp.apiappweek1tasks.repository.AddressRepository;
import uz.pdp.apiappweek1tasks.repository.CompanyRepository;
import uz.pdp.apiappweek1tasks.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private CompanyRepository companyRepository;

    public List<Department> getDepartments(){
        return departmentRepository.findAll();
    }

    public Department getDepartment(Integer id){
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        return optionalDepartment.orElse(null);
    }

    public ApiResponse addCompany(DepartmentDTO departmentDTO){
        if (departmentRepository.findByName(departmentDTO.getName()).isPresent())
            return new ApiResponse("Already exist", false);
        Optional<Company> optionalCompany = companyRepository.findById(departmentDTO.getCompanyId());
        if (optionalCompany.isEmpty())
            return new ApiResponse("No such company with this id ", false);
        Department department = new Department();
        department.setName(departmentDTO.getName());
        department.setCompany(optionalCompany.get());
        departmentRepository.save(department);
        return new  ApiResponse("Added successfully", true);
    }

    public ApiResponse editCompany(DepartmentDTO departmentDTO, Integer id){
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (optionalDepartment.isEmpty())
            return new ApiResponse("No such dep with this id", false);

        boolean isExist = departmentRepository.existsByNameAndIdNot(departmentDTO.getName(), id);
        if (isExist)
            return new ApiResponse("Already exist", false);

        Optional<Company> optionalCompany = companyRepository.findById(departmentDTO.getCompanyId());
        if (optionalCompany.isEmpty())
            return new ApiResponse("No such company with give id", false);

        Department editingDepartment = optionalDepartment.get();
        editingDepartment.setName(departmentDTO.getName());
        editingDepartment.setCompany(optionalCompany.get());
        departmentRepository.save(editingDepartment);

        return new ApiResponse("Successfully edited", true);
    }

    public ApiResponse deleteDepartment(Integer id){
        if (!departmentRepository.existsById(id))
            return new ApiResponse("No such company with this id", false);
        departmentRepository.deleteById(id);
        return new ApiResponse("Successfully deleted", true);
    }
}
