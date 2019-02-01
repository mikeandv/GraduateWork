package entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="session")
public class CookieSession {

    @Id
    @Column(name= "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="userid")
    private User user;

    @NotNull
    @Column(name="sessionstartdtm")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date sessionStartDTM;

    @NotNull
    @Column(name="sessionenddtm")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date sessionEndDTM;

    @NotNull
    @Column(name="status", length = 10)
    private String status;

    public CookieSession() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getSessionStartDTM() {
        return sessionStartDTM;
    }

    public void setSessionStartDTM(Date sessionStartDTM) {
        this.sessionStartDTM = sessionStartDTM;
    }

    public Date getSessionEndDTM() {
        return sessionEndDTM;
    }

    public void setSessionEndDTM(Date sessionEndDTM) {
        this.sessionEndDTM = sessionEndDTM;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
