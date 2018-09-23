package ru.pleshkov.rentAuto.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pleshkov.rentAuto.SAPIException;
import ru.pleshkov.rentAuto.entity.Client;
import ru.pleshkov.rentAuto.repository.ClientRepository;
import ru.pleshkov.rentAuto.restBean.NewClient;

import java.util.List;

/**
 * @author pleshkov on 21.09.2018.
 */
@Service
public class Impl {

    @Autowired
    private ClientRepository clientRepository;

    /**
     * Добавление клиента с предварительной проверкой его существования
     * @param newClient клиент
     * @throws SAPIException ошибка сервиса
     */
    public void addClient(NewClient newClient) throws SAPIException {
        if (isClientExist(newClient)){
            throw new SAPIException("Client already exists");
        }
        Client client = new Client();
        client.setName(newClient.getName());
        client.setBirthYear(newClient.getBirthYear());
        clientRepository.save(client);
    }

    /**
     * Получить список клиентов
     * @param name Имя клиента
     * @param birthYear год рождения
     * @return результат
     */
    public List<Client> getClientList(String name, Integer birthYear) {
        return getClients(name, birthYear);
    }

    /**
     * Удаление клиента
     * @param client клиент
     * @throws SAPIException ошибка сервиса
     */
    public void deleteClient(Client client) throws SAPIException {
        if (!isClientExist(client)){
            throw new SAPIException("Client not found");
        }
        clientRepository.delete(client);
    }

    /**
     * Проверка существования пользователя
     * @param client клиент
     * @return результат
     */
    private boolean isClientExist(NewClient client){
        return !(getClients(client.getName(), client.getBirthYear()).size() == 0);
    }

    /**
     * Проверка существования пользователя
     * @param client клиент
     * @return результат
     */
    private boolean isClientExist(Client client){
        clientRepository.findById(client.getId());
        return true;
    }

    /**
     * Получение списка клиентов
     * Наименование и год рождения не обязательные параметры. Если параметр не указан - игнорируется.
     * @param name Имя клиента
     * @param year Год рождения клиента
     * @return список
     */
    private List<Client> getClients(String name, Integer year){
        if ((name == null || name.isEmpty()) && year == null){
            return (List<Client>) clientRepository.findAll();
        }
        if ((name == null || name.isEmpty()) && year != null){
            return clientRepository.findByBirthYear(year);
        }
        if (!name.isEmpty() && year == null){
            return clientRepository.findByName(name);
        }
        return clientRepository.findByNameAndBirthYear(name, year);
    }
}
