package fuzagit.Api.Controllers;

import fuzagit.Domain.Models.Produto;
import fuzagit.Domain.Repositorios.produto_repositorio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(Produto_Controller.class)
class Produto_ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private produto_repositorio mockP_repo;

    @Test
    void testGetProdutoById() throws Exception {
        // Setup
        // Configure produto_repositorio.findById(...).
        final Optional<Produto> produto = Optional.of(new Produto(0L, "descricao", new BigDecimal("0.00")));
        when(mockP_repo.findById(0L)).thenReturn(produto);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/produtos/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetProdutoById_produto_repositorioReturnsAbsent() throws Exception {
        // Setup
        when(mockP_repo.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/produtos/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testSave() throws Exception {
        // Setup
        // Configure produto_repositorio.save(...).
        final Produto produto = new Produto(0L, "descricao", new BigDecimal("0.00"));
        when(mockP_repo.save(new Produto(0L, "descricao", new BigDecimal("0.00")))).thenReturn(produto);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/api/produtos")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testDelete() throws Exception {
        // Setup
        // Configure produto_repositorio.findById(...).
        final Optional<Produto> produto = Optional.of(new Produto(0L, "descricao", new BigDecimal("0.00")));
        when(mockP_repo.findById(0L)).thenReturn(produto);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/api/produtos/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
        verify(mockP_repo).delete(new Produto(0L, "descricao", new BigDecimal("0.00")));
    }

    @Test
    void testDelete_produto_repositorioFindByIdReturnsAbsent() throws Exception {
        // Setup
        when(mockP_repo.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/api/produtos/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testUpdate() throws Exception {
        // Setup
        // Configure produto_repositorio.findById(...).
        final Optional<Produto> produto = Optional.of(new Produto(0L, "descricao", new BigDecimal("0.00")));
        when(mockP_repo.findById(0L)).thenReturn(produto);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/api/produtos/{id}", 0)
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
        verify(mockP_repo).save(new Produto(0L, "descricao", new BigDecimal("0.00")));
    }

    @Test
    void testUpdate_produto_repositorioFindByIdReturnsAbsent() throws Exception {
        // Setup
        when(mockP_repo.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/api/produtos/{id}", 0)
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testFind() throws Exception {
        // Setup
        when(mockP_repo.findAll(any(Example.class))).thenReturn(List.of());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/produtos")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testFind_produto_repositorioReturnsNoItems() throws Exception {
        // Setup
        when(mockP_repo.findAll(any(Example.class))).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/produtos")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }
}
