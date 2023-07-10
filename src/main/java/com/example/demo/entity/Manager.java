package com.example.demo.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "manager")
@AttributeOverrides({  
    @AttributeOverride(name="employeeid", column=@Column(name="employeeid")),  
    @AttributeOverride(name="name", column=@Column(name="name")),
    @AttributeOverride(name="email", column=@Column(name="email")),
    @AttributeOverride(name="phoneNumber", column=@Column(name="phoneNumber")),
    @AttributeOverride(name="department", column=@Column(name="department")),
    @AttributeOverride(name="password", column=@Column(name="password")),
    @AttributeOverride(name="leaveBalances", column=@Column(name="leaveBalances"))
})  
public class Manager extends Employee {
	private Long managerid;
	
	public Long getManagerid() {
		return managerid;
	}

	public void setManagerid(Long managerid) {
		this.managerid = managerid;
	}

	public Manager(Long managerid) {
		super();
		this.managerid = managerid;
	}

	public Manager() {
	        super(); // Invoke the superclass constructor
	    }
}
