package me.ilizin.spring_demo.springboot_demo.jpa_h2_demo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import me.ilizin.spring_demo.springboot_demo.jpa_h2_demo.entity.Task;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Specialized annotation for repositories, supports component scanning, translate JDBC exceptions
@Repository
public class TaskDAOImpl implements TaskDAO {

    private final EntityManager entityManager;

    public TaskDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public void save(Task task) {
        entityManager.persist(task);
    }

    // The Transactional annotation is not necessary because we're just querying
    @Override
    public Task findById(Integer id) {
        return entityManager.find(Task.class, id);
    }

    @Override
    public List<Task> findAll() {
        /* FROM Task --> Task is the JPA entity name and not the database table name.
           All JPQL syntax is based on entity name and entity fields.
           By default, the order is ascending, but for descending order use 'desc'*/
        TypedQuery<Task> query = entityManager.createQuery("FROM Task order by title", Task.class);
        return query.getResultList();
    }

    @Override
    public List<Task> findByWord(String word) {
        TypedQuery<Task> query = entityManager.createQuery("FROM Task WHERE title like '%'||:word||'%' or description like '%'||:word||'%'",Task.class);
        query.setParameter("word", word);
        return query.getResultList();
    }

    @Transactional
    @Override
    public void update(Task task) {
        entityManager.merge(task);
    }

    @Transactional
    @Override
    public void delete(Task task) {
        /*
         * You can also delete using a query
         * int numRowsDeleted = entityManager.createQuery("DELETE FROM Task WHERE title='test title'").executeUpdate();
         * //Why are we saying executeUpdate if we're deleting ? The method name "Update" is a generic term to say we're modifying the database
         */
        entityManager.remove(task);
    }

    @Transactional
    @Override
    public int deleteAll() {
       int numRowsDeleted = entityManager.createQuery("DELETE FROM Task")
                .executeUpdate();
       return numRowsDeleted;
    }
}
