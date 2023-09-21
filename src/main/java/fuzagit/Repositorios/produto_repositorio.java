package fuzagit.Repositorios;

import fuzagit.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface produto_repositorio extends JpaRepository<Produto, Long> {
}
