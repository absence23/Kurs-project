package com.test.account;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "info")
public class AccountInfo implements java.io.Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String name = "";

    private String country = "";

    private String education = "";

    private String interests = "";

    private String about = "";

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "account_id")
    private Account account;

    protected AccountInfo(){}

    public AccountInfo(String name, String country, String education, String interests, String about, Account account) {
        this.name = name;
        this.country = country;
        this.education = education;
        this.interests = interests;
        this.about = about;
        this.account = account;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getInterests() {
        return interests;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
