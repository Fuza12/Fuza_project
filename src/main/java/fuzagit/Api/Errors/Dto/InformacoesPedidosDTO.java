package fuzagit.Api.Errors.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InformacoesPedidosDTO {
    private Long codigo;
    private String cpf;
    private String nome;
    private String dataPedido;
    private BigDecimal total;
    private String status;
    private List<InformacoesItemPedidosDTO> items;
}

