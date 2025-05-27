package com.ibm.ems.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibm.ems.entities.Employee;



@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e " +
           "WHERE (:firstName IS NULL OR LOWER(e.firstName) LIKE LOWER(CONCAT('%', :firstName, '%'))) " +
           "AND (:lastName IS NULL OR LOWER(e.lastName) LIKE LOWER(CONCAT('%', :lastName, '%'))) " +
           "AND (:position IS NULL OR LOWER(e.position) LIKE LOWER(CONCAT('%', :position, '%')))")
    List<Employee> searchEmployees(
        @Param("firstName") String firstName,
        @Param("lastName") String lastName,
        @Param("position") String position
    );

    Optional<Employee> findByFirstNameAndMiddleNameAndLastNameAndBirthDate(
        String firstName,
        String middleName,
        String lastName,
        LocalDate birthDate
    );
    Optional<Employee> findByUid(Long uid);
}
