package ru.pleshkov.rentAuto;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.pleshkov.rentAuto.entity.Auto;
import ru.pleshkov.rentAuto.entity.Client;
import ru.pleshkov.rentAuto.impl.AutoService;
import ru.pleshkov.rentAuto.impl.ClientService;
import ru.pleshkov.rentAuto.impl.RentService;
import ru.pleshkov.rentAuto.restBean.NewAuto;
import ru.pleshkov.rentAuto.restBean.NewClient;

import java.util.ArrayList;

/**
 * @author pleshkov on 23.09.2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RentTest {
    @Autowired
    private AutoService autoService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private RentService rentService;

    private static final String CLIENT_NAME = "Client_Name";
    private static final Integer CLIENT_YEAR = 1960;

    private static final String AUTO_BRAND = "AUTO_BRAND";
    private static final Integer AUTO_YEAR = 1980;

    @After
    public void afterTest() throws SAPIException {
        deleteAuto();
        deleteClient();
    }

    @Test
    public void findRentAction() throws SAPIException {
        Client client = createClient();
        Auto auto = createAuto();
        Assert.assertNull(client.getAutoId());
        Assert.assertNull(auto.getClientId());

        rentService.addRentAction(client, auto);

        Client clientDB = clientService.findClient(CLIENT_NAME);
        Auto autoDB = autoService.findAutoByName(AUTO_BRAND);

        Assert.assertEquals(clientDB.getAutoId(), auto.getId());
        Assert.assertEquals(autoDB.getClientId(), client.getId());


    }

    private Auto createAuto() throws SAPIException {
        NewAuto newAuto = new NewAuto();
        newAuto.setBrand(AUTO_BRAND);
        newAuto.setYear(AUTO_YEAR);
        return autoService.addAuto(newAuto);
    }

    private Client createClient() throws SAPIException {
        NewClient newClient = new NewClient();
        newClient.setName(CLIENT_NAME);
        newClient.setBirthYear(CLIENT_YEAR);
        return clientService.addClient(newClient);
    }

    private void deleteClient() throws SAPIException {
        ArrayList<Client> list = (ArrayList<Client>) clientService.getClientList(CLIENT_NAME);
        if (list.size() != 0){
            clientService.deleteClient(list.get(0));
        }
    }

    private void deleteAuto() throws SAPIException {
        ArrayList<Auto> list = (ArrayList<Auto>) autoService.getAutoList(AUTO_BRAND);
        if (list.size() != 0){
            autoService.deleteAuto(list.get(0));
        }
    }
//
//    private void deleteRentAction() throws SAPIException {
//        Client client = createClient();
//        Auto auto = createAuto();
//        ArrayList<Rent> list = (ArrayList<Rent>) rentService.findRentActions(client.getId(), auto.getId());
//        if (list.size() != 0){
//            rentService.deleteRentAction(list.get(0));
//        }
//    }
}
