package fuzagit.Domain.Models;

import fuzagit.Domain.Enums.StatusPedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id                         @GeneratedValue(strategy = GenerationType.AUTO)
    @Column                     (name = "id")
    private  Long              id;

    @ManyToOne                  @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column                     (name = "data_pedido")
    private  LocalDate         data_pedido;

    @Column                     (name = "total", precision = 20, scale = 2)
    private  BigDecimal        total;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusPedido statusPedido;

    @OneToMany                  (mappedBy = "pedido")
    private  List<Item_Pedido> itemPedidos;
}
