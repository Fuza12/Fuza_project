package fuzagit.Domain.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "item_pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item_Pedido {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne @JoinColumn(name = "produto_id")
    private Produto produto;

    @Column(name = "quantidade")
    private Integer quantidade;
}
