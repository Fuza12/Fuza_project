package fuzagit.Services.impl;

import fuzagit.Api.Errors.Dto.ItensPedidoDTO;
import fuzagit.Api.Errors.Dto.PedidoDTO;
import fuzagit.Domain.Enums.StatusPedido;
import fuzagit.Domain.Models.Cliente;
import fuzagit.Domain.Models.Item_Pedido;
import fuzagit.Domain.Models.Pedido;
import fuzagit.Domain.Models.Produto;
import fuzagit.Domain.Repositorios.cliente_repositorio;
import fuzagit.Domain.Repositorios.item_pedido_repositorio;
import fuzagit.Domain.Repositorios.pedido_repositorio;
import fuzagit.Domain.Repositorios.produto_repositorio;
import fuzagit.exception.PedidoNaoEncontradoException;
import fuzagit.exception.RegraNegocioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class Pedido_ServiceImplTest {

    @Mock
    private pedido_repositorio mockP_repo;
    @Mock
    private cliente_repositorio mockC_repo;
    @Mock
    private produto_repositorio mockProd_repo;
    @Mock
    private item_pedido_repositorio mockIp_repo;

    private Pedido_ServiceImpl pedidoServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        pedidoServiceImplUnderTest = new Pedido_ServiceImpl(mockP_repo, mockC_repo, mockProd_repo, mockIp_repo);
    }

    @Test
    void testSalvar() {
        // Setup
        final PedidoDTO dto = new PedidoDTO(0L, new BigDecimal("0.00"), List.of(new ItensPedidoDTO(0L, 0)));
        final Pedido expectedResult = new Pedido();
        final Cliente cliente = new Cliente();
        expectedResult.setCliente(cliente);
        expectedResult.setData_pedido(LocalDate.of(2020, 1, 1));
        expectedResult.setTotal(new BigDecimal("0.00"));
        expectedResult.setStatusPedido(StatusPedido.REALIZADO);
        final Item_Pedido itemPedido = new Item_Pedido();
        final Produto produto = new Produto();
        itemPedido.setProduto(produto);
        itemPedido.setQuantidade(0);
        expectedResult.setItemPedidos(List.of(itemPedido));

        // Configure cliente_repositorio.findById(...).
        final Cliente cliente2 = new Cliente();
        cliente2.setId(0L);
        cliente2.setCpf("cpf");
        cliente2.setNome("nome");
        final Pedido pedido = new Pedido();
        pedido.setId(0L);
        cliente2.setPedidos(Set.of(pedido));
        final Optional<Cliente> cliente1 = Optional.of(cliente2);
        when(mockC_repo.findById(0L)).thenReturn(cliente1);

        // Configure produto_repositorio.findById(...).
        final Optional<Produto> produto1 = Optional.of(new Produto(0L, "descricao", new BigDecimal("0.00")));
        when(mockProd_repo.findById(0L)).thenReturn(produto1);

        // Run the test
        final Pedido result = pedidoServiceImplUnderTest.salvar(dto);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);

        // Confirm pedido_repositorio.save(...).
        final Pedido entity = new Pedido();
        final Cliente cliente3 = new Cliente();
        entity.setCliente(cliente3);
        entity.setData_pedido(LocalDate.of(2020, 1, 1));
        entity.setTotal(new BigDecimal("0.00"));
        entity.setStatusPedido(StatusPedido.REALIZADO);
        final Item_Pedido itemPedido1 = new Item_Pedido();
        final Produto produto2 = new Produto();
        itemPedido1.setProduto(produto2);
        itemPedido1.setQuantidade(0);
        entity.setItemPedidos(List.of(itemPedido1));
        verify(mockP_repo).save(entity);

        // Confirm item_pedido_repositorio.saveAll(...).
        final Item_Pedido itemPedido2 = new Item_Pedido();
        final Pedido pedido1 = new Pedido();
        final Cliente cliente4 = new Cliente();
        pedido1.setCliente(cliente4);
        pedido1.setData_pedido(LocalDate.of(2020, 1, 1));
        pedido1.setTotal(new BigDecimal("0.00"));
        pedido1.setStatusPedido(StatusPedido.REALIZADO);
        pedido1.setItemPedidos(List.of(new Item_Pedido()));
        itemPedido2.setPedido(pedido1);
        final Produto produto3 = new Produto();
        itemPedido2.setProduto(produto3);
        itemPedido2.setQuantidade(0);
        final List<Item_Pedido> entities = List.of(itemPedido2);
        verify(mockIp_repo).saveAll(entities);
    }

    @Test
    void testSalvar_cliente_repositorioReturnsAbsent() {
        // Setup
        final PedidoDTO dto = new PedidoDTO(0L, new BigDecimal("0.00"), List.of(new ItensPedidoDTO(0L, 0)));
        when(mockC_repo.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> pedidoServiceImplUnderTest.salvar(dto)).isInstanceOf(RegraNegocioException.class);
    }

    @Test
    void testSalvar_produto_repositorioReturnsAbsent() {
        // Setup
        final PedidoDTO dto = new PedidoDTO(0L, new BigDecimal("0.00"), List.of(new ItensPedidoDTO(0L, 0)));

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

        when(mockProd_repo.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> pedidoServiceImplUnderTest.salvar(dto)).isInstanceOf(RegraNegocioException.class);
    }

    @Test
    void testObterPedidoCompleto() {
        // Setup
        final Pedido pedido = new Pedido();
        final Cliente cliente = new Cliente();
        pedido.setCliente(cliente);
        pedido.setData_pedido(LocalDate.of(2020, 1, 1));
        pedido.setTotal(new BigDecimal("0.00"));
        pedido.setStatusPedido(StatusPedido.REALIZADO);
        final Item_Pedido itemPedido = new Item_Pedido();
        final Produto produto = new Produto();
        itemPedido.setProduto(produto);
        itemPedido.setQuantidade(0);
        pedido.setItemPedidos(List.of(itemPedido));
        final Optional<Pedido> expectedResult = Optional.of(pedido);

        // Configure pedido_repositorio.findByIdFetchItens(...).
        final Pedido pedido2 = new Pedido();
        final Cliente cliente1 = new Cliente();
        pedido2.setCliente(cliente1);
        pedido2.setData_pedido(LocalDate.of(2020, 1, 1));
        pedido2.setTotal(new BigDecimal("0.00"));
        pedido2.setStatusPedido(StatusPedido.REALIZADO);
        final Item_Pedido itemPedido1 = new Item_Pedido();
        final Produto produto1 = new Produto();
        itemPedido1.setProduto(produto1);
        itemPedido1.setQuantidade(0);
        pedido2.setItemPedidos(List.of(itemPedido1));
        final Optional<Pedido> pedido1 = Optional.of(pedido2);
        when(mockP_repo.findByIdFetchItens(0L)).thenReturn(pedido1);

        // Run the test
        final Optional<Pedido> result = pedidoServiceImplUnderTest.ObterPedidoCompleto(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testObterPedidoCompleto_pedido_repositorioReturnsAbsent() {
        // Setup
        when(mockP_repo.findByIdFetchItens(0L)).thenReturn(Optional.empty());

        // Run the test
        final Optional<Pedido> result = pedidoServiceImplUnderTest.ObterPedidoCompleto(0L);

        // Verify the results
        assertThat(result).isEmpty();
    }

    @Test
    void testAtualizarStatus() {
        // Setup
        // Configure pedido_repositorio.findById(...).
        final Pedido pedido1 = new Pedido();
        final Cliente cliente = new Cliente();
        pedido1.setCliente(cliente);
        pedido1.setData_pedido(LocalDate.of(2020, 1, 1));
        pedido1.setTotal(new BigDecimal("0.00"));
        pedido1.setStatusPedido(StatusPedido.REALIZADO);
        final Item_Pedido itemPedido = new Item_Pedido();
        final Produto produto = new Produto();
        itemPedido.setProduto(produto);
        itemPedido.setQuantidade(0);
        pedido1.setItemPedidos(List.of(itemPedido));
        final Optional<Pedido> pedido = Optional.of(pedido1);
        when(mockP_repo.findById(0L)).thenReturn(pedido);

        // Configure pedido_repositorio.save(...).
        final Pedido pedido2 = new Pedido();
        final Cliente cliente1 = new Cliente();
        pedido2.setCliente(cliente1);
        pedido2.setData_pedido(LocalDate.of(2020, 1, 1));
        pedido2.setTotal(new BigDecimal("0.00"));
        pedido2.setStatusPedido(StatusPedido.REALIZADO);
        final Item_Pedido itemPedido1 = new Item_Pedido();
        final Produto produto1 = new Produto();
        itemPedido1.setProduto(produto1);
        itemPedido1.setQuantidade(0);
        pedido2.setItemPedidos(List.of(itemPedido1));
        final Pedido entity = new Pedido();
        final Cliente cliente2 = new Cliente();
        entity.setCliente(cliente2);
        entity.setData_pedido(LocalDate.of(2020, 1, 1));
        entity.setTotal(new BigDecimal("0.00"));
        entity.setStatusPedido(StatusPedido.REALIZADO);
        final Item_Pedido itemPedido2 = new Item_Pedido();
        final Produto produto2 = new Produto();
        itemPedido2.setProduto(produto2);
        itemPedido2.setQuantidade(0);
        entity.setItemPedidos(List.of(itemPedido2));
        when(mockP_repo.save(entity)).thenReturn(pedido2);

        // Run the test
        pedidoServiceImplUnderTest.AtualizarStatus(0L, StatusPedido.REALIZADO);

        // Verify the results
    }

    @Test
    void testAtualizarStatus_pedido_repositorioFindByIdReturnsAbsent() {
        // Setup
        when(mockP_repo.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> pedidoServiceImplUnderTest.AtualizarStatus(0L, StatusPedido.REALIZADO))
                .isInstanceOf(PedidoNaoEncontradoException.class);
    }
}
