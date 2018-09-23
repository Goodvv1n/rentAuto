package ru.pleshkov.rentAuto.restBean;

import ru.pleshkov.rentAuto.entity.Auto;
import ru.pleshkov.rentAuto.entity.Client;
import ru.pleshkov.rentAuto.entity.Rent;

import java.util.Date;

/**
 * Структура для создания нового события аренды
 * @author pleshkov on 23.09.2018.
 */
public class NewRentAction {

    /**
     * Ид клиента
     */
    private Long clientId;

    /**
     * Ид автомобиля
     */
    private Long autoId;

    /**
     * Дата начала аренды
     */
    private Date startRentDate;

    /**
     * Дата завершения аренды
     */
    private Date endRentDate;

    public NewRentAction(Client client, Auto auto) {
        clientId = client.getId();
        autoId = auto.getId();
        startRentDate = new Date();
    }

    public Rent getRent(){
        Rent rent = new Rent();
        rent.setAutoId(autoId);
        rent.setClientId(clientId);
//        rent.setStartRentDate(startRentDate);
//        rent.setEndRentDate(endRentDate);
        return rent;
    }

}
