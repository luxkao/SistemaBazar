package com.luxkao.bazar.controllers;

import com.luxkao.bazar.model.entities.Produto;
import com.luxkao.bazar.model.repositories.RepositoryFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.sql.SQLException;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    private final RepositoryFacade facade = RepositoryFacade.getCurrentInstance();

    @GetMapping
    public String index(Model model) {
        try {
            model.addAttribute("produtos", facade.readAllProdutos());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "produto/index";
    }

    @GetMapping("/cadastro")
    public String showCadastroForm(Model model) {
        model.addAttribute("produto", new Produto());
        return "produto/form";
    }

    @PostMapping("/cadastro")
    public String cadastrar(@ModelAttribute Produto produto, RedirectAttributes redirectAttributes) {
        try {
            facade.createProduto(produto);
            redirectAttributes.addFlashAttribute("success", "Produto cadastrado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Erro ao cadastrar Produto!");
        }
        return "redirect:/produtos";
    }

    @GetMapping("/editar/{codigo}")
    public String showEditarForm(@PathVariable("codigo") int codigo, Model model, RedirectAttributes redirectAttributes) {
        try {
            Produto produto = facade.readProduto(codigo);
            if (produto != null) {
                model.addAttribute("produto", produto);
                return "produto/form";
            }
            redirectAttributes.addFlashAttribute("error", "Produto não encontrado.");
        } catch (SQLException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Erro ao carregar dados para edição.");
        }
        return "redirect:/produtos";
    }

    @PostMapping("/editar")
    public String editar(@ModelAttribute Produto produto, RedirectAttributes redirectAttributes) {
        try {
            facade.updateProduto(produto);
            redirectAttributes.addFlashAttribute("success", "Produto atualizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Erro ao atualizar Produto!");
        }
        return "redirect:/produtos";
    }

    @PostMapping("/deletar/{codigo}")
    public String deletar(@PathVariable("codigo") int codigo, RedirectAttributes redirectAttributes) {
        try {
            facade.deleteProduto(codigo);
            redirectAttributes.addFlashAttribute("success", "Produto deletado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Erro ao deletar Produto!");
        }
        return "redirect:/produtos";
    }
}