package br.com.cardif.teste.presenter;

import lombok.Data;

@Data
public class FuncionarioPresenter {

    private Long funcionario_id;

    private String funcionario_name;

    private Integer funcionario_age;

    private String funcionario_birthday;

    private String funcionario_document;

    private Long cargo_id;

    private Long departamento_id;

}
