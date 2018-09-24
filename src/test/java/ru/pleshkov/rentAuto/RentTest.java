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
import ru.pleshkov.rentAuto.restBean.Rent;
import ru.pleshkov.rentAuto.restBean.NewRent;
import ru.pleshkov.rentAuto.service.AutoService;
import ru.pleshkov.rentAuto.service.ClientService;
import ru.pleshkov.rentAuto.service.RentService;
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
    public void findRentTest() throws SAPIException {
        Client client = createClient();
        Auto auto = createAuto();
        Assert.assertNull(client.getAutoId());
        Assert.assertNull(auto.getClientId());
        rentService.addRentAction(client, auto);

        Rent rent = rentService.findRentActions(client.getName(), auto.getBrand());
        Assert.assertNotNull(rent);
        Assert.assertEquals(client.getName(), rent.getClient().getName());
        Assert.assertEquals(auto.getBrand(), rent.getAuto().getBrand());

        Client clientDB = clientService.findClient(CLIENT_NAME);
        Auto autoDB = autoService.findAutoByName(AUTO_BRAND);
        Assert.assertEquals(clientDB.getAutoId(), auto.getId());
        Assert.assertEquals(autoDB.getClientId(), client.getId());
    }

    @Test
    public void addRentTest() throws SAPIException {
        createRent();
        Client client = clientService.findClient(CLIENT_NAME);
        Auto auto = autoService.findAutoByName(AUTO_BRAND);
        Assert.assertEquals(client.getAutoId(), auto.getId());
        Assert.assertEquals(auto.getClientId(), client.getId());
    }

    @Test (expected = SAPIException.class)
    public void deleteRentWithoutClient() throws SAPIException {
        Client client = createClient();
        Auto auto = createAuto();
        Assert.assertNull(client.getAutoId());
        Assert.assertNull(auto.getClientId());
        rentService.addRentAction(client, auto);

        Rent rent = rentService.findRentActions(client.getName(), auto.getBrand());
        Assert.assertNotNull(rent);
        Assert.assertEquals(client.getName(), rent.getClient().getName());
        Assert.assertEquals(auto.getBrand(), rent.getAuto().getBrand());

        rentService.deleteRentAction(client.getName(), auto.getBrand());

        try {
            rentService.findRentActions(client.getName(), auto.getBrand());
        } catch (SAPIException e){
            Assert.assertEquals(e.getMessage(), "Client Client_Name not found");
            throw e;
        }
    }

    @Test (expected = SAPIException.class)
    public void disabledAutoTest() throws SAPIException {
        try {
            Client client = createClient();
            Auto auto = createAuto();
            auto.setClientId(99999L);

            rentService.addRentAction(client, auto);
        } catch (SAPIException e){
            Assert.assertEquals("The car belongs to another client", e.getMessage());
            throw e;
        }
    }

    private void createRent() throws SAPIException {
        NewRent newRent = new NewRent();
        newRent.setClientName(CLIENT_NAME);
        newRent.setClientYear(CLIENT_YEAR);
        newRent.setAutoBrand(AUTO_BRAND);
        newRent.setAutoYear(AUTO_YEAR);
        rentService.addRentAction(newRent);
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
}
