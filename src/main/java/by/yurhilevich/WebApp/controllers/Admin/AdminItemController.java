package by.yurhilevich.WebApp.controllers.Admin;

import by.yurhilevich.WebApp.service.CategoryService;
import by.yurhilevich.WebApp.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminItemController {
    @Autowired
    ItemService itemService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/admin_item")
    public String admin_item(Model model) {
        return "admin/admin_item";
    }

    @GetMapping("/admin_item_add")
    public String admin_item_add(Model model) {
        model.addAttribute("categories", categoryService.getAllCategoriesInString());
        return "admin/admin_item_add";
    }

    @PostMapping("/addProduct")
    public String admin_add_item(@RequestParam("name") String itemName,
                                 @RequestParam("definition") String definition,
                                 @RequestParam("price") Double price,
                                 @RequestParam("categories") List<String> categories, Model model) {
        System.out.println("correct add");
        itemService.addItem(itemName, definition, price, categories);
        return "redirect:/admin/admin_item_add";
    }

    @GetMapping("/admin_item_delete")
    public String admin_item_delete(Model model) {
        addAttributes(model);
        return "admin/admin_item_delete";
    }

    @PostMapping("deleteItem")
    public String deleteItem(@RequestParam("itemId") Long id, Model model) {
        itemService.deleteItemById(id);
        return "redirect:/admin/admin_item_delete";
    }

    public void addAttributes(Model model) {
        model.addAttribute("items", itemService.getAllItems());
    }
}
