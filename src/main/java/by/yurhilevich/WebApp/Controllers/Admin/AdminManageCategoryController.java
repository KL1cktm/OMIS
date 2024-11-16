package by.yurhilevich.WebApp.Controllers.Admin;

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
public class AdminManageCategoryController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    ItemService itemService;

    @GetMapping("/admin_category")
    public String adminCategory(Model model) {
        addAttributes(model);
        return "admin/admin_category";
    }

    @PostMapping("/manage_category")
    public String manageCategory(@RequestParam("category") Long id,@RequestParam("new_category_name") String name, Model model) {
        if (categoryService.updateCategory(id, name)) {
            return "redirect:/admin/admin_category?success_category=true";
        }
        return "redirect:/admin/admin_category?failed_category=true";
    }

    @PostMapping("manage_create")
    public String createNewCategory(@RequestParam("selectedProducts") List<Long> selectedProductIds,
                                    @RequestParam("categoryName") String name, Model model) {
        if (categoryService.createCategory(selectedProductIds, name)) {
            return "redirect:/admin/admin_category?add_category=true";
        }
        return "redirect:/admin/admin_category?failed_add_category=true";
    }

    public void addAttributes(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("products", itemService.getAllItems());
    }
}
