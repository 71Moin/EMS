package com.nurul.TeamManagement.Entity;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;

@Entity
@Table(name="EMPLOYEE")
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Integer id;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	 private String lastName;
	
	@Column(name = "DATE_OF_BIRTH",nullable = false)
	 private Long dob;
	
	@Column(name = "GENDER")
	 private String gender;
	
	@Column(name = "PHONE_NUMBER",nullable = false)
	 private String phnNumber;
	
	@Column(name = "EMAIL",nullable = false,unique = true)
	 private String email;
	
	@Column(name = "HIRE_DATE",nullable = false)
	 private Long hireDate;
	
	@Column(name = "JOB_TITLE",nullable = false)
	 private String jobTitle;
	
	@Column(name = "DEPARTMENT_ID")
	 private Long departmentID;
	
	@Column(name = "SALARY")
	 private Float salary;
	
	@Column(name = "TO_REPORT")
	 private Long toReport;
	
	@Column(name = "EMPLOYMENT_STATUS")
	 private String empStatus;
	
	@Column(name = "EMERGENCY_CONTACT_NAME")
	 private String emergencyContactName;
	
	@Column(name = "EMERGENCY_CONTACT_RELATIONSHIP")
	 private String emergencyContactRelationship;
	
	@Column(name = "EMERGENCY_CONTACT_PHONENUMBER")
	 private String emergencyContactPhoneNumber;
	
	@Column(name = "USER_NAME")
	 private String userName;
	
	@Column(name = "IMAGE")
	@Lob
	 private byte[] image;
	
	private transient MultipartFile imageFile;
	
	@Column(name = "CREATE_TIME")
	 private LocalDate createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getDob() {
		return dob;
	}

	public void setDob(Long dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhnNumber() {
		return phnNumber;
	}

	public void setPhnNumber(String phnNumber) {
		this.phnNumber = phnNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getHireDate() {
		return hireDate;
	}

	public void setHireDate(Long hireDate) {
		this.hireDate = hireDate;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	

	public Long getDepartmentName() {
		return departmentID;
	}

	public void setDepartmentName(Long departmentID) {
		this.departmentID = departmentID;
	}

	public Long getAssignedManager() {
		return toReport;
	}

	public void setAssignedManager(Long toReport) {
		this.toReport = toReport;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Float getSalary() {
		return salary;
	}

	public void setSalary(Float salary) {
		this.salary = salary;
	}

	public String getEmpStatus() {
		return empStatus;
	}

	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
	}

	public String getEmergencyContactName() {
		return emergencyContactName;
	}

	public void setEmergencyContactName(String emergencyContactName) {
		this.emergencyContactName = emergencyContactName;
	}

	public String getEmergencyContactRelationship() {
		return emergencyContactRelationship;
	}

	public void setEmergencyContactRelationship(String emergencyContactRelationship) {
		this.emergencyContactRelationship = emergencyContactRelationship;
	}

	public String getEmergencyContactPhoneNumber() {
		return emergencyContactPhoneNumber;
	}

	public void setEmergencyContactPhoneNumber(String emergencyContactPhoneNumber) {
		this.emergencyContactPhoneNumber = emergencyContactPhoneNumber;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
	
	public MultipartFile getImageFile() {
        return imageFile;
    }
	public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

	public LocalDate getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDate date) {
		this.createTime =  date;
	}
	
	
}
