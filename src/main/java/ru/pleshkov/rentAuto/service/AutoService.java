package ru.pleshkov.rentAuto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pleshkov.rentAuto.SAPIException;
import ru.pleshkov.rentAuto.entity.Auto;
import ru.pleshkov.rentAuto.repository.AutoRepository;
import ru.pleshkov.rentAuto.restBean.NewAuto;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для обработки действий с автомобилями
 * @author pleshkov on 23.09.2018.
 */
@Service
public class AutoService {

    @Autowired
    private AutoRepository autoRepository;

    /**
     * Получить список автомобилей
     * @param brand марка авто
     * @return результат
     */
    public List<Auto> getAutoList(String brand){
        return findAuto(brand);
    }

    /**
     * Получить весь список автомобилей
     * @return результат
     */
    public List<Auto> getAutoList(){
        return getAutoList(null);
    }

    /**
     * Добавление автомобиля
     * Если такой автомобиль уже сущестует, будет возвращен экземпляр существующего в БД авто.
     * @param newAuto добавляемый автомобиль
     */
    public Auto addAuto(NewAuto newAuto) throws SAPIException {
        if (isAutoExist(newAuto)){
           return findAutoByName(newAuto.getBrand());
        }
        Auto auto = new Auto();
        auto.setBrand(newAuto.getBrand());
        auto.setYear(newAuto.getYear());
        auto.setClientId(newAuto.getClientId());
        return autoRepository.save(auto);
    }

    /**
     * Обновить сущность авто
     * @param auto автомобиль
     */
    void updateAuto(Auto auto){
        autoRepository.save(auto);
    }

    /**
     * Удаление автомобиля
     * @param auto автомобиль
     * @throws SAPIException ошибка сервиса
     */
    public void deleteAuto(Auto auto) throws SAPIException {
        if (!isAutoExist(auto)){
            throw new SAPIException("Auto not found");
        }
        autoRepository.delete(auto);
    }

    /**
     * Удаление автомобиля
     * @param brand марка автомобиля
     * @throws SAPIException ошибка сервиса
     */
    public void deleteAuto(String brand) throws SAPIException {
        Auto auto = findAutoByName(brand);
        autoRepository.delete(auto);
    }

    public Auto findAutoByName(String brand) throws SAPIException {
        ArrayList<Auto> list = (ArrayList<Auto>) findAuto(brand);
        if (list == null || list.size() == 0) {
            throw new SAPIException("Auto " + brand +" not found");
        } else if (list.size() > 1){
            throw new SAPIException("Found more than one item");
        }
        return list.get(0);
    }

    /**
     * Получение списка автомобилей
     * Марка и год выпуска не обязательные параметры. Если параметр не указан - игнорируется.
     * @param brand Марка авто
     * @return список
     */
    private List<Auto> findAuto(String brand){
        if (brand == null || brand.isEmpty()){
            return (List<Auto>) autoRepository.findAll();
        }
        return autoRepository.findByBrand(brand);
    }

    /**
     * Проверка существования автомобиля
     * @param newAuto авто
     * @return результат
     */
    private boolean isAutoExist(NewAuto newAuto){
        return !(findAuto(newAuto.getBrand()).size() == 0);
    }

    /**
     * Проверка существования авто
     * @param auto клиент
     * @return результат
     */
    private boolean isAutoExist(Auto auto){
        return autoRepository.findById(auto.getId()).isPresent();
    }
}
