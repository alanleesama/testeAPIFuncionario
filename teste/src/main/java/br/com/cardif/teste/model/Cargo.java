package br.com.cardif.teste.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_cargo")
    @SequenceGenerator(name="sequence_cargo", sequenceName = "seq_cargo")
    private Long cargo_id;

    @Column
    private String cargo_name;

    @OneToMany(mappedBy = "cargo")
    private List<Funcionario> funcionario;
}
