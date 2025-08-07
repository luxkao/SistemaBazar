package com.luxkao.bazar.controllers;

import com.luxkao.bazar.model.entities.OrgaoFiscalizador;
import com.luxkao.bazar.model.repositories.RepositoryFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;

@Controller
@RequestMapping("/orgaos-fiscalizadores")
public class OrgaoFiscalizadorController {
    private final RepositoryFacade facade = RepositoryFacade.getCurrentInstance();

    @GetMapping
    public String index(Model model) {
        try {
            model.addAttribute("orgaos", facade.readAllOrgaosFiscalizadores());
            return "orgao_fiscalizador/index";
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("error", "Não foi possível carregar os dados.");
            return "error";
        }
    }

    @GetMapping("/cadastro")
    public String showCadastroForm(Model model) {
        model.addAttribute("orgao", new OrgaoFiscalizador());
        return "orgao_fiscalizador/form";
    }

    @PostMapping("/cadastro")
    public String cadastrar(@ModelAttribute OrgaoFiscalizador orgao, RedirectAttributes redirectAttributes) {
        try {
            facade.createOrgaoFiscalizador(orgao);
            redirectAttributes.addFlashAttribute("success", "Órgão Fiscalizador cadastrado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Erro ao cadastrar Órgão Fiscalizador!");
        }
        return "redirect:/orgaos-fiscalizadores";
    }

    @GetMapping("/editar/{id}")
    public String showEditarForm(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes) {
        try {
            OrgaoFiscalizador orgao = facade.readOrgaoFiscalizador(id);
            if (orgao != null) {
                model.addAttribute("orgao", orgao);
                return "orgao_fiscalizador/form";
            } else {
                redirectAttributes.addFlashAttribute("error", "Órgão Fiscalizador não encontrado.");
                return "redirect:/orgaos-fiscalizadores";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Erro ao carregar dados para edição.");
            return "redirect:/orgaos-fiscalizadores";
        }
    }

    @PostMapping("/editar")
    public String editar(@ModelAttribute OrgaoFiscalizador orgao, RedirectAttributes redirectAttributes) {
        try {
            facade.updateOrgaoFiscalizador(orgao);
            redirectAttributes.addFlashAttribute("success", "Órgão Fiscalizador atualizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Erro ao atualizar Órgão Fiscalizador!");
        }
        return "redirect:/orgaos-fiscalizadores";
    }

    @PostMapping("/deletar/{id}")
    public String deletar(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        try {
            facade.deleteOrgaoFiscalizador(id);
            redirectAttributes.addFlashAttribute("success", "Órgão Fiscalizador deletado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Erro ao deletar Órgão Fiscalizador!");
        }
        return "redirect:/orgaos-fiscalizadores";
    }
}