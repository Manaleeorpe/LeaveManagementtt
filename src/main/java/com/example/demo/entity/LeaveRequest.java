package com.example.demo.entity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "leaverequests")
public class LeaveRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leaverequestid;
    public LeaveRequest(Long leaverequestid, Long employeeid, LocalDate startDate, LocalDate endDate, String leavetype,
			String reason, String status) {
		super();
		this.leaverequestid = leaverequestid;
		this.employeeid = employeeid;
		this.startDate = startDate;
		this.endDate = endDate;
		this.leavetype = leavetype;
		this.reason = reason;
		this.status = status;
	}

	private Long employeeid;
    private LocalDate startDate;
    private LocalDate endDate;
    private String leavetype;
    private String reason;
    private String status;
    
    //employeeid is foreign key
    
    @ManyToOne
    @JoinColumn(name = "employeeid", insertable = false, updatable = false)
    private Employee employee;

	public Long getLeaverequestid() {
		return leaverequestid;
	}

	public void setLeaverequestid(Long leaverequestid) {
		this.leaverequestid = leaverequestid;
	}

	public Long getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(Long employeeid) {
		this.employeeid = employeeid;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getLeavetype() {
		return leavetype;
	}

	public void setLeavetype(String leavetype) {
		this.leavetype = leavetype;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public LeaveRequest(Long leaverequestid, Long employeeid, LocalDate startDate, LocalDate endDate, String leavetype,
			String reason, String status, Employee employee) {
		super();
		this.leaverequestid = leaverequestid;
		this.employeeid = employeeid;
		this.startDate = startDate;
		this.endDate = endDate;
		this.leavetype = leavetype;
		this.reason = reason;
		this.status = status;
		this.employee = employee;
	}

	@Override
	public String toString() {
		return "LeaveRequest [leaverequestid=" + leaverequestid + ", employeeid=" + employeeid + ", startDate="
				+ startDate + ", endDate=" + endDate + ", leavetype=" + leavetype + ", reason=" + reason + ", status="
				+ status + ", employee=" + employee + "]";
	}

	public LeaveRequest() {
		super();
		this.status = "pending";
	}

	public LeaveRequest(Long leaverequestid) {
		super();
		this.leaverequestid = leaverequestid;
	}

	public LeaveRequest(Long leaverequestid, Long employeeid) {
		super();
		this.leaverequestid = leaverequestid;
		this.employeeid = employeeid;
	}

	public LeaveRequest(Long leaverequestid, Long employeeid, LocalDate startDate) {
		super();
		this.leaverequestid = leaverequestid;
		this.employeeid = employeeid;
		this.startDate = startDate;
	}

	public LeaveRequest(Long leaverequestid, Long employeeid, LocalDate startDate, LocalDate endDate) {
		super();
		this.leaverequestid = leaverequestid;
		this.employeeid = employeeid;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public LeaveRequest(Long leaverequestid, Long employeeid, LocalDate startDate, LocalDate endDate,
			String leavetype) {
		super();
		this.leaverequestid = leaverequestid;
		this.employeeid = employeeid;
		this.startDate = startDate;
		this.endDate = endDate;
		this.leavetype = leavetype;
	}

	public LeaveRequest(Long leaverequestid, Long employeeid, LocalDate startDate, LocalDate endDate, String leavetype,
			String reason) {
		super();
		this.leaverequestid = leaverequestid;
		this.employeeid = employeeid;
		this.startDate = startDate;
		this.endDate = endDate;
		this.leavetype = leavetype;
		this.reason = reason;
	}

	public int getNumberOfDays(LocalDate startDate, LocalDate endDate) {
	    long days = ChronoUnit.DAYS.between(startDate, endDate);
	    return (int) days;
	}
	
	

    // getters and setters
}
