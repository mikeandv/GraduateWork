package entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="systemuser")
public class User {

    @Id
    @Column(name= "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name="username", length = 15)
    private String name;

    @NotNull
    @Column(name="email", length=320)
    private String email;

    @NotNull
    @Column(name="password", length=64)
    private String password;

    @NotNull
    @Column(name="createdtm")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createDTM;

    @Column(name="lastlogindtm")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date lastLoginDTM;

    @OneToMany (fetch = FetchType.LAZY, mappedBy = "user")
    private Set<CookieSession> cookieSessions;



    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateDTM() {
        return createDTM;
    }

    public void setCreateDTM(Date createDTM) {
        this.createDTM = createDTM;
    }

    public Date getLastLoginDTM() {
        return lastLoginDTM;
    }

    public void setLastLoginDTM(Date lastLoginDTM) {
        this.lastLoginDTM = lastLoginDTM;
    }

    public Set<CookieSession> getCookieSessions() {
        return cookieSessions;
    }

    public void setCookieSessions(Set<CookieSession> cookieSessions) {
        this.cookieSessions = cookieSessions;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name=" + name +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", createDTM=" + createDTM +
                ", lastLoginDTM=" + lastLoginDTM +
                ", cookieSessions=" + cookieSessions +
                '}';
    }
}
