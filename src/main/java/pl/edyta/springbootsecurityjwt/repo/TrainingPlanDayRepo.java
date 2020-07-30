package pl.edyta.springbootsecurityjwt.repo;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;
import pl.edyta.springbootsecurityjwt.models.TrainingPlanDay;

import java.util.List;

@Repository
public interface TrainingPlanDayRepo extends MongoRepository <TrainingPlanDay,Integer> {

    List<TrainingPlanDay> findAllByUsername(String username);
    TrainingPlanDay findDistinctByDayAndUsername(String day, String username);


}
