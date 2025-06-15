package br.com.cancelamento.adapters.input;

import br.com.cancelamento.domain.ports.input.CancelamentoUseCase;
import br.com.cancelamento.exceptions.CancelamentoForaDoPrazoException;
import br.com.cancelamento.exceptions.TransacaoNaoEncontradaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
// Classe principal da aplicação que inicia o Spring Boot
public class CancelamentoController {

    private final CancelamentoUseCase cancelamentoUseCase;

    public CancelamentoController(CancelamentoUseCase cancelamentoUseCase) {
        this.cancelamentoUseCase = cancelamentoUseCase;
    }

    @PostMapping("/cancelar")
    public ResponseEntity<String> cancelar(@RequestBody CancelamentoRequest request) {
        cancelamentoUseCase.cancelar(request.getIdTransacao(), request.getMensagem());
        return ResponseEntity.ok("OK");
    }


    @ExceptionHandler(TransacaoNaoEncontradaException.class)
    public ResponseEntity<String> handleTransacaoNaoEncontrada(TransacaoNaoEncontradaException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(CancelamentoForaDoPrazoException.class)
    public ResponseEntity<String> handleForaDoPrazo(CancelamentoForaDoPrazoException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getMessage());
    }
}