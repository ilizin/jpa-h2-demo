package me.ilizin.spring_demo.springboot_demo.jpa_h2_demo.dao;

import me.ilizin.spring_demo.springboot_demo.jpa_h2_demo.entity.Student;

public interface StudentDAO {
    void save(Student student);

    Student findById(Integer id);
}
