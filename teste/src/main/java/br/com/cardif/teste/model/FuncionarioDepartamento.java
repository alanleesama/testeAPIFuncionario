package br.com.cardif.teste.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Data
public class FuncionarioDepartamento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_historico")
    @SequenceGenerator(name="sequence_historico", sequenceName = "seq_historico",allocationSize=10)
    private Long historico_id;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "funcionario_id", referencedColumnName = "funcionario_id")
    private Funcionario funcionario;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "departamento_id", referencedColumnName = "departamento_id")
    private Departamento departamento;

    @Column(nullable = false)
    private LocalDate data_admissao;
}
