package ru.pleshkov.rentAuto;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.pleshkov.rentAuto.entity.Auto;
import ru.pleshkov.rentAuto.impl.AutoService;
import ru.pleshkov.rentAuto.restBean.NewAuto;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AutoTests {

	@Autowired
	private AutoService service;

	private static final String BRAND = "TestAutoBrand";
	private static final Integer YEAR = 1980;
	private static final Long OWNER = 123L;

	@After
	public void afterTest() throws SAPIException {
		ArrayList<Auto> clients = (ArrayList<Auto>) service.getAutoList(BRAND);
		if (clients.size() != 0){
			service.deleteAuto(clients.get(0));
		}
	}

	@Test
	public void findAutoTest() throws SAPIException {
		createTestAuto();
		ArrayList<Auto> autoListList = (ArrayList<Auto>) service.getAutoList(BRAND);
		Assert.assertNotNull(autoListList);
		Assert.assertEquals(autoListList.size(), 1);
		Auto auto = autoListList.get(0);
		Assert.assertEquals(auto.getBrand(), BRAND);
		Assert.assertEquals(auto.getYear(), YEAR);
		Assert.assertEquals(auto.getClientId(), OWNER);
	}

	@Test
	public void addAuto() throws SAPIException {
		int startSize;
		ArrayList<Auto> autoList = (ArrayList<Auto>) service.getAutoList();
		Assert.assertNotNull(autoList);
		startSize = autoList.size();
		createTestAuto();

		autoList = (ArrayList<Auto>) service.getAutoList();
		Assert.assertNotNull(autoList);
		Assert.assertEquals(startSize + 1, autoList.size());
	}

	@Test(expected = SAPIException.class)
	public void addSecondAutoTest() throws SAPIException {
		try {
			int startSize;
			ArrayList<Auto> autoList = (ArrayList<Auto>) service.getAutoList();
			Assert.assertNotNull(autoList);
			startSize = autoList.size();
			createTestAuto();

			autoList = (ArrayList<Auto>) service.getAutoList();
			Assert.assertNotNull(autoList);
			Assert.assertEquals(startSize + 1, autoList.size());

			createTestAuto();
		} catch (SAPIException e){
			Assert.assertEquals("Auto already exists", e.getMessage());
			throw e;
		}
	}

	@Test
	public void deleteAuto() throws SAPIException {
		int startSize;
		createTestAuto();
		ArrayList<Auto> autoList = (ArrayList<Auto>) service.getAutoList();
		Assert.assertNotNull(autoList);
		startSize = autoList.size();

		ArrayList<Auto> findAutoList = (ArrayList<Auto>) service.getAutoList(BRAND);
		Assert.assertNotNull(findAutoList);
		Assert.assertEquals(findAutoList.size(), 1);
		Auto auto = findAutoList.get(0);

		service.deleteAuto(auto);

		autoList = (ArrayList<Auto>) service.getAutoList();
		Assert.assertNotNull(findAutoList);
		Assert.assertEquals(startSize - 1, autoList.size());
	}

	private void createTestAuto() throws SAPIException {
		NewAuto newAuto = new NewAuto();
		newAuto.setBrand(BRAND);
		newAuto.setYear(YEAR);
		newAuto.setClientId(OWNER);
		service.addAuto(newAuto);
	}
}