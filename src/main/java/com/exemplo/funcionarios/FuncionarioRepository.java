package com.exemplo.funcionarios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface de Repositório. Substitui o uso manual do EntityManager.
 * Ao estender JpaRepository, o Spring Data JPA automaticamente nos fornece
 * métodos CRUD prontos (save, findById, findAll, deleteById, etc.).
 */
@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
}

