package com.ibm.ems.controller;

import com.ibm.ems.entities.Compensation;
import com.ibm.ems.entities.Employee;
import com.ibm.ems.service.CompensationService;
import com.ibm.ems.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import org.springframework.validation.BindingResult;

import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

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
    
    
    @GetMapping("/history")
    public String showCompensationHistoryForm(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "comp_history_form";
    }
    @PostMapping("/history")
    public String processCompHistory(@RequestParam("uid") String uid,
                                     @RequestParam("startDate") String startDate,
                                     @RequestParam("endDate") String endDate,
                                     Model model) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        if (end.isBefore(start)) {
            model.addAttribute("error", "End date cannot be before start date.");
            model.addAttribute("employees", employeeService.getAllEmployees());
            return "comp_history_form";
        }

        List<Compensation> history = compensationService.getCompensationHistory(uid, start, end);

       
        Map<String, Double> monthlyTotals = history.stream()
                .collect(Collectors.groupingBy(
                    c -> YearMonth.from(c.getDate()).toString(), 
                    TreeMap::new, 
                    Collectors.summingDouble(c -> c.getAmount() != null ? c.getAmount() : 0.0)
                ));

        model.addAttribute("uid", uid);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("monthlyTotals", monthlyTotals); 

        return "comp_history";
    }
    
    
    @GetMapping("/breakdown")
    public String showCompBreakdownForm(@RequestParam("uid") Long uid, Model model) {
        model.addAttribute("uid", uid);
        return "comp_breakdown_form"; // for Monthly Breakdown from History page
    }

    @GetMapping("/monthly-entry")
    public String showMonthlyBreakdownEntryForm(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "comp_breakdown_entry"; // for direct entry form
    }



    @PostMapping("/breakdown")
    public String processCompBreakdown(@RequestParam("uid") Long uid,
                                       @RequestParam("yearMonth") String yearMonth,
                                       Model model,
                                       RedirectAttributes redirectAttributes) {

        List<Compensation> comps = compensationService.getCompensationsByMonth(uid, yearMonth);

        if (comps == null || comps.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "â�Œ No compensation breakdown found for this employee and month.");
            return "redirect:/compensation/monthly-entry";
        }

        double salary = comps.stream()
                .filter(c -> "SALARY".equalsIgnoreCase(c.getType()))
                .mapToDouble(Compensation::getAmount)
                .sum();

        double bonus = comps.stream()
                .filter(c -> "BONUS".equalsIgnoreCase(c.getType()))
                .mapToDouble(Compensation::getAmount)
                .sum();

        double other = comps.stream()
                .filter(c -> !"SALARY".equalsIgnoreCase(c.getType()) &&
                             !"BONUS".equalsIgnoreCase(c.getType()))
                .mapToDouble(Compensation::getAmount)
                .sum();

        double total = salary + bonus + other;

        model.addAttribute("uid", uid);
        model.addAttribute("yearMonth", yearMonth);
        model.addAttribute("salary", salary);
        model.addAttribute("bonus", bonus);
        model.addAttribute("other", other);
        model.addAttribute("total", total);
        model.addAttribute("compensations", comps);

        return "comp_breakdown";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(LocalDate.class, new java.beans.PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
        });
    }

    
    


   
}
