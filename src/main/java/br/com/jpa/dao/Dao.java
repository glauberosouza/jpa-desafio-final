package br.com.jpa.dao;

import br.com.jpa.model.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class Dao<T> {
    private Class<T> classz;
    private static EntityManagerFactory entityManagerFactory;
    private final EntityManager entityManager;

    static {
        try {
            entityManagerFactory = Persistence
                    .createEntityManagerFactory("jpa-desafio");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Dao() {
        this(null);
    }

    public Dao(Class<T> classz) {
        this.classz = classz;
        entityManager = entityManagerFactory.createEntityManager();
    }

    public Dao<T> save(T entity) {
        openTransaction().entityManager.persist(entity);
        return commitTransaction();
    }

    public T findById(Object id) {
        return entityManager.find(classz, id);
    }

    public List<T> findAll() {
        if (classz == null) {
            throw new IllegalArgumentException("O retorno não pode ser null");
        }
        var jpql = "SELECT p FROM " + classz.getName() + " p";
        var query = entityManager.createQuery(jpql, classz);
        return query.getResultList();
    }

    public void update(long id, int age, String name, String zipCode, String street) {
        var person = openTransaction().entityManager.find(Person.class, id);

        if (person != null) {
            person.setName(name);
            person.setAge(age);

            var personAddress = person.getAddress();
            personAddress.setZipcode(zipCode);
            personAddress.setStreet(street);

            entityManager.merge(person);
        }
        commitTransaction();
    }

    public void delete(long id) {
        openTransaction();
        var person = entityManager.find(Person.class, id);

        if (person == null) {
            System.out.println("Pessoa com ID " + id + " não encontrada.");
        }

        entityManager.remove(person);
        commitTransaction();
    }

    public Dao<T> openTransaction() {
        entityManager.getTransaction().begin();
        return this;
    }


    public Dao<T> commitTransaction() {
        entityManager.getTransaction().commit();
        return this;
    }

    public void close() {
        entityManager.close();
    }
}
