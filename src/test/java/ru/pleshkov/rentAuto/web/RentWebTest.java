package ru.pleshkov.rentAuto.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.pleshkov.rentAuto.SAPIException;
import ru.pleshkov.rentAuto.TestUtils;
import ru.pleshkov.rentAuto.restBean.NewClient;
import ru.pleshkov.rentAuto.restBean.NewRent;
import ru.pleshkov.rentAuto.restBean.Rent;

import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author pleshkov on 24.09.2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RentWebTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String AUTO_BRAND = "testWebAutoBrand";
    private static final int AUTO_YEAR = 1980;
    private static final String CLIENT_NAME = "testWebClientName";
    private static final int CLIENT_YEAR = 1980;

    @After
    public void afterTest() throws Exception {
        mockMvc.perform(delete("/rent")
                .param("clientName", CLIENT_NAME)
                .param("autoBrand", AUTO_BRAND));
        mockMvc.perform(delete("/auto").param("brand", AUTO_BRAND));
        mockMvc.perform(delete("/client").param("name", CLIENT_NAME));
    }

    @Test
    public void addRentTest() throws Exception {
        mockMvc.perform(get("/rent")
                .param("clientName", CLIENT_NAME)
                .param("autoBrand", AUTO_BRAND))
                    .andExpect(status().is5xxServerError());

        NewRent rent = createRent();

        mockMvc.perform(post("/rent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.parseObjecctToJSON(rent)));

        MvcResult mvcResult = mockMvc.perform(get("/rent")
                .param("clientName", CLIENT_NAME)
                .param("autoBrand", AUTO_BRAND))
                    .andExpect(status().isOk()).andReturn();

        checkRentResult(mvcResult);
    }

    @Test
    public void addRentClientExistTest() throws Exception {
        NewClient client = new NewClient();
        client.setName(CLIENT_NAME);
        client.setBirthYear(CLIENT_YEAR);
        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.parseObjecctToJSON(client)));

        mockMvc.perform(get("/rent")
                .param("clientName", CLIENT_NAME)
                .param("autoBrand", AUTO_BRAND))
                .andExpect(status().is5xxServerError());

        NewRent rent = createRent();

        MvcResult mvcResult = mockMvc.perform(post("/rent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.parseObjecctToJSON(rent)))
                .andExpect(status().is5xxServerError())
                .andReturn();

        checkResponseError(mvcResult, "Client " + CLIENT_NAME + " already exists");
    }

    @Test
    public void findRentTest() throws Exception {
        NewRent rent = createRent();

        mockMvc.perform(post("/rent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.parseObjecctToJSON(rent)));

        MvcResult mvcResult = mockMvc.perform(get("/rent")
                .param("clientName", CLIENT_NAME)
                .param("autoBrand", AUTO_BRAND))
                .andExpect(status().isOk()).andReturn();

        checkRentResult(mvcResult);
    }

    @Test
    public void deleteTest() throws Exception {
        NewRent rent = createRent();

        mockMvc.perform(post("/rent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.parseObjecctToJSON(rent)));

        MvcResult mvcResult = mockMvc.perform(get("/rent")
                .param("clientName", CLIENT_NAME)
                .param("autoBrand", AUTO_BRAND))
                .andExpect(status().isOk()).andReturn();

        checkRentResult(mvcResult);

        mockMvc.perform(delete("/rent")
                .param("clientName", CLIENT_NAME)
                .param("autoBrand", AUTO_BRAND))
                .andExpect(status().isOk());

        mvcResult = mockMvc.perform(get("/rent")
                .param("clientName", CLIENT_NAME)
                .param("autoBrand", AUTO_BRAND))
                .andExpect(status().is5xxServerError())
                .andReturn();

        checkResponseError(mvcResult, "Client " + CLIENT_NAME + " not found");
    }

    @Test
    public void deleteRentClientNotExist() throws Exception {
        NewRent rent = createRent();

        mockMvc.perform(post("/rent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.parseObjecctToJSON(rent)));

        MvcResult mvcResult = mockMvc.perform(get("/rent")
                .param("clientName", CLIENT_NAME)
                .param("autoBrand", AUTO_BRAND))
                .andExpect(status().isOk()).andReturn();

        checkRentResult(mvcResult);

        mockMvc.perform(delete("/client").param("name", CLIENT_NAME))
                .andExpect(status().isOk());

        mvcResult = mockMvc.perform(delete("/rent")
                .param("clientName", CLIENT_NAME)
                .param("autoBrand", AUTO_BRAND))
                .andExpect(status().is5xxServerError())
                .andReturn();
        checkResponseError(mvcResult, "Client " + CLIENT_NAME + " not found");
    }

    @Test
    public void deleteRentAutoNotExist() throws Exception {
        NewRent rent = createRent();

        mockMvc.perform(post("/rent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.parseObjecctToJSON(rent)));

        MvcResult mvcResult = mockMvc.perform(get("/rent")
                .param("clientName", CLIENT_NAME)
                .param("autoBrand", AUTO_BRAND))
                .andExpect(status().isOk()).andReturn();

        checkRentResult(mvcResult);

        mockMvc.perform(delete("/auto").param("brand", AUTO_BRAND))
                .andExpect(status().isOk());

        mvcResult = mockMvc.perform(delete("/rent")
                .param("clientName", CLIENT_NAME)
                .param("autoBrand", AUTO_BRAND))
                .andExpect(status().is5xxServerError())
                .andReturn();
        checkResponseError(mvcResult, "Auto " + AUTO_BRAND + " not found");
    }

    @Test
    public void deleteRentAutoAnotherClient() throws Exception {
        NewRent rent = createRent();

        mockMvc.perform(post("/rent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.parseObjecctToJSON(rent)));

        MvcResult mvcResult = mockMvc.perform(get("/rent")
                .param("clientName", CLIENT_NAME)
                .param("autoBrand", AUTO_BRAND))
                .andExpect(status().isOk()).andReturn();

        checkRentResult(mvcResult);

        //Создаем еще одну пару клиент - авто
        NewRent secondRent = createRent();
        secondRent.setAutoBrand(AUTO_BRAND + "_2");
        secondRent.setClientName(CLIENT_NAME + "_2");
        mockMvc.perform(post("/rent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.parseObjecctToJSON(secondRent)));

        //Пытаемся удалить авто другого клиента
        mvcResult = mockMvc.perform(delete("/rent")
                .param("clientName", CLIENT_NAME)
                .param("autoBrand", AUTO_BRAND + "_2"))
                .andExpect(status().is5xxServerError())
                .andReturn();
        checkResponseError(mvcResult, "The car belongs to another client");
    }

    private NewRent createRent() throws SAPIException {
        NewRent newRent = new NewRent();
        newRent.setClientName(CLIENT_NAME);
        newRent.setClientYear(CLIENT_YEAR);
        newRent.setAutoBrand(AUTO_BRAND);
        newRent.setAutoYear(AUTO_YEAR);
        return newRent;
    }

    private void checkRentResult(MvcResult mvcResult) throws IOException {
        mvcResult.getHandler();
        String json = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Rent rent = objectMapper.readValue(json, Rent.class);
        Assert.assertEquals(rent.getAuto().getClientId(), rent.getClient().getId());
        Assert.assertEquals(rent.getAuto().getId(), rent.getClient().getAutoId());
    }

    private void checkResponseError(MvcResult mvcResult, String errMsg) throws IOException {
        mvcResult.getHandler();
        String text = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals(errMsg, text);
    }
}
