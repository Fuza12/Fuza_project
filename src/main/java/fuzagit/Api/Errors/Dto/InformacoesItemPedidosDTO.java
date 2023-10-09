package fuzagit.Api.Errors.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InformacoesItemPedidosDTO {
    private String descricaoProduto;
    private BigDecimal precoUnitario;
    private Integer quantidade;

}
