package fuzagit.Api.Controllers;


import fuzagit.Domain.Repositorios.cliente_repositorio;
import fuzagit.Domain.Models.Cliente;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class Cliente_Controller {

    @Autowired
    private cliente_repositorio c_repo;

    @GetMapping("{id}")
    public Cliente getClienteById(@PathVariable Long id) {
        return c_repo.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente save(@RequestBody @Valid Cliente cliente) {
        return c_repo.save(cliente);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        c_repo.findById(id)
                .map(cliente -> { c_repo.delete(cliente);
                    return cliente;
                                     })
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Cliente não encontrado"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id ,
                       @RequestBody
                       @Valid Cliente cliente){
                     c_repo
                     .findById(id)
                     .map(clientExistente -> {
                         cliente.setId(clientExistente.getId());
                         c_repo.save(cliente);
                         return ResponseEntity.noContent().build();

                     }).orElseThrow(() ->
                                     new ResponseStatusException(HttpStatus.NOT_FOUND,
                                             "Cliente não encontrado"));
    }

    @GetMapping
    public List<Cliente> find(Cliente filtro){
        ExampleMatcher exampleMatcher = ExampleMatcher.
                 matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher
                        .StringMatcher.CONTAINING);
        Example example = Example.of(filtro, exampleMatcher);
        return c_repo.findAll(example);
    }

}
