package me.ilizin.spring_demo.springboot_demo.jpa_h2_demo.dao;

import jakarta.persistence.EntityManager;
import me.ilizin.spring_demo.springboot_demo.jpa_h2_demo.entity.Student;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

// Specialized annotation for repositories, supports component scanning, translate JDBC exceptions
@Repository
public class StudentDAOImpl implements StudentDAO {

    private final EntityManager entityManager;

    public StudentDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public void save(Student student) {
        entityManager.persist(student);
    }

    // The Transactional annotation is not necessary because we're just querying
    @Override
    public Student findById(Integer id) {
        return entityManager.find(Student.class, id);
    }
}
