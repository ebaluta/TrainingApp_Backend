package pl.edyta.springbootsecurityjwt.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edyta.springbootsecurityjwt.models.Training;
import pl.edyta.springbootsecurityjwt.models.User;
import pl.edyta.springbootsecurityjwt.payload.request.AddTraining;
import pl.edyta.springbootsecurityjwt.repo.TrainingRepo;
import pl.edyta.springbootsecurityjwt.repo.UserRepository;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/trainings")
public class TrainingController {

    @Autowired
    TrainingRepo trainingRepo;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/all")
    public List <Training> allAccess(){
     List <Training> all= new ArrayList<>();
     trainingRepo.findAll().forEach(all::add);
     return all;
    }

    @PostMapping("/user/add")
    @PreAuthorize("hasRole('USER')")
    public Training addTraining(@RequestBody AddTraining addTraining){
        Training training=new Training();
        training.setDate(addTraining.getDate());
        training.setDistance(addTraining.getDistance());
        training.setDuration(addTraining.getDuration());
        training.setDescription(addTraining.getDescription());
        User user= userRepository.findByUsername(addTraining.getUsername()).get();
        training.setUser(user);

        return trainingRepo.save(training);
    }

    @PostMapping( value = "/user/update/{id}")
    @PreAuthorize("hasRole('USER')")
    public Training updateTraining(@RequestBody AddTraining addTraining,
                                  @PathVariable int id,
                                   @RequestHeader("Username") String username){

        Training training=new Training();
        training.setDate(addTraining.getDate());
        training.setDistance(addTraining.getDistance());
        training.setDuration(addTraining.getDuration());
        training.setDescription(addTraining.getDescription());
        User user= userRepository.findByUsername(addTraining.getUsername()).get();
        training.setUser(user);
        training.setId((long) id);

        String retirevedUsername = user.getUsername();
        if(retirevedUsername.equals(username)) {
            return trainingRepo.save(training);
        } return null;
    }


    @GetMapping("/{username}/training")
    @PreAuthorize("hasRole('USER')")
    public List <Training> getUserTrainings(@PathVariable String username){
        User user= userRepository.findByUsername(username).get();
        List <Training> usersTrainings=new ArrayList<>();
        trainingRepo.findAllByUser(user).forEach(usersTrainings::add);
        return usersTrainings;
    }

    @GetMapping("/{id}")
    public Training getTrainingById(
            @PathVariable int id
    ){
        Training training = trainingRepo.findDistinctById((long)id);

        return training;
    }

}
