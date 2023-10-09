package fuzagit.Domain.Repositorios;

import fuzagit.Domain.Models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface cliente_repositorio extends JpaRepository<Cliente, Long> {

    @Query(value = "SELECT * FROM Cliente c WHERE c.nome LIKE %:nome%", nativeQuery = true)
    List<Cliente>encontrarPorNome(@Param("nome") String nome);

    @Query(" delete from Cliente c where c.nome =:nome ")
    @Modifying
    void deleteByNome(String nome);

    boolean existsByNome(String nome);

    @Query(" SELECT c FROM Cliente c LEFT JOIN FETCH c.pedidos WHERE c.id = :id ")
    Cliente findClienteFechPedidos(@Param("id") Integer id);

}
