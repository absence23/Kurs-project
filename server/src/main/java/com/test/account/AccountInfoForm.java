package com.test.account;

import com.test.article.Article;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;


public class AccountInfoForm {

    private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

    @Length(max = 20)
    private String name;

    @Length(max = 30)
    private String country;

    @Length(max = 80)
    private String education;

    @Length(max = 100)
    private String interests;

    @Length(max = 500)
    private String about;


    public AccountInfoForm(){
        super();
    }
    public AccountInfoForm(Account account){
        AccountInfo info = account.getInfo();
        name = info.getName();
        education = info.getEducation();
        country = info.getCountry();
        interests = info.getInterests();
        about = info.getAbout();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public void setInfo(Account account) {
        AccountInfo info = account.getInfo();
        info.setAbout(getAbout());
        info.setCountry(getCountry());
        info.setEducation(getEducation());
        info.setInterests(getInterests());
        info.setName(getName());
    }
}