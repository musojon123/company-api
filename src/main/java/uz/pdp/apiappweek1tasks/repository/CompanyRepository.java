package uz.pdp.apiappweek1tasks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.apiappweek1tasks.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
}
