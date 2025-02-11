package com.avaliakids.repositories;

import com.avaliakids.models.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface StudentRepository extends MongoRepository<Student, String> {
    List<Student> findByParentId(String parentId);
}
