package pl.edyta.springbootsecurityjwt.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;


@Document(collection = "plans")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingPlanDay {


    private String username;

    private String day;

    private double duration;

    private double distance;

    private String description;

}
