package ru.pleshkov.rentAuto.restBean;

import ru.pleshkov.rentAuto.entity.Auto;
import ru.pleshkov.rentAuto.entity.Client;

/**
 * Событие аренды
 * @author pleshkov on 23.09.2018.
 */
public class Rent {
    /**
     * Бронируемое авто
     */
    private Auto auto;

    /**
     * Клиент
     */
    private Client client;

    public Rent() {
    }

    public Rent(Client client, Auto auto) {
        this.auto = auto;
        this.client = client;
    }

    public Auto getAuto() {
        return auto;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
