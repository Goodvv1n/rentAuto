package ru.pleshkov.rentAuto;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.pleshkov.rentAuto.entity.Client;
import ru.pleshkov.rentAuto.impl.ClientService;
import ru.pleshkov.rentAuto.restBean.NewClient;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientTests {

	@Autowired
	private ClientService service;

	private static final String clientName = "TestClientName";
	private static final Integer clientYear = 1980;

	@After
	public void afterTest() throws SAPIException {
		ArrayList<Client> clients = (ArrayList<Client>) service.getClientList(clientName, clientYear);
		if (clients.size() != 0){
			service.deleteClient(clients.get(0));
		}
	}

	@Test
	public void getClientListTest(){
		ArrayList<Client> clientList = (ArrayList<Client>) service.getClientList(null, null);
		Assert.assertNotNull(clientList);
		Assert.assertNotEquals(0, clientList.size());
	}

	@Test
	public void addClient() throws SAPIException {
		int startSize;
		ArrayList<Client> clients = (ArrayList<Client>) service.getClientList(null, null);
		Assert.assertNotNull(clients);
		startSize = clients.size();

		NewClient newClient = new NewClient();
		newClient.setName(clientName);
		newClient.setBirthYear(clientYear);

		service.addClient(newClient);

		clients = (ArrayList<Client>) service.getClientList(null, null);
		Assert.assertNotNull(clients);
		Assert.assertEquals(startSize + 1, clients.size());
	}
}