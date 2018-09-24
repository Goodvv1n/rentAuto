package ru.pleshkov.rentAuto.restBean;

/**
 * Структура для добавления события аренды автомобиля
 * @author pleshkov on 24.09.2018.
 */
public class NewRent {

    private String clientName;

    private Integer clientYear;

    private String autoBrand;

    private Integer autoYear;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Integer getClientYear() {
        return clientYear;
    }

    public void setClientYear(Integer clientYear) {
        this.clientYear = clientYear;
    }

    public String getAutoBrand() {
        return autoBrand;
    }

    public void setAutoBrand(String autoBrand) {
        this.autoBrand = autoBrand;
    }

    public Integer getAutoYear() {
        return autoYear;
    }

    public void setAutoYear(Integer autoYear) {
        this.autoYear = autoYear;
    }
}
