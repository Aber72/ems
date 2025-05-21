package com.ibm.ems.entities;

import java.sql.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;


@Entity
@Table(name = "employees", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"first_name", "middle_name", "last_name", "birth_date"})
})
public class Employee {
	

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long uid;
	
    
    @NotBlank(message = "First Name is required")
    @Column(name = "first_name")
	private String firstName;
    
    @Column(name = "middle_name")
	private String middleName;
    
    @NotBlank(message = "Last Name is required")
    @Column(name = "last_name")
	private String lastName;
    
//    @NotNull(message = "Birth date is required")
    @PastOrPresent(message = "Birth date cannot be in the future")
    @Column(name = "birth_date")
	private Date  birthDate;
    
    @NotBlank(message = "Position is required")
	private String position;
	
    
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Compensation> compensations;


    
  //Getter & Setter
    
	public Long getUid() {
		return uid;
	}


	


	public void setUid(Long uid) {
		this.uid = uid;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getMiddleName() {
		return middleName;
	}


	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public Date getBirthDate() {
		return birthDate;
	}


	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}


	public String getPosition() {
		return position;
	}


	public void setPosition(String position) {
		this.position = position;
	}


	public List<Compensation> getCompensations() {
		return compensations;
	}


	public void setCompensations(List<Compensation> compensations) {
		this.compensations = compensations;
	}
	
	
	public Employee(Long uid, String firstName, String middleName, String lastName, Date birthDate, String position,
			List<Compensation> compensations) {
		super();
		this.uid = uid;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.position = position;
		this.compensations = compensations;
	}





	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	

}
