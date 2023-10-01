package fuzagit.Api;


import fuzagit.Repositorios.cliente_repositorio;
import fuzagit.models.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class Cliente_Controller {

    @Autowired
    private cliente_repositorio c_repo;

    @GetMapping("{id}")
    @ResponseBody
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        Optional<Cliente> cliente = c_repo.findById(id);

        if (cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity save(@RequestBody Cliente cliente) {
        Cliente clientesalvo = c_repo.save(cliente);
        return ResponseEntity.ok(clientesalvo);
    }

    @DeleteMapping("{id}")
    @ResponseBody
    public ResponseEntity delete(@PathVariable Long id) {
        Optional<Cliente> cliente = c_repo.findById(id);

        if (cliente.isPresent()) {
            c_repo.delete(cliente.get() );
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


    @PutMapping("{id}")
    @ResponseBody
    public ResponseEntity update(@PathVariable Long id ,
                                 @RequestBody Cliente cliente){
        return c_repo
                     .findById(id)
                     .map(clientExistente -> {
                         cliente.setId(clientExistente.getId());
                         c_repo.save(cliente);
                         return ResponseEntity.noContent().build();

                     }).orElseGet( () -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity find(Cliente filtro){
        ExampleMatcher exampleMatcher = ExampleMatcher.
                 matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher
                        .StringMatcher.CONTAINING);
        Example example = Example.of(filtro, exampleMatcher);
        List<Cliente> list = c_repo.findAll(example);
        return ResponseEntity.ok(list);


    }

}
