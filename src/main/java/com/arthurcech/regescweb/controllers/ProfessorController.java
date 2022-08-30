package com.arthurcech.regescweb.controllers;

import com.arthurcech.regescweb.dto.RequisicaoNovoProfessor;
import com.arthurcech.regescweb.models.Professor;
import com.arthurcech.regescweb.models.StatusProfessor;
import com.arthurcech.regescweb.repositories.ProfessorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProfessorController {

    private final ProfessorRepository professorRepository;

    public ProfessorController(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    @GetMapping("/professores")
    public ModelAndView index() {
        List<Professor> professores = professorRepository.findAll();

        ModelAndView modelAndView = new ModelAndView("professores/index");
        modelAndView.addObject("professores", professores);

        return modelAndView;
    }

    @GetMapping("/professor/new")
    public ModelAndView nnew() {
        ModelAndView modelAndView = new ModelAndView("professores/new");
        modelAndView.addObject("statusProfessor", StatusProfessor.values());
        return modelAndView;
    }

    /*
        Por que não utilizar a classe final (entidade) como parâmetro?
        - Web Parameter Tampering: o usuário pode passar um atributo sensível, como salário, na requisição. Para evitar
        isso, utilizamos o padrão DTO (Data Transfer Object)
     */
    @PostMapping("/professores")
    public String create(RequisicaoNovoProfessor requisicaoNovoProfessor) {
        Professor professor = requisicaoNovoProfessor.toProfessor();
        professorRepository.save(professor);
        return "redirect:/professores";
    }

}
