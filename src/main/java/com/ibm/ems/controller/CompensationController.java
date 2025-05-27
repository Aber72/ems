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

    // Add compensation form
    @GetMapping("/add/{employeeId}")
    public String showAddCompensationForm(@PathVariable Long employeeUid, Model model) {
        Employee employee = employeeService.getEmployeeById(employeeUid);
        Compensation compensation = new Compensation();
        compensation.setEmployee(employee);
        model.addAttribute("compensation", compensation);
        return "add-compensation";
    }

    // Save compensation
    @PostMapping("/save")
    public String saveCompensation(@Valid @ModelAttribute("compensation") Compensation compensation,
                                   BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-compensation";
        }

        compensationService.saveCompensation(compensation);
        model.addAttribute("successMessage", "Compensation added successfully.");
        return "add-compensation";
    }

    // View compensation history
    @GetMapping("/history/{employeeId}")
    public String viewCompHistory(@PathVariable Long employeeUid,
                                  @RequestParam String start,
                                  @RequestParam String end,
                                  Model model) {
        Date startDate = Date.valueOf(start + "-01");
        Date endDate = Date.valueOf(end + "-28"); // Approximate end of month

        List<Compensation> history = compensationService.getCompensationByEmployeeAndDateRange(employeeUid, startDate, endDate);
        model.addAttribute("compHistory", history);
        return "compensation-history";
    }

    // View monthly breakdown
    @GetMapping("/breakdown/{employeeId}/{year}/{month}")
    public String viewBreakdown(@PathVariable Long employeeUid,
                                @PathVariable int year,
                                @PathVariable int month,
                                Model model) {

        List<Compensation> comps = compensationService.getCompensationByEmployeeAndMonth(employeeUid, year, month);
        model.addAttribute("comps", comps);
        return "monthly-breakdown";
    }

    // Edit compensation
    @GetMapping("/edit/{id}")
    public String editCompensation(@PathVariable Long id, Model model) {
        Compensation comp = compensationService.getCompensationByEmployeeId(id)
                .stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
        model.addAttribute("compensation", comp);
        return "edit-compensation";
    }

    @PostMapping("/update")
    public String updateCompensation(@Valid @ModelAttribute("compensation") Compensation compensation,
                                     BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "edit-compensation";
        }

        compensationService.updateCompensation(compensation);
        model.addAttribute("successMessage", "Compensation updated successfully.");
        return "edit-compensation";
    }
}
