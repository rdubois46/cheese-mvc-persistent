package org.launchcode.controllers;

import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.launchcode.models.forms.AddMenuItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private CheeseDao cheeseDao;

    @RequestMapping(value = "")
    public String index(Model model) {

        Iterable<Menu> menus = menuDao.findAll();
        model.addAttribute("menus", menus);
        model.addAttribute("title", "Menus");

        return "menu/index";

    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {

        Menu menu = new Menu();
        model.addAttribute("menu", menu);
        model.addAttribute("title", "Add a Menu");

        return "menu/add";

    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid Menu newMenu, Errors errors) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add a Menu");
            return "menu/add";
        }
        menuDao.save(newMenu);
        return "redirect:/menu/view/" + newMenu.getId();
    }

    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    public String viewMenu(Model model, @PathVariable int id) {

        Menu menu = menuDao.findOne(id);

        model.addAttribute("menu", menu);
        model.addAttribute("title", "Menu: " + menu.getName());

        return "menu/view";

    }

    @RequestMapping(value = "add-item/{id}", method = RequestMethod.GET)
    public String addItem(Model model, @PathVariable int id) {

        Menu menu = menuDao.findOne(id);
        Iterable<Cheese> cheeses = cheeseDao.findAll();
        AddMenuItemForm form = new AddMenuItemForm(menu, cheeses);

        model.addAttribute("title", "Add Cheese to Menu: " + menu.getName());
        model.addAttribute("form", form);

        return "menu/add-item";
    }

    @RequestMapping(value = "add-item", method = RequestMethod.POST)
    public String addItem(Model model, @ModelAttribute @Valid AddMenuItemForm form, Errors errors, @RequestParam int cheeseId, @RequestParam int menuId) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Cheese to Menu: " + form.getMenu().getName());
            model.addAttribute("form", form);
            return "menu/add-item";
        }

        Cheese addedCheese = cheeseDao.findOne(cheeseId);
        Menu updatedMenu = menuDao.findOne(menuId);
        updatedMenu.addItem(addedCheese);
        menuDao.save(updatedMenu);

        return "redirect:/menu/view/" + menuId;
    }


}
