package ru.pleshkov.rentAuto.repository;

import org.springframework.data.repository.CrudRepository;
import ru.pleshkov.rentAuto.entity.Rent;

import java.util.List;

/**
 * Репозиторий для хранения событий аренды
 * @author pleshkov on 23.09.2018.
 */
public interface RentRepository extends CrudRepository<Rent, Long>{
    List<Rent> findByClientId(Long clientId);
    List<Rent> findByAutoId(Long autoId);
    List<Rent> findByClientIdAndAutoId(Long clientId, Long autoId);
}
