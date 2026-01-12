package com.devcaiqueoliveira.mercato.controller;

import com.devcaiqueoliveira.mercato.dto.CategoryRequest;
import com.devcaiqueoliveira.mercato.dto.CategoryResponse;
import com.devcaiqueoliveira.mercato.entity.Category;
import com.devcaiqueoliveira.mercato.mapper.CategoryMapper;
import com.devcaiqueoliveira.mercato.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoryService service;

    @MockitoBean
    private CategoryMapper mapper;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    @DisplayName("Deve retornar 200 ok e a lista de categorias")
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void shouldReturnOkStatusOnFindAll() throws Exception {

        Category category = new Category();
        category.setId(1L);
        category.setName("Bebidas");
        category.setActive(true);

        CategoryResponse response = new CategoryResponse(1L, "Bebidas", true);

        when(service.listAll()).thenReturn(List.of(category));

        when(mapper.toCategoryResponse(any(Category.class))).thenReturn(response);

        mockMvc.perform(get("/api/categories")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("Bebidas"));
    }

    @Test
    @DisplayName("Deve retornar 201 Created ao criar categoria com sucesso")
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void shouldReturnCreatedStatusOnCreate() throws Exception {

        CategoryRequest request = new CategoryRequest("Limpeza", true);

        Category categoryMapped = new Category();
        categoryMapped.setName("Limpeza");
        categoryMapped.setActive(true);

        Category categorySaved = new Category();
        categorySaved.setId(1L);
        categorySaved.setName("Limpeza");
        categorySaved.setActive(true);

        when(mapper.toCategory(any())).thenReturn(categorySaved);
        when(service.create(any(Category.class))).thenReturn(categorySaved);
        when(mapper.toCategoryResponse(any())).thenReturn(new CategoryResponse(1L, "Limpeza",  true));

        mockMvc.perform(post("/api/categories")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.id").value(1L))
                        .andExpect(jsonPath("$.name").value("Limpeza"));
    }
}
