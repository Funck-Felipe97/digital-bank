package com.funck.digitalbank.interfaces.endpoints;

import com.funck.digitalbank.application.TransferenciaSaldoService;
import com.funck.digitalbank.interfaces.dto.TransferenciaInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Set;

@RequiredArgsConstructor
@RequestMapping("/conta/transferencia")
@RestController
public class TransferenciaResource {

    private final TransferenciaSaldoService transferenciaSaldoService;

    @PostMapping
    public void salvarTransferencia(@RequestBody final Set<@Valid TransferenciaInfo> transferencias) {
        transferenciaSaldoService.salvarTransferencia(transferencias);
    }

}
