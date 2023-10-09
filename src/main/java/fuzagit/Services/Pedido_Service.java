package fuzagit.Services;

import fuzagit.Api.Errors.Dto.PedidoDTO;
import fuzagit.Domain.Enums.StatusPedido;
import fuzagit.Domain.Models.Pedido;

import java.util.Optional;

public interface Pedido_Service {

    Pedido salvar(PedidoDTO dto);

    Optional<Pedido> ObterPedidoCompleto(Long id);

    void AtualizarStatus(Long id, StatusPedido statusPedido);
}
