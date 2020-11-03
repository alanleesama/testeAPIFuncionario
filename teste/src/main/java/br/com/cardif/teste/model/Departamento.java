package br.com.cardif.teste.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_departamento")
    @SequenceGenerator(name="sequence_departamento", sequenceName = "seq_depto",allocationSize=10)
    private Long departamento_id;

    @Column
    private String departamento_name;

    @OneToOne
    @JoinColumn(name="chefe_id", referencedColumnName = "funcionario_id")
    private Funcionario funcionario_chefe;

    @OneToMany(mappedBy = "departamento")
    private List<FuncionarioDepartamento> funcionarioDepartamento;

    @OneToMany(mappedBy = "departamento")
    private List<Funcionario> funcionario;

}
