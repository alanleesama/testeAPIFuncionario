package br.com.cardif.teste.repository;

import br.com.cardif.teste.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario,Long> {

    @Query(value = "SELECT * FROM FUNCIONARIO f WHERE f.departamento_id = ?1", nativeQuery = true)
    public List<Funcionario> getAllByDepartamento_id(Long departamento_id);
}
