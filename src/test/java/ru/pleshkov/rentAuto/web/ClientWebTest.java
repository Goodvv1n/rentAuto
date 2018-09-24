package ru.pleshkov.rentAuto.web;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.pleshkov.rentAuto.TestUtils;
import ru.pleshkov.rentAuto.restBean.NewClient;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author pleshkov on 24.09.2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ClientWebTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String CLIENT_NAME = "testWebClientName";
    private static final int CLIENT_YEAR = 1980;

    @After
    public void afterTest() throws Exception {
        mockMvc.perform(delete("/client").param("name", CLIENT_NAME));
    }

    @Test
    public void addClientTest() throws Exception {
        mockMvc.perform(get("/client").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        NewClient client = new NewClient();
        client.setName(CLIENT_NAME);
        client.setBirthYear(CLIENT_YEAR);
        mockMvc.perform(post("/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.parseObjecctToJSON(client)));

        mockMvc.perform(get("/client").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(CLIENT_NAME)))
                .andExpect(jsonPath("$[0].birthYear", is(CLIENT_YEAR)));
    }

    @Test
    public void findClientTest() throws Exception {
        NewClient client = new NewClient();
        client.setName(CLIENT_NAME);
        client.setBirthYear(CLIENT_YEAR);
        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.parseObjecctToJSON(client)));

        mockMvc.perform(get("/client").param("name", CLIENT_NAME).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(CLIENT_NAME)))
                .andExpect(jsonPath("$[0].birthYear", is(CLIENT_YEAR)));
    }

    @Test
    public void deleteClientTest() throws Exception {
        NewClient client = new NewClient();
        client.setName(CLIENT_NAME);
        client.setBirthYear(CLIENT_YEAR);
        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.parseObjecctToJSON(client)));

        mockMvc.perform(get("/client").param("name", CLIENT_NAME).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(CLIENT_NAME)))
                .andExpect(jsonPath("$[0].birthYear", is(CLIENT_YEAR)));

        mockMvc.perform(delete("/client").param("name", CLIENT_NAME))
                .andExpect(status().isOk());

        mockMvc.perform(get("/client").param("name", CLIENT_NAME).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
