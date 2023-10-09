package fuzagit.Api.Controllers;

import fuzagit.Api.Errors.Dto.ItensPedidoDTO;
import fuzagit.Api.Errors.Dto.PedidoDTO;
import fuzagit.Domain.Enums.StatusPedido;
import fuzagit.Domain.Models.Cliente;
import fuzagit.Domain.Models.Item_Pedido;
import fuzagit.Domain.Models.Pedido;
import fuzagit.Domain.Models.Produto;
import fuzagit.Services.Pedido_Service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(Pedido_Controller.class)
class Pedido_ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Pedido_Service mockP_serv;

    @Test
    void testSave() throws Exception {
        // Setup
        // Configure Pedido_Service.salvar(...).
        final Pedido pedido = new Pedido();
        pedido.setId(0L);
        final Cliente cliente = new Cliente();
        cliente.setCpf("cpf");
        cliente.setNome("nome");
        pedido.setCliente(cliente);
        pedido.setData_pedido(LocalDate.of(2020, 1, 1));
        pedido.setTotal(new BigDecimal("0.00"));
        pedido.setStatusPedido(StatusPedido.REALIZADO);
        final Item_Pedido itemPedido = new Item_Pedido();
        final Produto produto = new Produto();
        produto.setDescricao("descricao");
        produto.setPreco_unitario(new BigDecimal("0.00"));
        itemPedido.setProduto(produto);
        pedido.setItemPedidos(List.of(itemPedido));
        when(mockP_serv.salvar(
                new PedidoDTO(0L, new BigDecimal("0.00"), List.of(new ItensPedidoDTO(0L, 0))))).thenReturn(pedido);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/api/pedidos")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetById() throws Exception {
        // Setup
        // Configure Pedido_Service.ObterPedidoCompleto(...).
        final Pedido pedido1 = new Pedido();
        pedido1.setId(0L);
        final Cliente cliente = new Cliente();
        cliente.setCpf("cpf");
        cliente.setNome("nome");
        pedido1.setCliente(cliente);
        pedido1.setData_pedido(LocalDate.of(2020, 1, 1));
        pedido1.setTotal(new BigDecimal("0.00"));
        pedido1.setStatusPedido(StatusPedido.REALIZADO);
        final Item_Pedido itemPedido = new Item_Pedido();
        final Produto produto = new Produto();
        produto.setDescricao("descricao");
        produto.setPreco_unitario(new BigDecimal("0.00"));
        itemPedido.setProduto(produto);
        pedido1.setItemPedidos(List.of(itemPedido));
        final Optional<Pedido> pedido = Optional.of(pedido1);
        when(mockP_serv.ObterPedidoCompleto(0L)).thenReturn(pedido);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/pedidos/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetById_Pedido_ServiceReturnsAbsent() throws Exception {
        // Setup
        when(mockP_serv.ObterPedidoCompleto(0L)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/pedidos/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testUpdateStaus() throws Exception {
        // Setup
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(patch("/api/pedidos/{id}", 0)
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
        verify(mockP_serv).AtualizarStatus(0L, StatusPedido.REALIZADO);
    }
}
