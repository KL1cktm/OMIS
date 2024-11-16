package by.yurhilevich.WebApp.service;

import by.yurhilevich.WebApp.models.Category;
import by.yurhilevich.WebApp.models.Item;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryService {
    List<String> getAllCategoriesInString();
    List<Item> loadItemsByCategory(String category);
    List<Category> getAllCategories();
    boolean updateCategory(Long id, String categoryName);
    boolean createCategory(List<Long> ids, String categoryName);
    Category getCategoryByName(String categoryName);
}
