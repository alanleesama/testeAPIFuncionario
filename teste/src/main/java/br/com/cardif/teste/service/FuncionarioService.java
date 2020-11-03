package br.com.cardif.teste.service;

import br.com.cardif.teste.model.Cargo;
import br.com.cardif.teste.model.Departamento;
import br.com.cardif.teste.model.Funcionario;
import br.com.cardif.teste.model.FuncionarioDepartamento;
import br.com.cardif.teste.presenter.FuncionarioPresenter;
import br.com.cardif.teste.repository.CargoRepository;
import br.com.cardif.teste.repository.DepartamentoRepository;
import br.com.cardif.teste.repository.FuncionarioRepository;
import br.com.cardif.teste.repository.FuncionarioDepartamentoRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    private FuncionarioRepository funcionarioRepository;

    private FuncionarioDepartamentoRepository funcionarioDepartamentoRepository;

    private CargoRepository cargoRepository;

    private DepartamentoRepository departamentoRepository;


    @Autowired
    public FuncionarioService(FuncionarioRepository funcionarioRepository, FuncionarioDepartamentoRepository funcionarioDepartamentoRepository, CargoRepository cargoRepository, DepartamentoRepository departamentoRepository){
        this.funcionarioRepository = funcionarioRepository;
        this.funcionarioDepartamentoRepository = funcionarioDepartamentoRepository;
        this.cargoRepository = cargoRepository;
        this.departamentoRepository = departamentoRepository;
    }

    public Optional<Funcionario> getFuncionario(Long funcionarioId){
        return funcionarioRepository.findById(funcionarioId);
    }

    public List<Funcionario> getAllbyDepartamentoId(Long departamento_id) {
        return funcionarioRepository.getAllByDepartamento_id(departamento_id);
    }

    public String insertFuncionario(FuncionarioPresenter funcionarioPresenter){
        Optional<Cargo> cargo = cargoRepository.findById(funcionarioPresenter.getCargo_id());
        if(cargo.isPresent())
        {
            Optional<Departamento> departamento = departamentoRepository.findById(funcionarioPresenter.getDepartamento_id());
            if(departamento.isPresent()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setFuncionario_name(funcionarioPresenter.getFuncionario_name());
                funcionario.setFuncionario_age(funcionarioPresenter.getFuncionario_age());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate localDate = LocalDate.parse(funcionarioPresenter.getFuncionario_birthday(), formatter);
                localDate.format(formatter);
                funcionario.setFuncionario_birthday(localDate);
                funcionario.setFuncionario_document(funcionarioPresenter.getFuncionario_document());
                funcionario.setCargo(cargo.get());
                funcionario.setDepartamento(departamento.get());
                funcionarioRepository.save(funcionario);

                FuncionarioDepartamento funcionarioDepartamento = new FuncionarioDepartamento();
                funcionarioDepartamento.setDepartamento(departamento.get());
                funcionarioDepartamento.setFuncionario(funcionario);
                funcionarioDepartamento.setData_admissao(LocalDate.now());
                funcionarioDepartamentoRepository.save(funcionarioDepartamento);
                return "Funcionario cadastrado com sucesso";
            }else{
                return "Departamento não encontrado";
            }
        }else{
            return "Cargo não encontrado";
        }
    }

    public String updateFuncionario(FuncionarioPresenter funcionarioPresenter){
        Optional<Funcionario> funcionario = funcionarioRepository.findById(funcionarioPresenter.getFuncionario_id());
        if(funcionario.isPresent()) {
            Funcionario func = funcionario.get();
            if (funcionarioPresenter.getCargo_id().longValue() != 0) {
                Optional<Cargo> cargo = cargoRepository.findById(funcionarioPresenter.getCargo_id());
                if (cargo.isPresent()) {
                    func.setCargo(cargo.get());
                }else{
                    return "Cargo não localizado";
                }
            }
            if (funcionarioPresenter.getDepartamento_id().longValue() != 0){
                Optional<Departamento> departamento = departamentoRepository.findById(funcionarioPresenter.getDepartamento_id());
                if(departamento.isPresent()){
                    func.setDepartamento(departamento.get());
                }else{
                    return "Departamento não localizado";
                }
            }
            if (!funcionarioPresenter.getFuncionario_name().isEmpty()) {
                func.setFuncionario_name(funcionarioPresenter.getFuncionario_name());
            }
            if (funcionarioPresenter.getFuncionario_age().intValue() != 0) {
                func.setFuncionario_age(funcionarioPresenter.getFuncionario_age());
            }
            if (!funcionarioPresenter.getFuncionario_birthday().isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate localDate = LocalDate.parse(funcionarioPresenter.getFuncionario_birthday(), formatter);
                localDate.format(formatter);
                func.setFuncionario_birthday(localDate);
            }
            if (!funcionarioPresenter.getFuncionario_document().isEmpty()) {
                func.setFuncionario_document(funcionarioPresenter.getFuncionario_document());
            }
            funcionarioRepository.save(func);
            FuncionarioDepartamento funcionarioDepartamento = new FuncionarioDepartamento();
            funcionarioDepartamento.setDepartamento(func.getDepartamento());
            funcionarioDepartamento.setFuncionario(func);
            funcionarioDepartamento.setData_admissao(LocalDate.now());
            funcionarioDepartamentoRepository.save(funcionarioDepartamento);
            return "Funcionario atualizado com sucesso";
        }
        else{
            insertFuncionario(funcionarioPresenter);
            return "Funcionário novo cadastrado com sucesso";
        }
    }

    public void deleteFuncionario(Long id){
        funcionarioRepository.deleteById(id);
    }

    public String setDepartamentoChefe(Long id_funcionario, Long id_departamento){
        Optional<Departamento> departamento = departamentoRepository.findById(id_departamento);
        if(departamento.isPresent()){
            Departamento depto = departamento.get();
            Optional<Funcionario> funcionario = funcionarioRepository.findById(id_funcionario);
            if(funcionario.isPresent()){
                Funcionario func = funcionario.get();
                depto.setFuncionario_chefe(func);
                departamentoRepository.save(depto);
                FuncionarioDepartamento funcionarioDepartamento = new FuncionarioDepartamento();
                funcionarioDepartamento.setDepartamento(depto);
                funcionarioDepartamento.setFuncionario(func);
                funcionarioDepartamento.setData_admissao(LocalDate.now());
                funcionarioDepartamentoRepository.save(funcionarioDepartamento);
                return "Chefe do departamento gravado com sucesso";
            }else{
                return "Funcionário não encontrado";
            }
        }else{
            return "Departamento não encontrado";
        }
    }
}
