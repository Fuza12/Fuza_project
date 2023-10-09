package fuzagit.Domain.Repositorios;

import fuzagit.Domain.Models.Cliente;
import fuzagit.Domain.Models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface pedido_repositorio extends JpaRepository<Pedido, Long> {

    List<Pedido> findByCliente(Cliente cliente);

    @Query("select  p from Pedido p left join fetch p.itemPedidos where p.id =:id")
    Optional<Pedido>findByIdFetchItens(@Param("id") Long id);


}
