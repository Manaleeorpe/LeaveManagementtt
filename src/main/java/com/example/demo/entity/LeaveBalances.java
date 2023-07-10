package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "leave_balances")
public class LeaveBalances {
    public LeaveBalances(Long leavebalanceid, Long employeeid, String leavetype, int balances) {
		super();
		this.leavebalanceid = leavebalanceid;
		this.employeeid = employeeid;
		this.leavetype = leavetype;
		this.balances = balances;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leavebalanceid;
    public LeaveBalances() {
		super();
	}

	public LeaveBalances(Long leavebalanceid) {
		super();
		this.leavebalanceid = leavebalanceid;
	}

	public LeaveBalances(Long leavebalanceid, Long employeeid) {
		super();
		this.leavebalanceid = leavebalanceid;
		this.employeeid = employeeid;
	}

	public LeaveBalances(Long leavebalanceid, Long employeeid, String leavetype) {
		super();
		this.leavebalanceid = leavebalanceid;
		this.employeeid = employeeid;
		this.leavetype = leavetype;
	}

	public LeaveBalances(Long leavebalanceid, Long employeeid, String leavetype, int balances, Employee employee) {
		super();
		this.leavebalanceid = leavebalanceid;
		this.employeeid = employeeid;
		this.leavetype = leavetype;
		this.balances = balances;
		this.employee = employee;
	}

	@Override
	public String toString() {
		return "LeaveBalances [leavebalanceid=" + leavebalanceid + ", employeeid=" + employeeid + ", leavetype="
				+ leavetype + ", balances=" + balances + ", employee=" + employee + "]";
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	private Long employeeid;
    private String leavetype;
    private int balances;
    
    @ManyToOne
    @JoinColumn(name = "employeeid", insertable = false, updatable = false)
    private Employee employee;

	public Long getLeavebalanceid() {
		return leavebalanceid;
	}

	public void setLeavebalanceid(Long leavebalanceid) {
		this.leavebalanceid = leavebalanceid;
	}

	public Long getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(Long employeeid) {
		this.employeeid = employeeid;
	}

	public String getLeavetype() {
		return leavetype;
	}

	public void setLeavetype(String leavetype) {
		this.leavetype = leavetype;
	}

	public int getBalances() {
		return balances;
	}

	public void setBalances(int balances) {
		this.balances = balances;
	}

    // getters and setters
}

