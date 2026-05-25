/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loginregistrationpoe2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author William
 */
public class LoginLogicPoeP2 {
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String phoneNumber;

    public LoginLogicPoeP2(String firstname, String lastname, String username, String password, String phoneNumber) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
    private boolean registered = false;
    
//Getters and setters
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String Firstname) {
        this.firstname = Firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String Lastname) {
        this.lastname = Lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String Username) {
        this.username = Username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String Password) {
        this.password = Password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.phoneNumber = PhoneNumber;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean Registered) {
        this.registered = Registered;
    }
/*
   Checks Username
    that has underscores and a lenght of 5 chatchers
    */
    
    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }
/*
    Check password
    that has Special characters 
    includes 8 characters
    capital letters 
    contains numbers
    */
    public boolean checkPasswordComplexity(String password) {
        String passwordPattern = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$";
        Pattern regexPattern = Pattern.compile(passwordPattern);
        Matcher match = regexPattern.matcher(password);
        return match.matches();
    }
/*
    check cellphone
    That start +27
    That adds with 9 digits
    */
    public boolean checkCellPhoneNumbers(String phoneNumbers) {
        String phoneNumberPattern = "^\\+27\\d{9}$";
        Pattern regexPattern = Pattern.compile(phoneNumberPattern);
        Matcher match = regexPattern.matcher(phoneNumbers);
        return match.matches();
    }
  //LoginUser that return username and password
    public boolean loginUser(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
//returnLoginStatus 
    public String returnLoginStatus(String username, String password, String Username, String Password, String PhoneNumber) {
        return returnLoginStatus(username, password);
    }
    
//Includes the first name + lastname and output the user name and last name
    public String returnLoginStatus(String username, String password) {
        if (loginUser(username, password)) {
            return "Welcome " + this.firstname + ", " +this.lastname + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
    
    //Includes username and passwords
        public String registerUser(String username, String password) {
        if (checkUserName(username) && checkPasswordComplexity(password)) {
            registered = true; 
            return "The two above conditions have been met , and the user has been registered successfully ";
        } else {
            return "The username is incorrectly formatted or the password does not meet the complexity requirements ";
        }
        }
      public boolean registered() {
        return registered; 
        
        
    }
      
      
}

    
