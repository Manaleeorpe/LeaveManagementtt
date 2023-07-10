package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "employee")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "employeeid")
    private Long employeeid;

	public Employee() {
		super();
	}

	public Employee(Long employeeid) {
		super();
		this.employeeid = employeeid;
	}

	public Employee(Long employeeid, @NotNull(message = "First name is mandatory") String name) {
		super();
		this.employeeid = employeeid;
		this.name = name;
	}
	


	public Employee(Long employeeid, @NotNull(message = "First name is mandatory") String name,
			@NotNull(message = "Email is mandatory") @Email(message = "Require email format") String email) {
		super();
		this.employeeid = employeeid;
		this.name = name;
		this.email = email;
	}

	public Employee(Long employeeid, @NotNull(message = "First name is mandatory") String name,
			@NotNull(message = "Email is mandatory") @Email(message = "Require email format") String email,
			@Size(max = 10, min = 10, message = "Require only 10 digits") String phoneNumber) {
		super();
		this.employeeid = employeeid;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}


	public Employee(Long employeeid, @NotNull(message = "First name is mandatory") String name,
			@NotNull(message = "Email is mandatory") @Email(message = "Require email format") String email,
			@Size(max = 10, min = 10, message = "Require only 10 digits") String phoneNumber, String department) {
		super();
		this.employeeid = employeeid;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.department = department;
	}

    public Employee(Long employeeid, @NotNull(message = "First name is mandatory") String name,
			@NotNull(message = "Email is mandatory") @Email(message = "Require email format") String email,
			@Size(max = 10, min = 10, message = "Require only 10 digits") String phoneNumber, String department,
			@NotNull(message = "Password is mandatory") String password) {
		super();
		this.employeeid = employeeid;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.department = department;
		this.password = password;
	}
	
	@NotNull(message = "First name is mandatory")
    private String name;
    
    @NotNull(message = "Email is mandatory")
	@Email(message = "Require email format")
    private String email;
    
    @Size(max = 10,min = 10, message = "Require only 10 digits")
    private String phoneNumber;
    private String department;
    
    @NotNull(message = "Password is mandatory")
    private String password;
    
    public Long getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(Long employeeid) {
        this.employeeid = employeeid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @OneToMany(mappedBy = "employee" , cascade = CascadeType.ALL)
    private List<LeaveBalances> leaveBalances;

   
    
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<LeaveRequest> leaveRequests;

    
}
