package fuzagit.Repositorios;

import fuzagit.models.Cliente;
import fuzagit.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface pedido_repositorio extends JpaRepository<Pedido, Long> {

    List<Pedido> findByCliente(Cliente cliente);


}
