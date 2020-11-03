package br.com.cardif.teste.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_funcionario")
    @SequenceGenerator(name="sequence_funcionario", sequenceName = "seq_func",allocationSize=10)
    private Long funcionario_id;

    @Column(nullable = false)
    private String funcionario_name;

    @Column(nullable = false)
    private Integer funcionario_age;

    @Column(nullable = false)
    private LocalDate funcionario_birthday;

    @Column(nullable = false)
    private String funcionario_document;

    @ManyToOne
    @JoinColumn(name="cargo_id", referencedColumnName = "cargo_id")
    private Cargo cargo;

    @ManyToOne
    @JoinColumn(name="departamento_id", referencedColumnName = "departamento_id")
    private Departamento departamento;

    @OneToOne(mappedBy = "funcionario_chefe", cascade = CascadeType.ALL, orphanRemoval = true)
    private Departamento departamento_chefe;

    @OneToMany(mappedBy = "funcionario")
    private List<FuncionarioDepartamento> funcionarioDepartamento;
}
