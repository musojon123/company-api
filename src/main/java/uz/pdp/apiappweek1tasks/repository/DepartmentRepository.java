package uz.pdp.apiappweek1tasks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.apiappweek1tasks.entity.Department;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    @Query("select d from Department d where d.name = ?1")
    Optional<Department> findByName(String name);

    @Query("select (count(d) > 0) from Department d where d.name = ?1 and d.id <> ?2")
    boolean existsByNameAndIdNot(String name, Integer id);
}