package com.ibm.ems.controller;

import com.ibm.ems.entities.Employee;
import com.ibm.ems.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "addEmployee";
    }

    @PostMapping("/add")
    public String addEmployee(@Valid @ModelAttribute("employee") Employee employee,
                              BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            return "addEmployee";
        }

        String status = employeeService.addEmployee(employee);

        switch (status) {
            case "DUPLICATE":
                model.addAttribute("error", "Employee already exists.");
                break;
            case "INVALID_DATE":
                model.addAttribute("error", "Birth date cannot be in the future.");
                break;
            case "SUCCESS":
                model.addAttribute("msg", "Employee added successfully.");
                model.addAttribute("employee", new Employee());
                break;
            default:
                model.addAttribute("error", "An unknown error occurred.");
        }
        return "addEmployee";
    }

  
}
