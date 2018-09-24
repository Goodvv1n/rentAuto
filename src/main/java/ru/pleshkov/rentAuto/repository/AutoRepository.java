package ru.pleshkov.rentAuto.repository;

import org.springframework.data.repository.CrudRepository;
import ru.pleshkov.rentAuto.entity.Auto;

import java.util.List;

/**
 * @author pleshkov on 23.09.2018.
 */
public interface AutoRepository extends CrudRepository<Auto, Long>{
    List<Auto> findByBrand(String brand);
    List<Auto> findByYear(Integer year);
    List<Auto> findByBrandAndYear(String brand, Integer year);
}
