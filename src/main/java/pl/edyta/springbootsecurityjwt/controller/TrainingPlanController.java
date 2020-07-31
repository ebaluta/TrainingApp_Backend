package pl.edyta.springbootsecurityjwt.controller;

import org.hibernate.sql.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edyta.springbootsecurityjwt.models.TrainingPlanDay;
import pl.edyta.springbootsecurityjwt.models.enums.Week;
import pl.edyta.springbootsecurityjwt.payload.request.AddTrainingDay;
import pl.edyta.springbootsecurityjwt.repo.TrainingPlanDayRepo;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Update.update;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/plan")
public class TrainingPlanController {

    TrainingPlanDayRepo trainingPlanDayRepo;

    @Autowired
    MongoOperations operations;


    @Autowired
    public TrainingPlanController(TrainingPlanDayRepo trainingPlanDayRepo) {
        this.trainingPlanDayRepo = trainingPlanDayRepo;
    }

    @PostMapping(value = "/add")
    @PreAuthorize("hasRole('USER')")
    public TrainingPlanDay addTraining(@RequestHeader("Username") String username,
                                       @RequestBody AddTrainingDay addTrainingDay){

        TrainingPlanDay trainingPlanDay = new TrainingPlanDay();
        trainingPlanDay.setDay(addTrainingDay.getDay());
        trainingPlanDay.setDistance(addTrainingDay.getDistance());
        trainingPlanDay.setDuration(addTrainingDay.getDuration());
        trainingPlanDay.setDescription(addTrainingDay.getDescription());
        trainingPlanDay.setUsername(username);

       return trainingPlanDayRepo.save(trainingPlanDay);
    }


    @GetMapping("/create")
    public void createWeekForUser(@RequestHeader("Username") String username){
        TrainingPlanDay trainingPlanDay = new TrainingPlanDay();
        trainingPlanDay.setUsername(username);
        List<String> days = Arrays.stream(Week.values()).map(day -> day.name()).collect(Collectors.toList());
        trainingPlanDay.setDescription("");

        for(int i =0 ; i< 7 ; i++){
            trainingPlanDay.setDay(days.get(i));
            trainingPlanDayRepo.save(trainingPlanDay);
        }
    }

    @GetMapping("/{username}/all")
    @PreAuthorize("hasRole('USER')")
    public List <TrainingPlanDay> getAllOfUser(@PathVariable String username) {
        return trainingPlanDayRepo.findAllByUsername(username);
    }

   @PostMapping("/update")
   @PreAuthorize("hasRole('USER')")
    public void updateTrainingDay(@RequestBody TrainingPlanDay trainingPlanDay,
                                  @RequestHeader("Username") String username) {

       Query query = new Query(where("Username").is(username).and("day").is(trainingPlanDay.getDay()));

       Update update = new Update();
       update.set("duration", trainingPlanDay.getDuration());
       update.set("distance", trainingPlanDay.getDistance());
       update.set("description", trainingPlanDay.getDescription());

       operations.updateFirst(query, update, TrainingPlanDay.class);


   }


   @GetMapping("/delete")
   @PreAuthorize("hasRole('USER')")
    public void deleteTrainingPlanDay(@RequestBody TrainingPlanDay trainingPlanDay,
                                      @RequestHeader("Username") String username){

       Query query = new Query(where("Username").is(username).and("day").is(trainingPlanDay.getDay()));
      operations.remove(query,TrainingPlanDay.class);
   }


}
