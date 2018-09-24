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
import ru.pleshkov.rentAuto.restBean.NewAuto;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author pleshkov on 24.09.2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AutoWebTest {
    @Autowired
    private MockMvc mockMvc;

    private static final String AUTO_BRAND = "testWebAutoBrand";
    private static final int AUTO_YEAR = 1980;

    @After
    public void afterTest() throws Exception {
        mockMvc.perform(delete("/auto").param("brand", AUTO_BRAND));
    }

    @Test
    public void addAutoTest() throws Exception {
        mockMvc.perform(get("/auto").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        NewAuto newAuto = new NewAuto();
        newAuto.setBrand(AUTO_BRAND);
        newAuto.setYear(AUTO_YEAR);
        mockMvc.perform(post("/auto")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.parseObjecctToJSON(newAuto)));

        mockMvc.perform(get("/auto").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].brand", is(AUTO_BRAND)))
                .andExpect(jsonPath("$[0].year", is(AUTO_YEAR)));
    }

    @Test
    public void findAutoTest() throws Exception {
        NewAuto newAuto = new NewAuto();
        newAuto.setBrand(AUTO_BRAND);
        newAuto.setYear(AUTO_YEAR);
        mockMvc.perform(post("/auto")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.parseObjecctToJSON(newAuto)));

        mockMvc.perform(get("/auto").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].brand", is(AUTO_BRAND)))
                .andExpect(jsonPath("$[0].year", is(AUTO_YEAR)));
    }

    @Test
    public void deleteAutoTest() throws Exception {
        NewAuto newAuto = new NewAuto();
        newAuto.setBrand(AUTO_BRAND);
        newAuto.setYear(AUTO_YEAR);
        mockMvc.perform(post("/auto")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.parseObjecctToJSON(newAuto)));

        mockMvc.perform(get("/auto").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].brand", is(AUTO_BRAND)))
                .andExpect(jsonPath("$[0].year", is(AUTO_YEAR)));

        mockMvc.perform(delete("/auto").param("brand", AUTO_BRAND))
                .andExpect(status().isOk());

        mockMvc.perform(get("/auto").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
