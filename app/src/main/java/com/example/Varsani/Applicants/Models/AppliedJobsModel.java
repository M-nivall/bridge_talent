package com.example.Varsani.Applicants.Models;

public class AppliedJobsModel {

    private String applicationID;
    private String jobID;
    private String dateApplied;
    private String applicationStatus;
    private String title;
    private String description;
    private String location;
    private String jobType;
    private String companyName;
    private String email;
    private String industry;

    public AppliedJobsModel(String applicationID, String jobID, String dateApplied,
                            String applicationStatus, String title, String description,
                            String location, String jobType, String companyName,
                            String email, String industry) {

        this.applicationID = applicationID;
        this.jobID = jobID;
        this.dateApplied = dateApplied;
        this.applicationStatus = applicationStatus;
        this.title = title;
        this.description = description;
        this.location = location;
        this.jobType = jobType;
        this.companyName = companyName;
        this.email = email;
        this.industry = industry;
    }

    public String getApplicationID() {
        return applicationID;
    }

    public String getJobID() {
        return jobID;
    }

    public String getDateApplied() {
        return dateApplied;
    }

    public String getApplicationStatus() {
        return applicationStatus;
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

    public String getCompanyName() {
        return companyName;
    }

    public String getEmail() {
        return email;
    }

    public String getIndustry() {
        return industry;
    }
}
