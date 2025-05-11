package com.kenyajug.getfruity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenyajug.getfruity.database.entities.Fruit;
import com.kenyajug.getfruity.database.repository.FruitsRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@Slf4j
@AutoConfigureMockMvc
@SpringBootTest
public class GetFruityHomePageTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private FruitsRepository fruitsRepository;
    @Test
    public void shouldLoadFruitsHomepageTest() throws Exception {
        mockMvc.perform(get("/")
                        .with(user("jane-doe").roles("ADMIN"))
                        .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("welcome"))
                .andExpect(model().attributeExists("fruits"));
    }
    @Test
    public void shouldLoadFruitsFormTest() throws Exception {
        var emptyFruit = new Fruit();
        mockMvc.perform(get("/fruits/add")
                        .with(user("jane-doe").roles("ADMIN"))
                        .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("add-fruit"))
                .andExpect(model().attributeExists("fruit"))
                .andExpect(model().attribute("fruit",emptyFruit));
    }
    @Test
    public void shouldSaveFruitTest() throws Exception {
        var actualFruit = new Fruit(
                1L,
                "Apple",
                "Crunchy",
                "🍎",
                BigDecimal.valueOf(12),
                BigDecimal.valueOf(100));
        when(fruitsRepository.save(any(Fruit.class))).thenReturn(actualFruit);
        var payload = new ObjectMapper().writeValueAsString(actualFruit);
        log.info("Payload for saving fruit\n{}", payload);
        mockMvc.perform(post("/fruits/add")
                        .with(user("jane-doe").roles("ADMIN"))
                        .with(csrf())
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(payload))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}
