package com.kenyajug.getfruity.web;

import com.kenyajug.getfruity.database.entities.Fruit;
import com.kenyajug.getfruity.database.repository.FruitsRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FruitsWebController {
    private final FruitsRepository fruitsRepository;
    public FruitsWebController(FruitsRepository fruitsRepository) {
        this.fruitsRepository = fruitsRepository;
    }
    @GetMapping("/")
    public String welcomePage(Model model){
        var fruits = fruitsRepository.findAll();
        model.addAttribute("fruits",fruits);
        return "welcome";
    }
    @GetMapping("/fruits/add")
    public String loadFruitsForm(Model model){
        var emptyFruit = new Fruit();
        model.addAttribute("fruit", emptyFruit);
        return "add-fruit";
    }
    @PostMapping("/fruits/add")
    public String saveNewFruit(@ModelAttribute Fruit newFruit){
        fruitsRepository.save(newFruit);
        return "redirect:/";
    }
}
