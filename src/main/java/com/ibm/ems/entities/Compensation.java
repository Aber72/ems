package com.ibm.ems.entities;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "compensations")
public class Compensation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @NotBlank(message = "Compensation type is required")
    @Column(name = "comp_type", nullable = false)
    private String type;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "-99999999.99", inclusive = true, message = "Amount must be a valid number")
    @Digits(integer = 10, fraction = 2, message = "Amount must be numeric with up to 2 decimal places")
    @Column(nullable = false)
    private BigDecimal amount;

    @Size(max = 255, message = "Description can't be longer than 255 characters")
    private String description;

    @NotNull(message = "Compensation date is required")
    @Column(name = "comp_date", nullable = false)
    private Date compDate;

    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCompDate() {
        return compDate;
    }

    public void setCompDate(Date compDate) {
        this.compDate = compDate;
    }

    // --- Constructors ---

    public Compensation() {
        // Default constructor
    }

    public Compensation(Employee employee, String type, BigDecimal amount, String description, Date compDate) {
        this.employee = employee;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.compDate = compDate;
    }
}
