package uz.pdp.apiappweek1tasks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.apiappweek1tasks.entity.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Integer> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Integer id);
}