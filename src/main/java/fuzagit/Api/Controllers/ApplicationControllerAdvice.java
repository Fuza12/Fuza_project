package fuzagit.Api.Controllers;

import fuzagit.Api.Errors.ApiErrors;
import fuzagit.exception.PedidoNaoEncontradoException;
import fuzagit.exception.RegraNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@ControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleRegraNegocioException(RegraNegocioException exception) {
        String mensagemerro = exception.getMessage();
        return new ApiErrors(mensagemerro);
    }

    @ExceptionHandler(PedidoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handlePedidoNotFoundException(PedidoNaoEncontradoException exception) {
        return new ApiErrors(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiErrors handleMethodValidException(MethodArgumentNotValidException exception) {
        List<String> errors =
                exception
                        .getBindingResult()
                        .getAllErrors().stream()
                        .map
                                (erro -> erro.getDefaultMessage())
                        .collect(Collectors.toList());

        return new ApiErrors(errors);
    }
}
