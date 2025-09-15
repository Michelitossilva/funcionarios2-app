package com.exemplo.funcionarios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller da camada web. Substitui o nosso antigo FuncionarioBean.
 * Ele lida com as requisições HTTP e interage com a camada de serviço.
 */
@Controller
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    /**
     * Lida com as requisições GET para a página inicial ("/").
     * Prepara o modelo com a lista de todos os funcionários e um novo objeto Funcionario
     * para o formulário de criação.
     * @param model O objeto do Spring para passar dados para a view (página HTML).
     * @return O nome do template Thymeleaf a ser renderizado ("index").
     */
    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("listaFuncionarios", funcionarioService.listarTodos());
        model.addAttribute("funcionario", new Funcionario()); // Para o formulário de criação
        return "index"; // Retorna o nome do template: /src/main/resources/templates/index.html
    }

    /**
     * Lida com as submissões POST do formulário a partir do URL "/salvar".
     * O @ModelAttribute preenche o objeto Funcionario com os dados do formulário.
     * @param funcionario O objeto preenchido com os dados do formulário.
     * @return Um redirecionamento para a página inicial para evitar o reenvio de dados.
     */
    @PostMapping("/salvar")
    public String salvarFuncionario(@ModelAttribute("funcionario") Funcionario funcionario) {
        funcionarioService.salvar(funcionario);
        return "redirect:/";
    }

    /**
     * Lida com as requisições GET para editar um funcionário, ex: "/editar/1".
     * Prepara o modelo com os dados do funcionário a ser editado.
     * @param id O ID do funcionário extraído da URL com @PathVariable.
     * @param model O objeto do Spring para passar dados para a view.
     * @return O nome do template Thymeleaf a ser renderizado ("index").
     */
    @GetMapping("/editar/{id}")
    public String showEditForm(@PathVariable(value = "id") long id, Model model) {
        Funcionario funcionario = funcionarioService.buscarPorId(id);
        model.addAttribute("funcionario", funcionario); // Objeto para o formulário de edição
        model.addAttribute("listaFuncionarios", funcionarioService.listarTodos()); // Adiciona a lista para a tabela
        return "index"; // Reutiliza a mesma página para edição
    }

    /**
     * Lida com as requisições GET para remover um funcionário, ex: "/remover/1".
     * @param id O ID do funcionário extraído da URL com @PathVariable.
     * @return Um redirecionamento para a página inicial para atualizar a tabela.
     */
    @GetMapping("/remover/{id}")
    public String removerFuncionario(@PathVariable(value = "id") long id) {
        funcionarioService.remover(id);
        return "redirect:/";
    }
}

