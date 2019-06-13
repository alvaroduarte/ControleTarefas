package com.com.controletarefas.repository;

import org.springframework.data.repository.CrudRepository;

import com.com.controletarefas.model.Desenvolvedor;
import com.com.controletarefas.model.Tarefa;

public interface DesenvolvedorRepository extends CrudRepository<Desenvolvedor, String> {
		
	Iterable<Desenvolvedor> findByTarefa(Tarefa tarefa);
	
	Desenvolvedor findByCodigo(long codigo);


}