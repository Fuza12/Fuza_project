package fuzagit.Api.Controllers;

import fuzagit.Domain.Models.Cliente;
import fuzagit.Domain.Models.Pedido;
import fuzagit.Domain.Repositorios.cliente_repositorio;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(Cliente_Controller.class)
class Cliente_ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private cliente_repositorio mockC_repo;

    @Test
    void testGetClienteById() throws Exception {
        // Setup
        // Configure cliente_repositorio.findById(...).
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        cliente1.setCpf("cpf");
        cliente1.setNome("nome");
        final Pedido pedido = new Pedido();
        pedido.setId(0L);
        cliente1.setPedidos(Set.of(pedido));
        final Optional<Cliente> cliente = Optional.of(cliente1);
        when(mockC_repo.findById(0L)).thenReturn(cliente);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/clientes/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetClienteById_cliente_repositorioReturnsAbsent() throws Exception {
        // Setup
        when(mockC_repo.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/clientes/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testSave() throws Exception {
        // Setup
        // Configure cliente_repositorio.save(...).
        final Cliente cliente = new Cliente();
        cliente.setId(0L);
        cliente.setCpf("cpf");
        cliente.setNome("nome");
        final Pedido pedido = new Pedido();
        pedido.setId(0L);
        cliente.setPedidos(Set.of(pedido));
        final Cliente entity = new Cliente();
        entity.setId(0L);
        entity.setCpf("cpf");
        entity.setNome("nome");
        final Pedido pedido1 = new Pedido();
        pedido1.setId(0L);
        entity.setPedidos(Set.of(pedido1));
        when(mockC_repo.save(entity)).thenReturn(cliente);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/api/clientes")
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
        // Configure cliente_repositorio.findById(...).
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        cliente1.setCpf("cpf");
        cliente1.setNome("nome");
        final Pedido pedido = new Pedido();
        pedido.setId(0L);
        cliente1.setPedidos(Set.of(pedido));
        final Optional<Cliente> cliente = Optional.of(cliente1);
        when(mockC_repo.findById(0L)).thenReturn(cliente);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/api/clientes/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");

        // Confirm cliente_repositorio.delete(...).
        final Cliente entity = new Cliente();
        entity.setId(0L);
        entity.setCpf("cpf");
        entity.setNome("nome");
        final Pedido pedido1 = new Pedido();
        pedido1.setId(0L);
        entity.setPedidos(Set.of(pedido1));
        verify(mockC_repo).delete(entity);
    }

    @Test
    void testDelete_cliente_repositorioFindByIdReturnsAbsent() throws Exception {
        // Setup
        when(mockC_repo.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/api/clientes/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testUpdate() throws Exception {
        // Setup
        // Configure cliente_repositorio.findById(...).
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        cliente1.setCpf("cpf");
        cliente1.setNome("nome");
        final Pedido pedido = new Pedido();
        pedido.setId(0L);
        cliente1.setPedidos(Set.of(pedido));
        final Optional<Cliente> cliente = Optional.of(cliente1);
        when(mockC_repo.findById(0L)).thenReturn(cliente);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/api/clientes/{id}", 0)
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");

        // Confirm cliente_repositorio.save(...).
        final Cliente entity = new Cliente();
        entity.setId(0L);
        entity.setCpf("cpf");
        entity.setNome("nome");
        final Pedido pedido1 = new Pedido();
        pedido1.setId(0L);
        entity.setPedidos(Set.of(pedido1));
        verify(mockC_repo).save(entity);
    }

    @Test
    void testUpdate_cliente_repositorioFindByIdReturnsAbsent() throws Exception {
        // Setup
        when(mockC_repo.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/api/clientes/{id}", 0)
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
        when(mockC_repo.findAll(any(Example.class))).thenReturn(List.of());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/clientes")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testFind_cliente_repositorioReturnsNoItems() throws Exception {
        // Setup
        when(mockC_repo.findAll(any(Example.class))).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/clientes")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }
}
