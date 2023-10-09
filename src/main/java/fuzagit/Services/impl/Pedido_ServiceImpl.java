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
import fuzagit.Services.Pedido_Service;
import fuzagit.exception.PedidoNaoEncontradoException;
import fuzagit.exception.RegraNegocioException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Pedido_ServiceImpl implements Pedido_Service {


    private final pedido_repositorio p_repo;
    private final cliente_repositorio c_repo;
    private final produto_repositorio prod_repo;
    private final item_pedido_repositorio ip_repo;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Long idcliente = dto.getCliente();
        Cliente cliente = c_repo
                .findById(idcliente)
                .orElseThrow(() -> new
                        RegraNegocioException("Código de Cliente inválido"));


        Pedido pedido = new Pedido();
        pedido.setData_pedido(LocalDate.now());
        pedido.setTotal(dto.getTotal());
        pedido.setCliente(cliente);
        pedido.setStatusPedido(StatusPedido.REALIZADO);


        List<Item_Pedido> itens_pedidos = converterItens(pedido, dto.getItens());
        p_repo.save(pedido);
        ip_repo.saveAll(itens_pedidos);
        pedido.setItemPedidos(itens_pedidos);

        return pedido;
    }

    private List<Item_Pedido> converterItens(Pedido pedido, List<ItensPedidoDTO> itensdto) {
        if (itensdto.isEmpty()) {
            throw new RegraNegocioException("Não é possivel realizar um pedido sem itens!!");
        }
        return itensdto
                .stream()
                .map(dto -> {
                    Long idProduto = dto.getProduto();
                    Produto produto = prod_repo
                            .findById(idProduto)
                            .orElseThrow(
                                    () -> new RegraNegocioException(
                                            "Código de Produto inválido: " + idProduto
                                    ));

                    Item_Pedido itemPedido = new Item_Pedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());

    }


    @Override
    public Optional<Pedido> ObterPedidoCompleto(Long id) {
        return p_repo.findByIdFetchItens(id);


    }

    @Override
    @Transactional
    public void AtualizarStatus(Long id, StatusPedido statusPedido) {
        p_repo
                .findById(id)
                .map(pedido -> {
                    pedido.setStatusPedido(statusPedido);
                    return p_repo.save(pedido);
                }).orElseThrow(() -> new PedidoNaoEncontradoException());
    }
}

