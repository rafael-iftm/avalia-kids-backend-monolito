package com.avaliakids.repositories;

import com.avaliakids.models.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface QuestionRepository extends MongoRepository<Question, String> {
    List<Question> findByClassLevel(String classLevel);
}
