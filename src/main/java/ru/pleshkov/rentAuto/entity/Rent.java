package ru.pleshkov.rentAuto.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Сущность События аренды
 * @author pleshkov on 23.09.2018.
 */
@Entity
public class Rent {
    /**
     * Ид события
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Ид клиента
     */
    private Long clientId;

    /**
     * Ид авто
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getAutoId() {
        return autoId;
    }

    public void setAutoId(Long autoId) {
        this.autoId = autoId;
    }

    public Date getStartRentDate() {
        return startRentDate;
    }

    public void setStartRentDate(Date startRentDate) {
        this.startRentDate = startRentDate;
    }

    public Date getEndRentDate() {
        return endRentDate;
    }

    public void setEndRentDate(Date endRentDate) {
        this.endRentDate = endRentDate;
    }
}
