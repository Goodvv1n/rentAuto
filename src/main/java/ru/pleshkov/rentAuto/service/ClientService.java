package ru.pleshkov.rentAuto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pleshkov.rentAuto.SAPIException;
import ru.pleshkov.rentAuto.entity.Client;
import ru.pleshkov.rentAuto.repository.ClientRepository;
import ru.pleshkov.rentAuto.restBean.NewClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для обработки действий с клиентами
 * @author pleshkov on 21.09.2018.
 */
@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    /**
     * Добавление клиента с предварительной проверкой его существования
     * @param newClient клиент
     * @throws SAPIException ошибка сервиса
     */
    public Client addClient(NewClient newClient) throws SAPIException {
        if (isClientExist(newClient)){
            throw new SAPIException("Client " + newClient.getName() + " already exists");
        }
        Client client = new Client();
        client.setName(newClient.getName());
        client.setBirthYear(newClient.getBirthYear());
        return clientRepository.save(client);
    }

    void updateClient(Client client){
        clientRepository.save(client);
    }

    /**
     * Получить список клиентов
     * @param name Имя клиента
     * @return результат
     */
    public List<Client> getClientList(String name) {
        return findClients(name);
    }

    /**
     * Получить весь список клиентов
     * @return результат
     */
    public List<Client> getClientList() {
        return getClientList(null);
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
     * Удаление клиента
     * @param name Имя клиента
     * @throws SAPIException ошибка сервиса
     */
    public void deleteClient(String name) throws SAPIException {
        Client client = findClient(name);
        clientRepository.delete(client);
    }

    /**
     * Проверка существования пользователя
     * @param client клиент
     * @return результат
     */
    private boolean isClientExist(NewClient client){
        return !(findClients(client.getName()).size() == 0);
    }

    /**
     * Проверка существования пользователя
     * @param client клиент
     * @return результат
     */
    private boolean isClientExist(Client client){
        return clientRepository.findById(client.getId()).isPresent();
    }

    /**
     * Получить клиента по имени
     * @param name имя клиента
     * @return результат
     */
    public Client findClient(String name) throws SAPIException {
        ArrayList<Client> list = (ArrayList<Client>) findClients(name);
        if (list.size() == 0){
            throw new SAPIException("Client " + name + " not found");
        }
        if (list.size() > 1){
            throw new SAPIException("Found more than one object");
        }
        return list.get(0);
    }

    /**
     * Получение списка клиентов
     * Наименование и год рождения не обязательные параметры. Если параметр не указан - игнорируется.
     * @param name Имя клиента
     * @return список
     */
    private List<Client> findClients(String name) {
        if (name == null || name.isEmpty()) {
            return (List<Client>) clientRepository.findAll();
        }
        return clientRepository.findByName(name);
    }
}
