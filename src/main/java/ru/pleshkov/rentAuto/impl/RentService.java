package ru.pleshkov.rentAuto.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pleshkov.rentAuto.SAPIException;
import ru.pleshkov.rentAuto.entity.Auto;
import ru.pleshkov.rentAuto.entity.Client;
import ru.pleshkov.rentAuto.entity.Rent;
import ru.pleshkov.rentAuto.repository.RentRepository;
import ru.pleshkov.rentAuto.restBean.NewAuto;
import ru.pleshkov.rentAuto.restBean.NewClient;

import java.util.List;

/**
 * Сервис для заполнения данных об событиях аренды
 * @author pleshkov on 23.09.2018.
 */
@Service
public class RentService {

    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AutoService autoService;

    public List<Rent> findRentActions(String name, String autoBrand) throws SAPIException {
        Client client = clientService.findClient(name);
        Auto auto = autoService.findAutoByName(autoBrand);
        return getRentActions(client.getId(), auto.getId());
    }

    public List<Rent> findRentActions() throws SAPIException {
        return findRentActions(null, null);
    }

    /**
     * Удаление события аренды
     * @param rent событие аренды
     */
    public void deleteRentAction(Rent rent){
        rentRepository.delete(rent);
    }

    /**
     * Добавление события аренды
     * @param newClient новый клиент
     * @param newAuto новое авто
     * @throws SAPIException ошибка сервиса
     */
    public void addRentAction(NewClient newClient, NewAuto newAuto) throws SAPIException {
        Client client = clientService.addClient(newClient);
        Auto auto = autoService.addAuto(newAuto);
        addRentAction(client, auto);
    }

    /**
     * Добавление события аренды
     * @param client клиент
     * @param auto авто
     * @throws SAPIException ошибка сервиса
     */
    public void addRentAction(Client client, Auto auto) throws SAPIException {
        client.setAutoId(auto.getId());
        auto.setClientId(client.getId());
        autoService.updateAuto(auto);
        clientService.updateClient(client);
    }

    /**
     * Получение списка событий аренды
     * Ид клиента и ИД авто не обязательные параметры. Если параметр не указан - игнорируется.
     * @param clientId ИД клиента
     * @param autoId ИД авто
     * @return список
     */
    private List<Rent> getRentActions(Long clientId, Long autoId){
        if (clientId == null && autoId == null){
            return (List<Rent>) rentRepository.findAll();
        }
        if (clientId == null){
            return rentRepository.findByAutoId(autoId);
        }
        if (autoId == null){
            return rentRepository.findByClientId(clientId);
        }
        return rentRepository.findByClientIdAndAutoId(clientId, autoId);
    }
}
