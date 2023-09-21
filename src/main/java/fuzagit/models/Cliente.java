package fuzagit.models;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "cliente")
@Data
public class Cliente {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long    id;

    @Column(name = "nome", length = 100)
    private String  nome;

    @OneToMany(mappedBy = "cliente")
    private Set<Pedido> pedidos;


}

