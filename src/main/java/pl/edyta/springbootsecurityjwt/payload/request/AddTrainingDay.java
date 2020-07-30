package pl.edyta.springbootsecurityjwt.payload.request;

import lombok.Data;

@Data
public class AddTrainingDay {


    private String day;

    private double duration;

    private double distance;

    private String description;

}
