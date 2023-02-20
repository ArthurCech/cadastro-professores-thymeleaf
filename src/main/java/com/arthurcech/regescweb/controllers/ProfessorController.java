package com.arthurcech.regescweb.controllers;

import com.arthurcech.regescweb.dto.RequisicaoFormProfessor;
import com.arthurcech.regescweb.models.Professor;
import com.arthurcech.regescweb.models.StatusProfessor;
import com.arthurcech.regescweb.repositories.ProfessorRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/professores")
public class ProfessorController {

    private final ProfessorRepository professorRepository;

    public ProfessorController(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    @GetMapping
    public ModelAndView index() {
        List<Professor> professores = professorRepository.findAll();
        ModelAndView mv = new ModelAndView("professores/index");
        mv.addObject("professores", professores);
        return mv;
    }

    @GetMapping("/new")
    public ModelAndView nnew(RequisicaoFormProfessor requisicaoFormProfessor) {
        ModelAndView modelAndView = new ModelAndView("professores/new");
        modelAndView.addObject("listaStatusProfessor", StatusProfessor.values());
        return modelAndView;
    }

    /*
        Por que não utilizar a classe da entidade como parâmetro?
        - Web Parameter Tampering: o usuário pode passar um atributo sensível, como salário, na requisição.
        - O usuário cria um input nas ferramentas de desenvolvedor do navegador
        - Para evitar isso, utilizamos o padrão DTO (Data Transfer Object)
     */
    @PostMapping
    public ModelAndView create(@Valid RequisicaoFormProfessor requisicaoFormProfessor, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView("professores/new");
            mv.addObject("requisicaoFormProfessor", requisicaoFormProfessor);
            mv.addObject("listaStatusProfessor", StatusProfessor.values());
            return mv;
        } else {
            Professor professor = requisicaoFormProfessor.toProfessor();
            professorRepository.save(professor);
            return new ModelAndView("redirect:/professores/" + professor.getId());
        }
    }

    @GetMapping("/{id}")
    public ModelAndView show(@PathVariable("id") Long id) {
        Optional<Professor> optionalProfessor = professorRepository.findById(id);
        if (optionalProfessor.isPresent()) {
            ModelAndView mv = new ModelAndView("professores/show");
            mv.addObject("professor", optionalProfessor.get());
            return mv;
        } else {
            return retornaErroProfessor("SHOW ERROR: Professor #" + id + " não encontrado!");
        }
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable("id") Long id, RequisicaoFormProfessor requisicaoFormProfessor) {
        Optional<Professor> optionalProfessor = professorRepository.findById(id);
        if (optionalProfessor.isPresent()) {
            Professor professor = optionalProfessor.get();
            requisicaoFormProfessor.fromProfessor(professor);
            ModelAndView mv = new ModelAndView("professores/edit");
            mv.addObject("listaStatusProfessor", StatusProfessor.values());
            mv.addObject("professorId", professor.getId());
            return mv;
        } else {
            return retornaErroProfessor("EDIT ERROR: Professor #" + id + " não encontrado!");
        }
    }

    @PostMapping("/{id}")
    public ModelAndView update(@PathVariable("id") Long id, @Valid RequisicaoFormProfessor requisicaoFormProfessor,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView("professores/edit");
            mv.addObject("listaStatusProfessor", StatusProfessor.values());
            return mv;
        } else {
            Optional<Professor> optionalProfessor = professorRepository.findById(id);
            if (optionalProfessor.isPresent()) {
                Professor professor = requisicaoFormProfessor.toProfessor(optionalProfessor.get());
                professorRepository.save(professor);
                return new ModelAndView("redirect:/professores/" + professor.getId());
            } else {
                return retornaErroProfessor("UPDATE ERROR: Professor #" + id + " não encontrado!");
            }
        }
    }

    @GetMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("redirect:/professores");
        try {
            professorRepository.deleteById(id);
            mv.addObject("mensagem", "Professor #" + id + " deletado com sucesso!");
            mv.addObject("erro", false);
        } catch (EmptyResultDataAccessException e) {
            mv = retornaErroProfessor("DELETE ERROR: Professor #" + id + " não encontrado!");
        }
        return mv;
    }

    private ModelAndView retornaErroProfessor(String msg) {
        ModelAndView mv = new ModelAndView("redirect:/professores");
        mv.addObject("mensagem", msg);
        mv.addObject("erro", true);
        return mv;
    }

}
