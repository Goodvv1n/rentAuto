package ru.pleshkov.rentAuto.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.pleshkov.rentAuto.entity.Client;

import java.util.List;

/**
 * @author pleshkov on 20.09.2018.
 */
@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {
    List<Client> findByName(String name);
    List<Client> findByBirthYear(Integer birthYear);
    List<Client> findByNameAndBirthYear(String name, Integer birthYear);
}
