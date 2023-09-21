package fuzagit;

import fuzagit.Repositorios.cliente_repositorio;
import fuzagit.models.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication

public class Application {

    private cliente_repositorio clienteeRepositorio;

    @Bean
    public CommandLineRunner init(@Autowired cliente_repositorio clientee_repositorio){
        clienteeRepositorio = clientee_repositorio;
        return args -> {

    boolean existe = clientee_repositorio.exisitsByNome("Fuza");
            System.out.println("Exite alguem com esse nome? " + existe);
        };
    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);



    }
}


