/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.projet.domain;

import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import ma.projet.beans.Employe;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import ma.projet.beans.User;
import ma.projet.services.UserService;

/**
 *
 * @author HP
 */
@ManagedBean(name = "loginBean")
@SessionScoped

public class LoginBean {
private UserService userService;
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    // Getters and setters
    public void setPassword(String password) {
        this.password = password;
    }

    public String login() {
        if (isValidCredentials(username, password)) {
            System.out.println("============yes");
            return "index.xhtml"; 
        } else {
            return null;
        }
    }

    private boolean isValidCredentials(String username, String password) {
          List<User> users;
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            users = session.createQuery("FROM User WHERE username = :username AND password = :password").setParameter("username", username).setParameter("password", password).list();
            session.getTransaction().commit();
            if(users.size()==0){
                return false;
            }else
            return true;
    }
}
