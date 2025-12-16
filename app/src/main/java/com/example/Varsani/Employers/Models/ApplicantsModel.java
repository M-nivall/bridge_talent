package com.example.Varsani.Employers.Models;

public class ApplicantsModel {

    private String applicationId;
    private String applicantId;
    private String cvUrl;
    private String coverLater;
    private String salary;
    private String noticePeriod;
    private String dateApplied;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String bio;
    private String skills;
    private String education;
    private String profileUrl;
    private String applicationStatus;

    // Constructor
    public ApplicantsModel(
            String applicationId,
            String applicantId,
            String cvUrl,
            String coverLater,
            String salary,
            String noticePeriod,
            String dateApplied,
            String firstName,
            String lastName,
            String email,
            String phone,
            String bio,
            String skills,
            String education,
            String profileUrl,
            String applicationStatus
    ) {
        this.applicationId = applicationId;
        this.applicantId = applicantId;
        this.cvUrl = cvUrl;
        this.coverLater = coverLater;
        this.salary = salary;
        this.noticePeriod = noticePeriod;
        this.dateApplied = dateApplied;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.bio = bio;
        this.skills = skills;
        this.education = education;
        this.profileUrl = profileUrl;
        this.applicationStatus = applicationStatus;
    }

    // Getters
    public String getApplicationId() {
        return applicationId;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public String getCvUrl() {
        return cvUrl;
    }

    public String getCoverLater() {
        return coverLater;
    }

    public String getSalary() {
        return salary;
    }

    public String getNoticePeriod() {
        return noticePeriod;
    }

    public String getDateApplied() {
        return dateApplied;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getBio() {
        return bio;
    }

    public String getSkills() {
        return skills;
    }

    public String getEducation() {
        return education;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }
}
