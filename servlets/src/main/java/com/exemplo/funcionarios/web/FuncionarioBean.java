package com.exemplo.funcionarios.web;

import com.exemplo.funcionarios.model.Funcionario;
import com.exemplo.funcionarios.service.FuncionarioService;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Named("funcionarioBean")
@RequestScoped
public class FuncionarioBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private FuncionarioService funcionarioService;

    private Funcionario funcionario = new Funcionario();

    /**
     * Retorna todos os funcionários para a tabela.
     */
    public List<Funcionario> getFuncionarios() {
        return funcionarioService.listarTodos();
    }

    /**
     * Salva um novo funcionário ou atualiza um existente.
     */
    public String salvar() {
        // Garantindo que o salário tenha 2 casas decimais
        if (funcionario.getSalario() != null) {
            funcionario.setSalario(funcionario.getSalario().setScale(2, RoundingMode.HALF_UP));
        }

        if (funcionario.getId() == null) {
            funcionarioService.criar(funcionario);
        } else {
            funcionarioService.atualizar(funcionario);
        }

        // Limpa o formulário
        funcionario = new Funcionario();

        // Redireciona para atualizar a tabela
        return "index.xhtml?faces-redirect=true";
    }

    // --- IMPLEMENTAÇÃO ADICIONADA ---

    /**
     * Prepara um funcionário para edição, carregando seus dados no formulário.
     * Este método é chamado pelo botão "Editar" na página.
     * @param func O funcionário da linha da tabela que foi clicado.
     */
    public void editar(Funcionario func) {
        this.funcionario = func;
    }

    /**
     * Remove um funcionário do banco de dados.
     * Este método é chamado pelo botão "Remover" na página.
     * @param id O ID do funcionário a ser removido.
     * @return Redireciona para a página principal para atualizar a lista.
     */
    public String remover(Long id) {
        funcionarioService.remover(id);
        return "index.xhtml?faces-redirect=true";
    }

    // --- FIM DA IMPLEMENTAÇÃO ---


    // Getters e Setters

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    /**
     * Getter/Setter auxiliar para o salário diretamente como BigDecimal.
     * Útil para o <f:convertNumber> do JSF.
     */
    public BigDecimal getSalario() {
        return funcionario.getSalario();
    }

    public void setSalario(BigDecimal salario) {
        if (salario != null) {
            funcionario.setSalario(salario.setScale(2, RoundingMode.HALF_UP));
        } else {
            funcionario.setSalario(null);
        }
    }
}

