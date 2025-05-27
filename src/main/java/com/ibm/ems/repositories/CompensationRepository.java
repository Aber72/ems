package com.ibm.ems.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ibm.ems.entities.Compensation;
import com.ibm.ems.entities.Employee;



@Repository

public interface CompensationRepository extends JpaRepository<Compensation, Long> {
	boolean existsByEmployeeUidAndTypeAndDateBetween(Long uid, String type, LocalDate start, LocalDate end);
	List<Compensation> findByEmployeeUidAndDateBetween(Long uid, LocalDate start, LocalDate end);
}
