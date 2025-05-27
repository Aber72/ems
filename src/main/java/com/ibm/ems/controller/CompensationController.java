package com.ibm.ems.controller;

import com.ibm.ems.entities.Compensation;
import com.ibm.ems.entities.Employee;
import com.ibm.ems.service.CompensationService;
import com.ibm.ems.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import org.springframework.validation.BindingResult;

import java.sql.Date;
import java.time.YearMonth;
import java.util.List;

@Controller
@RequestMapping("/compensation")
public class CompensationController {

    @Autowired
    private CompensationService compensationService;

    @Autowired
    private EmployeeService employeeService;
    
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("compensation", new Compensation());
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "add_compensation";
    }
    
    
    @PostMapping("/save")
    public String saveComp(@ModelAttribute Compensation compensation, Model model) {
        String result = compensationService.addCompensation(compensation);

        // Always load employee dropdown
        model.addAttribute("employees", employeeService.getAllEmployees());

        switch (result) {
            case "DUPLICATE_SALARY":
                model.addAttribute("message", "Salary already exists for this month.");
                break;
            case "INVALID_AMOUNT":
                model.addAttribute("message", "Amount must be greater than 0 for selected type.");
                break;
            case "INVALID_ADJUSTMENT":
                model.addAttribute("message", "Adjustment amount cannot be zero.");
                break;
            case "MISSING_DESCRIPTION":
                model.addAttribute("message", "Description is required for this compensation type.");
                break;
            case "SUCCESS":
                model.addAttribute("msg", "Compensation added successfully!");
                model.addAttribute("compensation", new Compensation()); // Clear form for next entry
                break;
            default:
                model.addAttribute("message", "Unknown error occurred.");
        }

        return "add_compensation";
    }


   
}
