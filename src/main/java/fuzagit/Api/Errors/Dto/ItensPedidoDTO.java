package fuzagit.Api.Errors.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItensPedidoDTO {

    private Long produto;
    private Integer quantidade;
}
