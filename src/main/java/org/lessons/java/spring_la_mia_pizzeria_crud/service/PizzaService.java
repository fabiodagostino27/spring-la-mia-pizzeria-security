package org.lessons.java.spring_la_mia_pizzeria_crud.service;

import java.util.List;
import java.util.Optional;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_crud.model.Sale;
import org.lessons.java.spring_la_mia_pizzeria_crud.repo.PizzaRepository;
import org.lessons.java.spring_la_mia_pizzeria_crud.repo.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PizzaService {
    @Autowired
    private final SaleRepository saleRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    PizzaService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public List<Pizza> findAll(String name) {
        if (name != null && !name.trim().isEmpty()) {
            return pizzaRepository.findByNameContainingIgnoreCase(name);
        }

        return pizzaRepository.findAll();
    }

    public Optional<Pizza> findById(Integer id) {
        return pizzaRepository.findById(id);
    }

    public Pizza create(Pizza pizza) {
        return pizzaRepository.save(pizza);
    }

    public Pizza update(Pizza pizza) {
        return pizzaRepository.save(pizza);
    }

    public void deleteById(Integer id) {
        for (Sale sale : this.findById(id).get().getSales()) {
            saleRepository.delete(sale);
        }

        pizzaRepository.deleteById(id);
    }
}
