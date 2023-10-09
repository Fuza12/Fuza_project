package fuzagit.Api.Controllers;

import fuzagit.Api.Errors.ApiErrors;
import fuzagit.exception.PedidoNaoEncontradoException;
import fuzagit.exception.RegraNegocioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

class ApplicationControllerAdviceTest {

    private ApplicationControllerAdvice applicationControllerAdviceUnderTest;

    @BeforeEach
    void setUp() {
        applicationControllerAdviceUnderTest = new ApplicationControllerAdvice();
    }

    @Test
    void testHandleRegraNegocioException() {
        // Setup
        final RegraNegocioException exception = new RegraNegocioException("mensagemErros");
        final ApiErrors expectedResult = new ApiErrors("mensagemErros");

        // Run the test
        final ApiErrors result = applicationControllerAdviceUnderTest.handleRegraNegocioException(exception);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testHandlePedidoNotFoundException() {
        // Setup
        final PedidoNaoEncontradoException exception = new PedidoNaoEncontradoException();
        final ApiErrors expectedResult = new ApiErrors("mensagemErros");

        // Run the test
        final ApiErrors result = applicationControllerAdviceUnderTest.handlePedidoNotFoundException(exception);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testHandleMethodValidException() {
        // Setup
        final MethodArgumentNotValidException exception = new MethodArgumentNotValidException(
                new MethodParameter((Method) null, 0), null);
        final ApiErrors expectedResult = new ApiErrors("mensagemErros");

        // Run the test
        final ApiErrors result = applicationControllerAdviceUnderTest.handleMethodValidException(exception);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
