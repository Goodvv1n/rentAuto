package ru.pleshkov.rentAuto.repository;

import org.springframework.data.repository.CrudRepository;
import ru.pleshkov.rentAuto.entity.Client;

/**
 * @author pleshkov on 20.09.2018.
 */
public interface ClientRepository extends CrudRepository<Client, Long> {
}
