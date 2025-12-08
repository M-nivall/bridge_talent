package com.example.Varsani.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.Employers.Models.EmployerModel;

import java.util.Date;

public class SessionHandler {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_CLIENT_ID = "clientID";
    private static final String KEY_ID = "ID";
    private static final String KEY_FIRST_NAME = "first_name";
    private static final String KEY_LAST_NAME = "last_name";
    private static final String KEY_EMAIL= "email";
    private static final String KEY_EXPIRES = "expires";
    private static final String KEY_USER_NAME = "username";
    private static final String KEY_DATE_CREATED = "dateCreated";
    private static final String KEY_EMPTY = "";
    private static final String KEY_PHONE_NO = "phone_no";
    // check user type
    private static final String KEY_USER_TYPE = "user_type";

    private static final String KEY_BIO = "bio";
    private static final String KEY_SKILLS = "skills";
    private static final String KEY_EDUCATION = "education";
    private static final String KEY_PROFILE_URL = "profileUrl";


    private static final String KEY_COMPANY_NAME = "companyName";
    private static final String KEY_INDUSTRY = "industry";
    private static final String KEY_CONTACTS = "contacts";
    private static final String KEY_EMAIL_ADDRESS = "emailAddress";
    private static final String KEY_WEBSITE = "website";


    private Context mContext;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mPreferences;

    public SessionHandler(Context mContext) {
        this.mContext = mContext;
        mPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.mEditor = mPreferences.edit();
    }

    /**
     * Logs in the user by saving user details and setting session
     *
     * @param firstname
     * @param username
     */
    public void loginUser(String clientID,String firstname,String lastname,String username,String phone_no,
                          String email,String dateCreated,String user_type,String bio,String skills,String education,String profileUrl ) {

        mEditor.putString(KEY_CLIENT_ID, clientID);
        mEditor.putString(KEY_FIRST_NAME, firstname);
        mEditor.putString(KEY_LAST_NAME, lastname);
        mEditor.putString(KEY_USER_NAME, username);
        mEditor.putString(KEY_PHONE_NO, phone_no);
        mEditor.putString(KEY_EMAIL, email);
        mEditor.putString(KEY_DATE_CREATED, dateCreated);
        mEditor.putString(KEY_USER_TYPE,user_type);
        mEditor.putString(KEY_BIO, bio);
        mEditor.putString(KEY_SKILLS, skills);
        mEditor.putString(KEY_EDUCATION, education);
        mEditor.putString(KEY_PROFILE_URL, profileUrl);
        Date date = new Date();

        //Set user session for next 7 days
        long millis = date.getTime() + (7 * 24 * 60 * 60 * 1000);
        mEditor.putLong(KEY_EXPIRES, millis);
        mEditor.commit();
    }

    public void loginUser_2(String clientID,String firstname,String lastname,String username,String phoneno,
                          String email,String dateCreated,String user_type) {

        mEditor.putString(KEY_CLIENT_ID, clientID);
        mEditor.putString(KEY_FIRST_NAME, firstname);
        mEditor.putString(KEY_LAST_NAME, lastname);
        mEditor.putString(KEY_USER_NAME, username);
        mEditor.putString(KEY_PHONE_NO, phoneno);
        mEditor.putString(KEY_EMAIL, email);
        mEditor.putString(KEY_DATE_CREATED, dateCreated);
        mEditor.putString(KEY_USER_TYPE,user_type);
//        mEditor.putString(KEY_PHOTO_URL, photoUrl);
        Date date = new Date();

        //Set user session for next 7 days
        long millis = date.getTime() + (7 * 24 * 60 * 60 * 1000);
        mEditor.putLong(KEY_EXPIRES, millis);
        mEditor.commit();
    }

    public void loginUser_3(String employerID,String companyName,String username,String industry,String contacts,
                            String emailAddress,String website,String user_type) {

        mEditor.putString(KEY_CLIENT_ID, employerID);
        mEditor.putString(KEY_COMPANY_NAME, companyName);
        mEditor.putString(KEY_LAST_NAME, username);
        mEditor.putString(KEY_INDUSTRY, industry);
        mEditor.putString(KEY_CONTACTS, contacts);
        mEditor.putString(KEY_EMAIL_ADDRESS, emailAddress);
        mEditor.putString(KEY_WEBSITE, website);
        mEditor.putString(KEY_USER_TYPE,user_type);
//        mEditor.putString(KEY_PHOTO_URL, photoUrl);
        Date date = new Date();

        //Set user session for next 7 days
        long millis = date.getTime() + (7 * 24 * 60 * 60 * 1000);
        mEditor.putLong(KEY_EXPIRES, millis);
        mEditor.commit();
    }


    /**
     * Checks whether user is logged in
     *
     * @return
     */

    public boolean isLoggedIn() {
        Date currentDate = new Date();

        long millis = mPreferences.getLong(KEY_EXPIRES, 0);

        /* If shared preferences does not have a value
         then user is not logged in
         */
        if (millis == 0) {
            return false;
        }
        Date expiryDate = new Date(millis);

        /* Check if session is expired by comparing
        current date and Session expiry date
        */
        return currentDate.before(expiryDate);
    }

    /**
     * Fetches and returns user details
     *
     * @return user details
     */
    public UserModel getUserDetails() {
        //Check if user is logged in first
        if (!isLoggedIn()) {
            return null;
        }
        UserModel user = new UserModel();
        user.setUser_type(mPreferences.getString(KEY_USER_TYPE, KEY_EMPTY));
        user.setClientID(mPreferences.getString(KEY_CLIENT_ID, KEY_EMPTY));
        user.setFirstname(mPreferences.getString(KEY_FIRST_NAME, KEY_EMPTY));
        user.setLastname(mPreferences.getString(KEY_LAST_NAME, KEY_EMPTY));
        user.setUsername(mPreferences.getString(KEY_USER_NAME, KEY_EMPTY));
        user.setEmail(mPreferences.getString(KEY_EMAIL, KEY_EMPTY));
        user.setPhoneNo(mPreferences.getString(KEY_PHONE_NO, KEY_EMPTY));
        user.setDateCreated(mPreferences.getString(KEY_DATE_CREATED, KEY_EMPTY));

        user.setBio(mPreferences.getString(KEY_BIO, KEY_EMPTY));
        user.setSkills(mPreferences.getString(KEY_SKILLS, KEY_EMPTY));
        user.setEducation(mPreferences.getString(KEY_EDUCATION, KEY_EMPTY));
        user.setProfileUrl(mPreferences.getString(KEY_PROFILE_URL, KEY_EMPTY));

        return user;
    }
    public EmployerModel getUserDetails_2() {
        //Check if user is logged in first
        if (!isLoggedIn()) {
            return null;
        }
        EmployerModel user = new EmployerModel();
        user.setUser_type(mPreferences.getString(KEY_USER_TYPE, KEY_EMPTY));
        user.setClientID(mPreferences.getString(KEY_CLIENT_ID, KEY_EMPTY));
        user.setCompanyName(mPreferences.getString(KEY_COMPANY_NAME, KEY_EMPTY));
        user.setIndustry(mPreferences.getString(KEY_INDUSTRY, KEY_EMPTY));
        user.setUsername(mPreferences.getString(KEY_USER_NAME, KEY_EMPTY));
        user.setContacts(mPreferences.getString(KEY_CONTACTS, KEY_EMPTY));
        user.setEmailAddress(mPreferences.getString(KEY_EMAIL_ADDRESS, KEY_EMPTY));
        user.setWebsite(mPreferences.getString(KEY_WEBSITE, KEY_EMPTY));

        return user;
    }


    /**
     * Logs out user by clearing the session
     */
    public void logoutUser(){
        mEditor.clear();
        mEditor.commit();

    }
}
