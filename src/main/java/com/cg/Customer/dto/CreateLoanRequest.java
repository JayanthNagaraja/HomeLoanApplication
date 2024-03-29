package com.cg.Customer.dto;

import java.sql.Date;

import javax.validation.constraints.NotBlank;

public class CreateLoanRequest {
	
	@NotBlank
	private Date date;
	@NotBlank
	private float applyamount;
	private float approveamount;
	private boolean landverify;
	private boolean financeverify;
	private boolean adminapprove;
	private String status;
	@NotBlank
	private int id;
	
	public CreateLoanRequest() {
		super();
	}

	public CreateLoanRequest(@NotBlank Date date, @NotBlank float applyamount, float approveamount, boolean landverify,
			boolean financeverify, boolean adminapprove, String status, @NotBlank int id) {
		super();
		this.date = date;
		this.applyamount = applyamount;
		this.approveamount = approveamount;
		this.landverify = landverify;
		this.financeverify = financeverify;
		this.adminapprove = adminapprove;
		this.status = status;
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public float getApplyamount() {
		return applyamount;
	}

	public void setApplyamount(float applyamount) {
		this.applyamount = applyamount;
	}

	public float getApproveamount() {
		return approveamount;
	}

	public void setApproveamount(float approveamount) {
		this.approveamount = approveamount;
	}

	public boolean isLandverify() {
		return landverify;
	}

	public void setLandverify(boolean landverify) {
		this.landverify = landverify;
	}

	public boolean isFinanceverify() {
		return financeverify;
	}

	public void setFinanceverify(boolean financeverify) {
		this.financeverify = financeverify;
	}

	public boolean isAdminapprove() {
		return adminapprove;
	}

	public void setAdminapprove(boolean adminapprove) {
		this.adminapprove = adminapprove;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "CreateLoanRequest [date=" + date + ", applyamount=" + applyamount + ", approveamount=" + approveamount
				+ ", landverify=" + landverify + ", financeverify=" + financeverify + ", adminapprove=" + adminapprove
				+ ", status=" + status + ", id=" + id + "]";
	}
	
}
