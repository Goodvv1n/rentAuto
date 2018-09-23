package ru.pleshkov.rentAuto.restBean;

/**
 * Описание структуры для добавления нового клиента
 * @author pleshkov on 20.09.2018.
 */
public class NewClient {

    private String name;

    private Integer birthYear;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }
}
