package br.com.cardif.teste.repository;

import br.com.cardif.teste.model.FuncionarioDepartamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionarioDepartamentoRepository extends JpaRepository<FuncionarioDepartamento, Long> {

}
