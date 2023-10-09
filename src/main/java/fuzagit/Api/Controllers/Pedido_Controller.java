package fuzagit.Api.Controllers;

import fuzagit.Api.Errors.Dto.AtualizacaoStatusPedidoDTO;
import fuzagit.Api.Errors.Dto.InformacoesItemPedidosDTO;
import fuzagit.Api.Errors.Dto.InformacoesPedidosDTO;
import fuzagit.Api.Errors.Dto.PedidoDTO;
import fuzagit.Domain.Enums.StatusPedido;
import fuzagit.Domain.Models.Item_Pedido;
import fuzagit.Domain.Models.Pedido;
import fuzagit.Services.Pedido_Service;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
public class Pedido_Controller {

    @Autowired
    private Pedido_Service p_serv;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long save(@RequestBody @Valid PedidoDTO dto){
           Pedido pedido = p_serv.salvar( dto );
           return pedido.getId();
    }

    @GetMapping("{id}")
    public InformacoesPedidosDTO GetById(@PathVariable Long id){
        return p_serv
                .ObterPedidoCompleto(id)
                .map(p -> converter(p))
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido nao encontrado."));
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void UpdateStaus(@PathVariable Long id,
                            @RequestBody @Valid AtualizacaoStatusPedidoDTO dto){
        String novoStatus = dto.getNovoStatus();

        p_serv.AtualizarStatus(id, StatusPedido.valueOf(novoStatus));

    }


    private InformacoesPedidosDTO converter(Pedido pedido){
          return   InformacoesPedidosDTO
                    .builder()
                    .codigo(pedido.getId())
                    .dataPedido(pedido.getData_pedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .cpf(pedido.getCliente().getCpf())
                    .nome(pedido.getCliente().getNome())
                    .total(pedido.getTotal())
                    .status(pedido.getStatusPedido().name())
                    .items(converter(pedido.getItemPedidos()))
                    .build();
    }

    private List<InformacoesItemPedidosDTO> converter(List<Item_Pedido> itens){
        if (CollectionUtils.isEmpty(itens)){
            return Collections.emptyList();
        }

        return itens
                .stream()
                .map(
                    item -> InformacoesItemPedidosDTO
                            .builder().descricaoProduto(item.getProduto().getDescricao())
                            .precoUnitario(item.getProduto().getPreco_unitario())
                            .build()
                ).collect(Collectors.toList());
    }

}
