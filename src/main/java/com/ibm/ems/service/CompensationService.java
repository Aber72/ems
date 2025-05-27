package com.ibm.ems.service;


import java.time.YearMonth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.ems.entities.Compensation;
import com.ibm.ems.repositories.CompensationRepository;

@Service
 interface CompensationService{
	   @Autowired
	    private CompensationRepository compensationRepository;

	    public String addCompensation(Compensation comp) {
	        YearMonth month = YearMonth.from(comp.getDate());
	        String type = comp.getType().toUpperCase();
	        Double amount = comp.getAmount();
	        String description = comp.getDescription() != null ? comp.getDescription().trim() : "";

	        // 1. ✅ Salary: one per employee per month
	        if ("SALARY".equals(type)) {
	            boolean exists = compensationRepository.existsByEmployeeUidAndTypeAndDateBetween(
	                    comp.getEmployee().getUid(), "SALARY",
	                    month.atDay(1), month.atEndOfMonth()
	            );
	            if (exists) return "DUPLICATE_SALARY";
	            // Salary can be zero or negative — no further validation needed
	        }

	        // 2. ✅ Amount > 0 required for these types
	        if (("BONUS".equals(type) || "COMMISSION".equals(type) || "ALLOWANCE".equals(type)) && (amount == null || amount <= 0)) {
	            return "INVALID_AMOUNT";
	        }

	        // 3. ✅ Adjustment: amount ≠ 0
	        if ("ADJUSTMENT".equals(type) && (amount == null || amount == 0)) {
	            return "INVALID_ADJUSTMENT";
	        }

	        // 4. ✅ Description required for these types
	        if (("BONUS".equals(type) || "COMMISSION".equals(type) || "ALLOWANCE".equals(type) || "ADJUSTMENT".equals(type))
	                && description.isEmpty()) {
	            return "DESCRIPTION_REQUIRED";
	        }

	        // ✅ Save to DB
	        compensationRepository.save(comp);
	        return "SUCCESS";
	    }
	
	

}
