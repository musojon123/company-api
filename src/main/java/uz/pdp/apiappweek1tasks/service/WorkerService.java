package uz.pdp.apiappweek1tasks.service;

import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.apiappweek1tasks.entity.Address;
import uz.pdp.apiappweek1tasks.entity.Department;
import uz.pdp.apiappweek1tasks.entity.Worker;
import uz.pdp.apiappweek1tasks.paylad.ApiResponse;
import uz.pdp.apiappweek1tasks.paylad.WorkerDTO;
import uz.pdp.apiappweek1tasks.repository.AddressRepository;
import uz.pdp.apiappweek1tasks.repository.DepartmentRepository;
import uz.pdp.apiappweek1tasks.repository.WorkerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {
    @Autowired
    WorkerRepository workerRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    public List<Worker> getWorkers(){
        return workerRepository.findAll();
    }

    public Worker getWorker(Integer id){
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (optionalWorker.isEmpty())
            return null;
        return optionalWorker.get();
    }

    public ApiResponse addWorker(WorkerDTO workerDTO){
        if (workerRepository.existsByPhoneNumber(workerDTO.getPhoneNumber()))
            return new ApiResponse("Worker already exist with this phoneNumber", false);

        Optional<Department> optionalDepartment = departmentRepository.findById(workerDTO.getDepartmentId());
        if (optionalDepartment.isEmpty())
            return new ApiResponse("No such dep with id", false);
        Address address = new Address();
        address.setStreet(workerDTO.getStreet());
        address.setHomeNumber(workerDTO.getHomeNumber());
        Address savedAddress = addressRepository.save(address);

        Worker worker = new Worker();
        worker.setName(workerDTO.getName());
        worker.setPhoneNumber(workerDTO.getPhoneNumber());
        worker.setAddress(savedAddress);
        worker.setDepartment(optionalDepartment.get());

        workerRepository.save(worker);
        return new ApiResponse("Saved success", true);
    }

    public ApiResponse editWorker(WorkerDTO workerDTO, Integer id){
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (optionalWorker.isEmpty())
            return new ApiResponse("No such worker", false);

        Optional<Department> optionalDepartment = departmentRepository.findById(workerDTO.getDepartmentId());
        if (optionalDepartment.isEmpty())
            return new ApiResponse("No such dep with id", false);

        if (workerRepository.existsByPhoneNumberAndIdNot(workerDTO.getPhoneNumber(), id))
            return new ApiResponse("Already exist this number",false);

        Address editingAddress = optionalWorker.get().getAddress();
        editingAddress.setStreet(workerDTO.getStreet());
        editingAddress.setHomeNumber(workerDTO.getHomeNumber());
        Address editedAddress = addressRepository.save(editingAddress);

        Worker editingWorker = optionalWorker.get();
        editingWorker.setName(workerDTO.getName());
        editingWorker.setPhoneNumber(workerDTO.getPhoneNumber());
        editingWorker.setAddress(editedAddress);
        editingWorker.setDepartment(optionalDepartment.get());
        workerRepository.save(editingWorker);

        return new ApiResponse("Success in editing", true);
    }

    public ApiResponse deleteWorker(Integer id){
        if (!workerRepository.existsById(id))
            return new ApiResponse("No such worker", false);
        workerRepository.deleteById(id);
        return new ApiResponse("Successfully deleted", true);
    }
}
