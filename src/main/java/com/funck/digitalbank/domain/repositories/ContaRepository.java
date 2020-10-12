package com.funck.digitalbank.domain.repositories;

import com.funck.digitalbank.domain.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.websocket.server.PathParam;
import java.util.Optional;

public interface ContaRepository extends JpaRepository<Conta, String> {

    boolean existsByProposta_id(String idProposta);

    @Query(nativeQuery = true, value = "select c.* from conta c inner join proposta_conta pc on pc.id = c.proposta_id " +
            "inner join pessoa p on p.id = pc.pessoa_id where p.cpf = :cpf and p.email = :email")
    Optional<Conta> findByEmailAndCpfPessoa(@PathParam("email") String email, @PathParam("cpf") String cpf);

    Optional<Conta> findByAgenciaAndNumero(String agencia, String numero);

}
