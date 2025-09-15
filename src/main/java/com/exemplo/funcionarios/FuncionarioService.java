package com.exemplo.funcionarios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Camada de serviço, responsável pela lógica de negócio.
 * O antigo EJB @Stateless agora é um @Service do Spring.
 * Ele delega as operações de banco de dados para o FuncionarioRepository.
 */
@Service
public class FuncionarioService {

    // Injeção de dependência do nosso repositório de dados
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    /**
     * Retorna uma lista com todos os funcionários cadastrados.
     * @return a lista de funcionários.
     */
    public List<Funcionario> listarTodos() {
        return funcionarioRepository.findAll();
    }

    /**
     * Salva um funcionário no banco de dados.
     * O método save() do Spring Data JPA lida tanto com a criação (se o ID for nulo)
     * quanto com a atualização (se o ID já existir).
     * @param funcionario o objeto funcionário a ser salvo.
     */
    public void salvar(Funcionario funcionario) {
        funcionarioRepository.save(funcionario);
    }

    /**
     * Busca um único funcionário pelo seu ID.
     * @param id o ID do funcionário a ser encontrado.
     * @return o objeto Funcionario.
     * @throws RuntimeException se o funcionário não for encontrado.
     */
    public Funcionario buscarPorId(long id) {
        Optional<Funcionario> optional = funcionarioRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        // Lançar uma exceção é uma boa prática caso o ID não exista
        throw new RuntimeException("Funcionário não encontrado pelo id :: " + id);
    }

    /**
     * Remove um funcionário do banco de dados pelo seu ID.
     * @param id o ID do funcionário a ser removido.
     */
    public void remover(long id) {
        this.funcionarioRepository.deleteById(id);
    }
}

