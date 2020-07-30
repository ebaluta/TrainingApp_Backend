package pl.edyta.springbootsecurityjwt.payload.request;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
public class AddTraining {

    @NotNull
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date date;

    @NotNull
    private double distance;

    @NotNull
    private double duration;

    @NotNull
    private String username;

    private String description;

}
