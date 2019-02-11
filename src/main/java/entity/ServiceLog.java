package entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="servicelog")
public class ServiceLog {
    @Id
    @Column(name= "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name="request", columnDefinition = "TEXT")
    private String request;

    @Column(name="response", columnDefinition = "TEXT")
    private String response;

    @Column(name="error", columnDefinition = "TEXT")
    private String error;

    @NotNull
    @Column(name="createdtm")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createDTM;

    public ServiceLog() {
    }

    public ServiceLog(String request, String response, String error, Date createDTM) {
        this.request = request;
        this.response = response;
        this.error = error;
        this.createDTM = createDTM;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Date getCreateDTM() {
        return createDTM;
    }

    public void setCreateDTM(Date createDTM) {
        this.createDTM = createDTM;
    }
}
