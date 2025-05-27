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

    // Show form to add employee
    @GetMapping("/add")
    public String showAddEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "add-employee";
    }

    // Handle employee save
    @PostMapping("/save")
    public String saveEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-employee";
        }

        if (employeeService.searchEmployees(employee.getFirstName(), employee.getLastName(), employee.getPosition()).stream()
                .anyMatch(e -> e.getBirthDate().equals(employee.getBirthDate()))) {
            model.addAttribute("errorMessage", "Employee already exists.");
            return "add-employee";
        }

        employeeService.saveEmployee(employee);
        model.addAttribute("successMessage", "Employee added successfully.");
        model.addAttribute("employee", new Employee());
        return "add-employee";
    }

    // Show search form
    @GetMapping("/search")
    public String showSearchForm(Model model) {
        return "search-employee";
    }

    // Handle employee search
    @PostMapping("/search")
    public String searchEmployees(@RequestParam(required = false) String firstName,
                                  @RequestParam(required = false) String lastName,
                                  @RequestParam(required = false) String position,
                                  Model model) {

        List<Employee> employees = employeeService.searchEmployees(firstName, lastName, position);
        model.addAttribute("employees", employees);
        return "search-employee";
    }

    // Show edit form
    @GetMapping("/edit/{id}")
    public String editEmployee(@PathVariable("id") Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "edit-employee";
    }

    // Handle update
    @PostMapping("/update")
    public String updateEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "edit-employee";
        }

        employeeService.updateEmployee(employee);
        model.addAttribute("successMessage", "Employee updated successfully.");
        return "edit-employee";
    }
}
