package fuzagit.Domain.Repositorios;

import fuzagit.Domain.Models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface produto_repositorio extends JpaRepository<Produto, Long> {
}
