package pl.edyta.springbootsecurityjwt.models;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Date;

@Entity
@Data
@Table(name="trainings")
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date date;

    private double distance;


    private double duration;

    @Size(max = 100)
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Training() {
    }

    public Training(Long id, Date date, double distance, double duration, User user) {
        this.id=id;
        this.date=date;
        this.distance=distance;
        this.duration= duration;
        this.user=user;
    }
}
