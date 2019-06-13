package com.com.controletarefas;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.com.controletarefas.model.Desenvolvedor;
import com.com.controletarefas.model.Tarefa;
import com.com.controletarefas.repository.DesenvolvedorRepository;
import com.com.controletarefas.repository.TarefaRepository;

@Controller
public class TarefaController {
	
	@SuppressWarnings("unused")
	@Autowired
	private TarefaRepository tr;
	
	@Autowired
	private DesenvolvedorRepository dr;

	@RequestMapping(value="/cadastrarTarefa", method=RequestMethod.GET)
	public String form() {
		return "tarefa/formTarefa";
	}
	
	@RequestMapping(value="/cadastrarTarefa", method=RequestMethod.POST)
	public String form(@Valid Tarefa tarefa,  BindingResult result, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Digite o Nome, Detalhe, Data inicio e fim!");
			return "redirect:/cadastrarTarefa";
		}
		
		tr.save(tarefa);
		
		
		attributes.addFlashAttribute("mensagem", "Tarefa adicionado com sucesso!");
		
		return "redirect:/cadastrarTarefa";
	}
	
	@RequestMapping(value="/tarefas", method=RequestMethod.GET)
	public ModelAndView listaTarefas() {
		
		ModelAndView mv = new ModelAndView("index");
		
		Iterable<Tarefa> tarefas = tr.findAll();
		
		mv.addObject("tarefas", tarefas);
		
		return mv;
		
	}
	
	@RequestMapping(value="/{codigo}", method=RequestMethod.GET)
	public ModelAndView detalhesTarefa(@PathVariable("codigo") long codigo) {
		
		Tarefa tarefa = tr.findByCodigo(codigo);
		
		ModelAndView mv = new ModelAndView("tarefa/detalhesTarefa");
		mv.addObject("tarefa", tarefa);
		
		
		Iterable<Desenvolvedor> densenvolvedores = dr.findByTarefa(tarefa);
		mv.addObject("desenvolvedores", densenvolvedores);
		
		return mv;
	}
	
	@RequestMapping("/deletarTarefa")
	public String deletarTarefa(long codigo) {
		
		Tarefa tarefa = tr.findByCodigo(codigo);
		tr.delete(tarefa);
		
		return "redirect:/tarefas";
	}
	
	@RequestMapping(value="/{codigo}", method=RequestMethod.POST)
	public String detalhesTarefaPost(@PathVariable("codigo") long codigo, @Valid Desenvolvedor desenvolvedor, BindingResult result, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Digite o nome e o rg!");
			return "redirect:/{codigo}";
		}
		
		
		Tarefa tarefa = tr.findByCodigo(codigo);
		desenvolvedor.setTarefa(tarefa);
		dr.save(desenvolvedor);
		
		
		attributes.addFlashAttribute("mensagem", "Desenvolvedor adicionado com sucesso!");
		
		return "redirect:/{codigo}";
	}
	
	
	@RequestMapping("/deletarDesenvolvedor")
	public String deletarDesenvolvedor(long codigo) {
		
		Desenvolvedor desenvolvedor = dr.findByCodigo(codigo);
		dr.delete(desenvolvedor);
		
		Tarefa tarefa = desenvolvedor.getTarefa();
		long codigoTarefa = tarefa.getCodigo();
		
		
		return "redirect:/"+codigoTarefa;
	}
	
}