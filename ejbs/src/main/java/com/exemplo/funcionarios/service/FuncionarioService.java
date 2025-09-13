package com.exemplo.funcionarios.service;

import com.exemplo.funcionarios.model.Funcionario;

// CORREÇÃO: Pacotes atualizados de 'javax' para 'jakarta'
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class FuncionarioService {

    // CORREÇÃO CRÍTICA: O nome da unidade de persistência foi corrigido de "funcionariosPU"
    // para "funcionarios-pu", para corresponder exatamente ao que está no seu arquivo persistence.xml.
    @PersistenceContext(unitName = "funcionarios-pu")
    private EntityManager em;

    public void criar(Funcionario f) {
        em.persist(f);
    }

    public void atualizar(Funcionario f) {
        em.merge(f);
    }

    public void remover(Long id) {
        Funcionario f = em.find(Funcionario.class, id);
        if (f != null) {
            em.remove(f);
        }
    }

    public List<Funcionario> listarTodos() {
        return em.createQuery("SELECT f FROM Funcionario f", Funcionario.class).getResultList();
    }

    public Funcionario buscarPorId(Long id) {
        return em.find(Funcionario.class, id);
    }
}
