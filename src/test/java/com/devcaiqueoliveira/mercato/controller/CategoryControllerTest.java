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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(CategoryController.class)
@WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoryService service;

    @MockitoBean
    private CategoryMapper mapper;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    @DisplayName("Deve retornar 200 ok ao buscar categoria por ID existente")
    void shoulReturnOkStatusOnFindById() throws Exception {
        Long id = 1L;

        Category categoryFound = new Category(id, "Bebidas", true);

        CategoryResponse response = new CategoryResponse(id, "Bebidas", true);

        when(service.findById(id)).thenReturn(categoryFound);
        when(mapper.toCategoryResponse(categoryFound)).thenReturn(response);

        mockMvc.perform(get("/api/categories/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Bebidas"));
    }


    @Test
    @DisplayName("Deve retornar 200 ok e a lista de categorias")
    void shouldReturnOkStatusOnFindAll() throws Exception {

        Category category = new Category(1L, "Bebidas", true);

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
    void shouldReturnCreatedStatusOnCreate() throws Exception {

        CategoryRequest request = new CategoryRequest("Limpeza", true);

        Category categoryMapped = Category.builder()
                .name("Limpeza")
                .active(true)
                .build();

        Category categorySaved = Category.builder()
                .id(1L)
                .name("Limpeza")
                .active(true)
                .build();

        when(mapper.toCategory(any())).thenReturn(categorySaved);
        when(service.create(any(Category.class))).thenReturn(categorySaved);
        when(mapper.toCategoryResponse(any())).thenReturn(new CategoryResponse(1L, "Limpeza", true));

        mockMvc.perform(post("/api/categories")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Limpeza"));
    }

    @Test
    @DisplayName("Deve retornar 200 ok ao atualizar categoria com sucesso")
    void shouldReturnOkStatusOnUpdate() throws Exception {
        Long id = 1L;

        CategoryRequest request = new CategoryRequest("Bebidas Atualizada", true);

        Category categoryToUpdate = Category.builder()
                .name("Bebidas Atualizada")
                .active(true)
                .build();

        Category categoryUpdated = new Category(id, "Bebidas Atualizada", true);

        when(mapper.toCategory(any(CategoryRequest.class))).thenReturn(categoryToUpdate);

        when(service.update(any(Long.class), any(Category.class))).thenReturn(categoryUpdated);

        when(mapper.toCategoryResponse(categoryUpdated)).thenReturn(
                new CategoryResponse(id, "Bebidas Atualizada", true)
        );


        mockMvc.perform(put("/api/categories/{id}", id)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Bebidas Atualizada"));
    }

    @Test
    @DisplayName("Deve retornar 204 no content ao deletar categoria")
    void shouldReturnNoContentStatusOnDelete() throws Exception {
        Long id = 1L;

        doNothing().when(service).deleteById(id);

        mockMvc.perform(delete("/api/categories/{id}", id)
                        .with(csrf()))
                .andExpect(status().isNoContent());

        verify(service, times(1)).deleteById(id);
    }
}
