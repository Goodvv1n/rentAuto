package ru.pleshkov.rentAuto.restBean;

/**
 * Описание структуры для добавления нового авто
 * @author pleshkov on 20.09.2018.
 */
public class NewAuto {

    /**
     * Макрва авто
     */
    private String brand;

    /**
     * Год выпуска
     */
    private Integer year;

    /**
     * Ид владельца
     */
    private Long clientId;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
