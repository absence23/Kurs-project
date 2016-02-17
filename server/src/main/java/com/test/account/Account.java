package com.test.account;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.article.Article;

import java.util.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "account")
public class Account implements java.io.Serializable {

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true)
	private String email;
	
	@JsonIgnore
	private String password;

	private String role = "ROLE_USER";

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Article> articles = new ArrayList<Article>();

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "account")
	private AccountInfo info = new AccountInfo();

    protected Account() {}
	
	public Account(String email, String password, String role) {
        info = new AccountInfo("", "", "", "", "", this);
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public AccountInfo getInfo(){
		return info;
	}

	public void setInfo(AccountInfo info){
		this.info = info;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void addArticle(Article userFile) {
		boolean isSuchExist = false;
		for(Article uf: articles){
			if(uf.getName().equals(userFile.getName())) {
				isSuchExist = true;
				break;
			}
		}
		if(!isSuchExist)
			this.articles.add(userFile);
	}

    public List<Article> getArticles(){
        return this.articles;
    }

	public void deleteArticle(String name){
        for(Article uf: articles){
            if(uf.getName().equals(name)) {
                articles.remove(uf);
                break;
            }
        }
	}


    public boolean isArticleExist(String name){
        for(Article a: articles)
            if(a.getName().equals(name))
                return true;
        return false;
    }

	public Article getArticleById(Long id){
		for(Article a: articles)
			if(a.getId().equals(id))
				return a;
		return Article.createEmpty(this);
	}
}
