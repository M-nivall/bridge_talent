package com.example.Varsani.Staff.Finance.Models;

public class PaymentModel {
    private String jobID;
    private String title;
    private String jobCategory;
    private String jobLevel;
    private String description;
    private String qualifications;
    private String jobResponsibilities;
    private String location;
    private String jobType;
    private String salaryRange;
    private String datePosted;
    private String deadline;
    private String jobStatus;
    private String companyName;
    private String contacts;
    private String amount;
    private String paymentMethod;
    private String transactionCode;

    public PaymentModel
            (String jobID, String title, String jobCategory, String jobLevel,
                       String description, String qualifications, String jobResponsibilities,
                       String location, String jobType, String salaryRange,
                       String datePosted, String deadline, String jobStatus, String companyName,
             String contacts, String amount, String paymentMethod, String transactionCode) {

        this.jobID = jobID;
        this.title = title;
        this.jobCategory = jobCategory;
        this.jobLevel = jobLevel;
        this.description = description;
        this.qualifications = qualifications;
        this.jobResponsibilities = jobResponsibilities;
        this.location = location;
        this.jobType = jobType;
        this.salaryRange = salaryRange;
        this.datePosted = datePosted;
        this.deadline = deadline;
        this.jobStatus = jobStatus;
        this.companyName = companyName;
        this.contacts = contacts;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.transactionCode = transactionCode;
    }

    public String getJobID() { return jobID; }
    public String getTitle() { return title; }
    public String getJobCategory() { return jobCategory; }
    public String getJobLevel() { return jobLevel; }
    public String getDescription() { return description; }
    public String getQualifications() { return qualifications; }
    public String getJobResponsibilities() { return jobResponsibilities; }
    public String getLocation() { return location; }
    public String getJobType() { return jobType; }
    public String getSalaryRange() { return salaryRange; }
    public String getDatePosted() { return datePosted; }
    public String getDeadline() { return deadline; }
    public String getJobStatus() { return jobStatus; }
    public String getCompanyName() { return companyName; }
    public String getContacts() { return contacts; }
    public String getAmount() { return amount; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getTransactionCode() { return transactionCode; }
}
