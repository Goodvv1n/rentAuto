package ru.pleshkov.rentAuto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pleshkov.rentAuto.SAPIException;
import ru.pleshkov.rentAuto.entity.Auto;
import ru.pleshkov.rentAuto.entity.Client;
import ru.pleshkov.rentAuto.restBean.Rent;
import ru.pleshkov.rentAuto.restBean.NewAuto;
import ru.pleshkov.rentAuto.restBean.NewClient;
import ru.pleshkov.rentAuto.restBean.NewRent;

/**
 * Сервис для заполнения данных об событиях аренды
 * @author pleshkov on 23.09.2018.
 */
@Service
public class RentService {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AutoService autoService;

    public Rent findRentActions(String name, String autoBrand) throws SAPIException {
        Client client = clientService.findClient(name);
        Auto auto = autoService.findAutoByName(autoBrand);
        if (!auto.getClientId().equals(client.getId())){
            throw new SAPIException("The car belongs to another client");
        }
        return new Rent(client, auto);
    }

    /**
     * Удаление события аренды
     */
    public void deleteRentAction(String name, String autoBrand) throws SAPIException {
        Client client = clientService.findClient(name);
        Auto auto = autoService.findAutoByName(autoBrand);
        if (auto.getClientId() == null){
            throw new SAPIException("The car is not occupied by any client");
        }
        if (!auto.getClientId().equals(client.getId())){
            throw new SAPIException("The car belongs to another client");
        }
        auto.setClientId(null);
        autoService.updateAuto(auto);
        clientService.deleteClient(client);
    }

    /**
     * Бронирование автомобиля
     * @param newRent клиент и авто для аренды
     * @throws SAPIException ошибка сервиса
     */
    public void addRentAction(NewRent newRent) throws SAPIException {
        Client client = createClient(newRent);
        Auto auto = createAuto(newRent);
        addRentAction(client, auto);
    }

    /**
     * Бронирование автомобиля
     * @param client клиент
     * @param auto авто
     * @throws SAPIException ошибка сервиса
     */
    public void addRentAction(Client client, Auto auto) throws SAPIException {
        if (auto.getClientId() != null
                && !auto.getClientId().equals(client.getId())){
            throw new SAPIException("The car belongs to another client");
        }
        client.setAutoId(auto.getId());
        auto.setClientId(client.getId());
        autoService.updateAuto(auto);
        clientService.updateClient(client);
    }

    /**
     * Бронирование автомобиля
     * @param clientName Имя клиента
     * @param autoBrand Марка авто
     * @throws SAPIException ошибка сервиса
     */
     void addRentAction(String clientName, String autoBrand) throws SAPIException {
        Client client = clientService.findClient(clientName);
        Auto auto = autoService.findAutoByName(autoBrand);
        if (auto.getClientId() != null
                && !auto.getClientId().equals(client.getId())){
            throw new SAPIException("The car belongs to another client");
        }
        client.setAutoId(auto.getId());
        auto.setClientId(client.getId());
        autoService.updateAuto(auto);
        clientService.updateClient(client);
    }

    /**
     * Создание клиента по входящим данным
     * @param rent входящие данные (о клиенте и авто)
     * @return Клиент
     * @throws SAPIException ошибка сервиса
     */
    private Client createClient(NewRent rent) throws SAPIException {
        NewClient newClient = new NewClient();
        newClient.setName(rent.getClientName());
        newClient.setBirthYear(rent.getClientYear());
        return clientService.addClient(newClient);
    }

    /**
     * Создание авто (если оно отсутствует) или получение сущности существующего авто
     * @param rent входящие данные (о клиенте и авто)
     * @return Авто
     * @throws SAPIException ошибка сервиса
     */
    private Auto createAuto(NewRent rent) throws SAPIException {
        NewAuto newAuto = new NewAuto();
        newAuto.setBrand(rent.getAutoBrand());
        newAuto.setYear(rent.getAutoYear());
        return autoService.addAuto(newAuto);
    }
}
