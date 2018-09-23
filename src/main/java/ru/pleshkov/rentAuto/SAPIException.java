package ru.pleshkov.rentAuto;

/**
 * сключение, генерируемое сорвисом
 * @author pleshkov on 23.09.2018.
 */
public class SAPIException extends Exception {

    public SAPIException(String message) {
        super(message);
    }

    public SAPIException(String message, Throwable cause) {
        super(message, cause);
    }
}
