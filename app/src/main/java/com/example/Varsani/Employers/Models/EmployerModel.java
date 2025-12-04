package com.example.Varsani.Employers.Models;

public class EmployerModel {

    String employerID;
    String companyName;
    String username;
    String industry;
    String contacts;
    String emailAddress;
    String user_type;

    String website;



    public String getClientID() {
        return employerID;
    }
    public void setClientID(String employerID) {
        this.employerID = employerID;
    }


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String employerID) {
        this.companyName = employerID;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getIndustry() {
        return industry;
    }
    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }


}
