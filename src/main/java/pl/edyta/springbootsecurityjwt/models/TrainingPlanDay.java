package pl.edyta.springbootsecurityjwt.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


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
