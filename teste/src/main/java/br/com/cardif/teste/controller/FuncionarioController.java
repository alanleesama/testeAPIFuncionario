package br.com.cardif.teste.controller;

import br.com.cardif.teste.model.Funcionario;
import br.com.cardif.teste.presenter.ChefePresenter;
import br.com.cardif.teste.presenter.FuncionarioPresenter;
import br.com.cardif.teste.service.FuncionarioService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {

    private FuncionarioService funcionarioService;

    @Autowired
    public FuncionarioController(FuncionarioService funcionarioService){
        this.funcionarioService = funcionarioService;
    }

    @ApiOperation(value = "Busca informações de um funcionario")
    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioPresenter> getFuncionario(@PathVariable Long id){
        Optional<Funcionario> funcionario = funcionarioService.getFuncionario(id);
        if(funcionario.isPresent()){
            FuncionarioPresenter funcionarioPresenter = new FuncionarioPresenter();
            funcionarioPresenter.setFuncionario_id(funcionario.get().getFuncionario_id());
            funcionarioPresenter.setFuncionario_name(funcionario.get().getFuncionario_name());
            funcionarioPresenter.setFuncionario_age(funcionario.get().getFuncionario_age());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            funcionarioPresenter.setFuncionario_birthday(funcionario.get().getFuncionario_birthday().format(formatter));
            funcionarioPresenter.setFuncionario_document(funcionario.get().getFuncionario_document());
            funcionarioPresenter.setCargo_id(funcionario.get().getCargo().getCargo_id());
            return ResponseEntity.ok(funcionarioPresenter);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ApiOperation(value = "Cadastra um funcionario")
    @PostMapping
    public ResponseEntity<String> insertFuncionario(@RequestBody FuncionarioPresenter funcionarioPresenter){
        String retorno = funcionarioService.insertFuncionario(funcionarioPresenter);
        if(retorno.equals("Funcionario cadastrado com sucesso")){
            return ResponseEntity.ok(retorno);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(retorno);
        }
    }

    @ApiOperation(value = "Exclui um funcionario")
    @DeleteMapping("/{id}")
    public void deleteFuncionario(@PathVariable Long id){
        funcionarioService.deleteFuncionario(id);
    }

    @ApiOperation(value = "Atualiza um funcionario")
    @PatchMapping
    public ResponseEntity<String> updateFuncionario(@RequestBody FuncionarioPresenter funcionarioPresenter){
        String retorno = funcionarioService.updateFuncionario(funcionarioPresenter);
        if(retorno.equals("Cargo não localizado")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(retorno);
        }else{
            return ResponseEntity.ok(retorno);
        }
    }

    @ApiOperation(value = "Determina um funcionário como chefe de um departamento")
    @PostMapping("/chefedepto")
    public ResponseEntity<String> setChefeDepartamento(@RequestBody ChefePresenter chefePresenter){
        String retorno = funcionarioService.setDepartamentoChefe(chefePresenter.getFuncionario_id(), chefePresenter.getDepartamento_id());
        if(retorno.equals("Chefe do departamento gravado com sucesso")){
            return ResponseEntity.ok(retorno);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(retorno);
        }
    }

    @ApiOperation(value = "Lista todos os funcionarios de um departamento")
    @GetMapping("/listaTodos/{id}")
    public ResponseEntity<List<FuncionarioPresenter>> listAllByDepartamento(@PathVariable Long id){
        List<Funcionario> funcionarios = funcionarioService.getAllbyDepartamentoId(id);
        List<FuncionarioPresenter> funcionarioPresenters = new ArrayList<>();
        for(Funcionario f : funcionarios){
            FuncionarioPresenter funcionarioPresenter = new FuncionarioPresenter();
            funcionarioPresenter.setFuncionario_id(f.getFuncionario_id());
            funcionarioPresenter.setFuncionario_name(f.getFuncionario_name());
            funcionarioPresenter.setFuncionario_age(f.getFuncionario_age());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            funcionarioPresenter.setFuncionario_birthday(f.getFuncionario_birthday().format(formatter));
            funcionarioPresenter.setFuncionario_document(f.getFuncionario_document());
            funcionarioPresenter.setCargo_id(f.getCargo().getCargo_id());
            funcionarioPresenter.setDepartamento_id(f.getDepartamento().getDepartamento_id());
            funcionarioPresenters.add(funcionarioPresenter);
        }
        return ResponseEntity.ok(funcionarioPresenters);
    }




}
