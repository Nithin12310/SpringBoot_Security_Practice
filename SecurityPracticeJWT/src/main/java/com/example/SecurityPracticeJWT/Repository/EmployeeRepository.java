package com.example.SecurityPracticeJWT.Repository;

import com.example.SecurityPracticeJWT.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,String> {
    Optional<Employee> findByemailid(String emailid);
}
