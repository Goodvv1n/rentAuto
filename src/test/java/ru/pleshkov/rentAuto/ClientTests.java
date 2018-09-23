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

	private static final String CLIENT_NAME = "TestClientName";
	private static final Integer CLIENT_YEAR = 1980;

	@After
	public void afterTest() throws SAPIException {
		ArrayList<Client> clients = (ArrayList<Client>) service.getClientList(CLIENT_NAME);
		if (clients.size() != 0){
			service.deleteClient(clients.get(0));
		}
	}

	@Test
	public void addClient() throws SAPIException {
		int startSize;
		ArrayList<Client> clients = (ArrayList<Client>) service.getClientList(null);
		Assert.assertNotNull(clients);
		startSize = clients.size();
		createClient();

		clients = (ArrayList<Client>) service.getClientList(null);
		Assert.assertNotNull(clients);
		Assert.assertEquals(startSize + 1, clients.size());
	}

	@Test(expected = SAPIException.class)
	public void addSecondItemClientTest() throws SAPIException {
		try {
			int startSize;
			ArrayList<Client> clients = (ArrayList<Client>) service.getClientList(null);
			Assert.assertNotNull(clients);
			startSize = clients.size();
			createClient();

			clients = (ArrayList<Client>) service.getClientList(null);
			Assert.assertNotNull(clients);
			Assert.assertEquals(startSize + 1, clients.size());

			createClient();
		} catch (SAPIException e){
			Assert.assertEquals("Client " + CLIENT_NAME + " already exists", e.getMessage());
			throw e;
		}
	}

	@Test
	public void deleteClient() throws SAPIException {
		int startSize;
		createClient();
		ArrayList<Client> list = (ArrayList<Client>) service.getClientList();
		Assert.assertNotNull(list);
		startSize = list.size();

		ArrayList<Client> clientList = (ArrayList<Client>) service.getClientList(CLIENT_NAME);
		Assert.assertNotNull(clientList);
		Assert.assertEquals(clientList.size(), 1);
		Client client = clientList.get(0);

		service.deleteClient(client);

		list = (ArrayList<Client>) service.getClientList();
		Assert.assertNotNull(clientList);
		Assert.assertEquals(startSize - 1, list.size());
	}

	@Test
	public void findClient() throws SAPIException {
		createClient();
		ArrayList<Client> clients = (ArrayList<Client>) service.getClientList(null);
		Assert.assertNotNull(clients);
		Assert.assertEquals(1, clients.size());
		Assert.assertEquals(clients.get(0).getName(), CLIENT_NAME);
		Assert.assertEquals(clients.get(0).getBirthYear(), CLIENT_YEAR);
	}

	private void createClient() throws SAPIException {
		NewClient newClient = new NewClient();
		newClient.setName(CLIENT_NAME);
		newClient.setBirthYear(CLIENT_YEAR);
		service.addClient(newClient);
	}
}