package entity;

import com.sun.istack.NotNull;
import dbhandler.dao.CookieSessionService;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

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


    public static Set<CookieSession> createCookieSession(User user) {
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();

        CookieSession cs = new CookieSession();
        cs.setUser(user);
        cs.setSessionStartDTM(date);

        c.add(Calendar.DAY_OF_YEAR, 2);  // advances day by 2
        date = c.getTime();

        cs.setSessionEndDTM(date);
        cs.setStatus("100");
        CookieSessionService csService = new CookieSessionService();
        csService.saveCookieSession(cs);

        return csService.findCookiSessByUserIdAndCode(user.getId(), "100");
    }
    public static void statusChange(CookieSession cookieSession) {
        cookieSession.setStatus("101");
        CookieSessionService csService = new CookieSessionService();
        csService.updateCookieSession(cookieSession);
    }
    public static CookieSession findById(Long id) {
        CookieSessionService csService = new CookieSessionService();
        return csService.findCookieSessionById(id);
    }
}
