package org.lessons.java.spring_la_mia_pizzeria_crud.controller;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.Sale;
import org.lessons.java.spring_la_mia_pizzeria_crud.repo.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/sales")
public class SaleController {
    @Autowired
    private SaleRepository repository;

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("sale") Sale sale, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/sales/create";
        }

        repository.save(sale);

        return "redirect:/pizzas/" + sale.getPizza().getId();
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        boolean edit = true;
        model.addAttribute("sale", repository.findById(id).get());
        model.addAttribute("edit", edit);
        return "sales/form";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("sale") Sale sale, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/sale/form";
        }

        repository.save(sale);

        return "redirect:/pizzas/" + sale.getPizza().getId();
    }

    @PostMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        Integer utilId = repository.findById(id).get().getPizza().getId();
        repository.deleteById(id);
        
        return "redirect:/pizzas/" + utilId;
    }
}
