package fuzagit.Repositorios;

import fuzagit.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface cliente_repositorio extends JpaRepository<Cliente, Long> {

   // List<Cliente>FindByNomeLike(String nome);

    boolean exisitsByNome(String nome);


}
