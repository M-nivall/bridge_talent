package com.example.Varsani.Applicants.Models;

public class JobsModel {
    private String jobID;
    private String employerID;
    private String title;
    private String description;
    private String location;
    private String jobType;
    private String salaryRange;
    private String datePosted;
    private String deadline;
    private String jobStatus;
    private String companyName;
    private String contacts;
    private String email;
    private String industry;
    private String employerDescription;

    public JobsModel(String jobID, String employerID, String title, String description, String location,
                    String jobType, String salaryRange, String datePosted, String deadline,
                    String jobStatus, String companyName, String contacts, String email,
                    String industry, String employerDescription) {

        this.jobID = jobID;
        this.employerID = employerID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.jobType = jobType;
        this.salaryRange = salaryRange;
        this.datePosted = datePosted;
        this.deadline = deadline;
        this.jobStatus = jobStatus;
        this.companyName = companyName;
        this.contacts = contacts;
        this.email = email;
        this.industry = industry;
        this.employerDescription = employerDescription;
    }

    public String getJobID() {
        return jobID;
    }

    public String getEmployerID() {
        return employerID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getJobType() {
        return jobType;
    }

    public String getSalaryRange() {
        return salaryRange;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getContacts() {
        return contacts;
    }

    public String getEmail() {
        return email;
    }

    public String getIndustry() {
        return industry;
    }

    public String getEmployerDescription() {
        return employerDescription;
    }

}
