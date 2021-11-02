package uz.pdp.apiappweek1tasks.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.apiappweek1tasks.entity.Address;
import uz.pdp.apiappweek1tasks.entity.Company;
import uz.pdp.apiappweek1tasks.paylad.ApiResponse;
import uz.pdp.apiappweek1tasks.paylad.CompanyDTO;
import uz.pdp.apiappweek1tasks.repository.AddressRepository;
import uz.pdp.apiappweek1tasks.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private AddressRepository addressRepository;

    public List<Company> getCompanies(){
        return companyRepository.findAll();
    }

    public Company getCompany(Integer id){
        Optional<Company> optionalCompany = companyRepository.findById(id);
        return optionalCompany.orElse(null);
    }

    public ApiResponse addCompany(CompanyDTO companyDTO){
        Address address = new Address();
        address.setStreet(companyDTO.getStreet());
        address.setHomeNumber(companyDTO.getHomeNumber());
        Address savedAddress = addressRepository.save(address);

        Company company = new Company();
        company.setCorpName(companyDTO.getCorpName());
        company.setDirectorName(companyDTO.getDirectorName());
        company.setAddress(savedAddress);
        companyRepository.save(company);
        return new  ApiResponse("Added successfully", true);
    }

    public ApiResponse editCompany(CompanyDTO companyDTO, Integer id){
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isEmpty())
            return new ApiResponse("No such company with this id", false);

        Address editingAddress = optionalCompany.get().getAddress();
        editingAddress.setStreet(companyDTO.getStreet());
        editingAddress.setHomeNumber(companyDTO.getHomeNumber());
        Address editedAddress = addressRepository.save(editingAddress);
        Company editingCompany = optionalCompany.get();
        editingCompany.setCorpName(companyDTO.getCorpName());
        editingCompany.setDirectorName(companyDTO.getDirectorName());
        editingCompany.setAddress(editedAddress);

        companyRepository.save(editingCompany);
        return new ApiResponse("Successfully edited", true);
    }

    public ApiResponse deleteCompany(Integer id){
        if (!companyRepository.existsById(id))
            return new ApiResponse("No such company with this id", false);
        addressRepository.delete(companyRepository.getById(id).getAddress());
        companyRepository.deleteById(id);
        return new ApiResponse("Successfully deleted", true);
    }
}
