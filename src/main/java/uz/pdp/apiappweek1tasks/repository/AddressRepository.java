package uz.pdp.apiappweek1tasks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.apiappweek1tasks.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
