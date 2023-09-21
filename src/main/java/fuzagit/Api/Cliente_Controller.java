package fuzagit.Api;


import fuzagit.Repositorios.cliente_repositorio;
import fuzagit.models.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class Cliente_Controller {

    @Autowired
    private cliente_repositorio c_repo;

    @GetMapping("{/id}")
    @ResponseBody
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id){
        Optional<Cliente> cliente =  c_repo.findById(id);

        if (cliente.isPresent()){
            return ResponseEntity.ok(cliente.get());
    }
        return  ResponseEntity.notFound().build();
    }


}
