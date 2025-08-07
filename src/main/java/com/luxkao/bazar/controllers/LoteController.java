package com.luxkao.bazar.controllers;

import com.luxkao.bazar.model.entities.Lote;
import com.luxkao.bazar.model.entities.OrgaoDonatario;
import com.luxkao.bazar.model.entities.OrgaoFiscalizador;
import com.luxkao.bazar.model.entities.Produto;
import com.luxkao.bazar.model.repositories.RepositoryFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/lotes")
public class LoteController {

    private final RepositoryFacade facade = RepositoryFacade.getCurrentInstance();

    @GetMapping
    public String index(Model model){
        try {
            model.addAttribute("lotes", facade.readAllLotes());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "lote/index";
    }

    @GetMapping("/cadastro")
    public String showCadastroForm(Model model){
        try {
            Lote lote = new Lote();
            lote.setOrgaoFiscalizador(new OrgaoFiscalizador());
            lote.setOrgaoDonatario(new OrgaoDonatario());

            model.addAttribute("lote", lote);
            model.addAttribute("orgaosFiscalizadores", facade.readAllOrgaosFiscalizadores());
            model.addAttribute("orgaosDonatarios", facade.readAllOrgaosDonatarios());
            model.addAttribute("produtos", facade.readAllAvailableProdutos());

        } catch (SQLException e) {
            e.printStackTrace();
            return "redirect:/lotes";
        }
        return "lote/form";
    }

    @PostMapping("/cadastro")
    public String cadastrar(@ModelAttribute Lote lote, RedirectAttributes redirectAttributes) {
        try {
            facade.createLote(lote);
            redirectAttributes.addFlashAttribute("success", "Lote cadastrado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Erro ao cadastrar Lote!");
        }
        return "redirect:/lotes";
    }
}