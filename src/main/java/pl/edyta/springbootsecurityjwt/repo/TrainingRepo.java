package pl.edyta.springbootsecurityjwt.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.edyta.springbootsecurityjwt.models.Training;
import pl.edyta.springbootsecurityjwt.models.User;

import java.util.List;

@Repository
public interface TrainingRepo extends CrudRepository <Training, Long> {

    List<Training> findAllByUser(User user);
    Training findDistinctById(Long id);


}
