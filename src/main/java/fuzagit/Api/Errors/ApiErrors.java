package fuzagit.Api.Errors;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class ApiErrors {
    private List<String> errors;

    public ApiErrors(String mensagemErros){
        this.errors = Arrays.asList(mensagemErros);
    }

    public ApiErrors(List<String> errors){
        this.errors = errors;
    }

}
