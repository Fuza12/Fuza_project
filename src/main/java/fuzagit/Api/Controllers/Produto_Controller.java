package fuzagit.Api.Controllers;


import fuzagit.Domain.Models.Produto;
import fuzagit.Domain.Repositorios.produto_repositorio;
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
@RequestMapping("/api/produtos")
public class Produto_Controller {

    @Autowired
    private produto_repositorio p_repo;


    @GetMapping("{id}")
    public Produto getProdutoById(@PathVariable Long id) {
        return p_repo.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto save(@RequestBody @Valid Produto produto) {
        return p_repo.save(produto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        p_repo.findById(id)
                .map(produto -> {
                    p_repo.delete(produto);
                    return produto;
                })
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Produto não encontrado"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id,
                       @RequestBody
                       @Valid Produto produto) {
        p_repo
                .findById(id)
                .map(ProductExistente -> {
                    produto.setId(ProductExistente.getId());
                    p_repo.save(produto);
                    return ResponseEntity.noContent().build();

                }).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Produto não encontrado"));
    }

    @GetMapping
    public List<Produto> find(Produto filtro) {
        ExampleMatcher exampleMatcher = ExampleMatcher.
                matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher
                        .StringMatcher.CONTAINING);
        Example example = Example.of(filtro, exampleMatcher);
        return p_repo.findAll(example);
    }

}
