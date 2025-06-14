
package br.com.cancelamento.adapters.input;

import br.com.cancelamento.domain.ports.input.CancelamentoUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CancelamentoController {

    private final CancelamentoUseCase cancelamentoUseCase;

    public CancelamentoController(CancelamentoUseCase cancelamentoUseCase) {
        this.cancelamentoUseCase = cancelamentoUseCase;
    }

    @PostMapping("/cancelar")
    public ResponseEntity<String> cancelar(@RequestBody CancelamentoRequest request) {
        cancelamentoUseCase.cancelar(request.getNumeroConta(), request.getMensagem());
        return ResponseEntity.ok("OK");
    }
}
