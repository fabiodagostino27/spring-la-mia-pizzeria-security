package org.lessons.java.spring_la_mia_pizzeria_crud.controller;

import java.util.List;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_crud.model.Sale;
import org.lessons.java.spring_la_mia_pizzeria_crud.repo.IngredientRepository;
import org.lessons.java.spring_la_mia_pizzeria_crud.repo.PizzaRepository;
import org.lessons.java.spring_la_mia_pizzeria_crud.repo.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    @Autowired
    private PizzaRepository repository;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @GetMapping
    public String pizzasIndex(Model model) {
        List<Pizza> pizzas = repository.findAll();
        model.addAttribute("pizzas", pizzas);
        return "pizzas/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        Pizza pizza = repository.findById(id).get();
        model.addAttribute(pizza);
        return "pizzas/show";
    }

    @GetMapping("/search")
    public String search(@RequestParam("name") String name, Model model) {
        List<Pizza> pizzas = repository.findByNameContainingIgnoreCase(name);
        model.addAttribute("pizzas", pizzas);
        return "pizzas/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());
        model.addAttribute("ingredients", ingredientRepository.findAll());
        return "pizzas/form";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza pizza, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredients", ingredientRepository.findAll());
            return "/pizzas/form";
        }

        repository.save(pizza);

        return "redirect:/pizzas";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        boolean edit = true;
        model.addAttribute("edit", edit);
        model.addAttribute("ingredients", ingredientRepository.findAll());
        model.addAttribute("pizza", repository.findById(id).get());
        return "pizzas/form";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("pizza") Pizza pizza, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredients", ingredientRepository.findAll());
            return "/pizzas/form";
        }

        repository.save(pizza);

        return "redirect:/pizzas";
    }

    @PostMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        Pizza pizza = repository.findById(id).get();

        for (Sale sale : pizza.getSales()) {
            saleRepository.delete(sale);
        }

        repository.delete(pizza);
        
        return "redirect:/pizzas";
    }

    @GetMapping("/{id}/sale")
    public String sale(@PathVariable("id") Integer id, Model model) {
        Sale sale = new Sale();
        sale.setPizza(repository.findById(id).get());
        model.addAttribute("sale", sale);
        return "/sales/form";
    }
    
}
