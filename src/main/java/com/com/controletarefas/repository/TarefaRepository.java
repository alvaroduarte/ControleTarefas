package com.com.controletarefas.repository;

import org.springframework.data.repository.CrudRepository;

import com.com.controletarefas.model.Tarefa;

public interface TarefaRepository extends CrudRepository<Tarefa, String> {
	
	
	Tarefa findByCodigo(long codigo);
	
	

}
