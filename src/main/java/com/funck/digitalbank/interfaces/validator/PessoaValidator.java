package com.funck.digitalbank.interfaces.validator;

import com.funck.digitalbank.domain.repositories.PessoaRepository;
import com.funck.digitalbank.interfaces.dto.NovaPessoaDTO;
import com.funck.digitalbank.interfaces.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@RequiredArgsConstructor
@Component
public class PessoaValidator implements Validator {

    private final PessoaRepository pessoaRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return NovaPessoaDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        var novaPessoaDTO = (NovaPessoaDTO) target;

        if (pessoaRepository.existsByEmail(novaPessoaDTO.getEmail())) {
            throw new BadRequestException();
        }

        if (LocalDate.now().isBefore(novaPessoaDTO.getDataNascimento())) {
            throw new BadRequestException();
        }

        if (novaPessoaDTO.getIdade() < 18) {
            throw new BadRequestException();
        }
    }

}
