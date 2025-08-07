package com.luxkao.bazar.controllers;

import com.luxkao.bazar.model.entities.OrgaoDonatario;
import com.luxkao.bazar.model.repositories.RepositoryFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.sql.SQLException;

@Controller
@RequestMapping("/orgaos-donatarios")
public class OrgaoDonatarioController {

    private final RepositoryFacade facade = RepositoryFacade.getCurrentInstance();

    @GetMapping
    public String index(Model model) {
        try {
            model.addAttribute("orgaos", facade.readAllOrgaosDonatarios());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "orgao_donatario/index";
    }

    @GetMapping("/cadastro")
    public String showCadastroForm(Model model) {
        model.addAttribute("orgao", new OrgaoDonatario());
        return "orgao_donatario/form";
    }

    @PostMapping("/cadastro")
    public String cadastrar(@ModelAttribute OrgaoDonatario orgao, RedirectAttributes redirectAttributes) {
        try {
            facade.createOrgaoDonatario(orgao);
            redirectAttributes.addFlashAttribute("success", "Órgão Donatário cadastrado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Erro ao cadastrar Órgão Donatário!");
        }
        return "redirect:/orgaos-donatarios";
    }

    @GetMapping("/editar/{id}")
    public String showEditarForm(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes) {
        try {
            OrgaoDonatario orgao = facade.readOrgaoDonatario(id);
            if (orgao != null) {
                model.addAttribute("orgao", orgao);
                return "orgao_donatario/form";
            }
            redirectAttributes.addFlashAttribute("error", "Órgão Donatário não encontrado.");
        } catch (SQLException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Erro ao carregar dados para edição.");
        }
        return "redirect:/orgaos-donatarios";
    }

    @PostMapping("/editar")
    public String editar(@ModelAttribute OrgaoDonatario orgao, RedirectAttributes redirectAttributes) {
        try {
            facade.updateOrgaoDonatario(orgao);
            redirectAttributes.addFlashAttribute("success", "Órgão Donatário atualizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Erro ao atualizar Órgão Donatário!");
        }
        return "redirect:/orgaos-donatarios";
    }

    @PostMapping("/deletar/{id}")
    public String deletar(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        try {
            facade.deleteOrgaoDonatario(id);
            redirectAttributes.addFlashAttribute("success", "Órgão Donatário deletado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Erro ao deletar Órgão Donatário!");
        }
        return "redirect:/orgaos-donatarios";
    }
}