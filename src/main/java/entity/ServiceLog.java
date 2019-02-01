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
    @Column(name="request", length = 4000)
    private String request;

    @Column(name="response", length = 4000)
    private String response;

    @Column(name="error", length = 4000)
    private String error;

    @NotNull
    @Column(name="createdtm")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createDTM;
}
