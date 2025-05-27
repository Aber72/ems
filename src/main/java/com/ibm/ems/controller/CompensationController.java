package com.ibm.ems.controller;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ibm.ems.entities.Compensation;
import com.ibm.ems.service.CompensationService;
import com.ibm.ems.service.EmployeeService;

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
                model.addAttribute("compensation", new Compensation());
                break;
            default:
                model.addAttribute("message", "Description is required for this Adjustment type.");
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

        //  Group by YearMonth and sum
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



    // for Monthly Breakdown from History page
    @GetMapping("/breakdown")
    public String showCompBreakdownForm(@RequestParam("uid") Long uid, Model model) {
        model.addAttribute("uid", uid);
        return "comp_breakdown_form"; 
    }
    
    // for direct entry form
    @GetMapping("/monthly-entry")
    public String showMonthlyBreakdownEntryForm(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "comp_breakdown_entry"; 
    }



    @PostMapping("/breakdown")
    public String processCompBreakdown(@RequestParam("uid") Long uid,
                                       @RequestParam("yearMonth") String yearMonth,
                                       Model model,
                                       RedirectAttributes redirectAttributes) {

        List<Compensation> comps = compensationService.getCompensationsByMonth(uid, yearMonth);

        if (comps == null || comps.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "❌ No compensation breakdown found for this employee and month.");
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
    
    @PostMapping("/update")
    public String updateCompensation(@RequestParam Long id,
                                     @RequestParam Double amount,
                                     @RequestParam String description,
                                     @RequestParam Long uid,
                                     @RequestParam String yearMonth,
                                     RedirectAttributes redirectAttributes) {

        Compensation comp = compensationService.getCompensationById(id);
        if (comp == null) {
            redirectAttributes.addFlashAttribute("message", "Compensation not found.");
            return "redirect:/compensation/monthly-entry";
        }

        String result = compensationService.validateAndUpdate(comp, amount, description);

        if ("SUCCESS".equals(result)) {
            // ✅ Redirect to refreshed breakdown
            return "redirect:/compensation/breakdown?uid=" + uid + "&yearMonth=" + yearMonth;
        } else {
            // ❌ Validation failed → redirect back to edit page with message
            redirectAttributes.addFlashAttribute("message", result);
            return "redirect:/compensation/edit?id=" + id + "&uid=" + uid + "&yearMonth=" + yearMonth;
        }
    }


    
    @GetMapping("/edit")
    public String showEditForm(@RequestParam("id") Long id,
                               @RequestParam("uid") Long uid,
                               @RequestParam("yearMonth") String yearMonth,
                               Model model) {
        Compensation comp = compensationService.getCompensationById(id);
        if (comp == null) {
            model.addAttribute("message", "Compensation not found.");
            return "redirect:/compensation/monthly-entry";
        }
        model.addAttribute("compensation", comp);
        model.addAttribute("uid", uid);
        model.addAttribute("yearMonth", yearMonth);
        return "edit_comp"; // points to /WEB-INF/views/edit_comp.jsp
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