package by.yurhilevich.WebApp.service;

import by.yurhilevich.WebApp.models.Category;
import by.yurhilevich.WebApp.models.Item;
import by.yurhilevich.WebApp.repository.CategoryRepository;
import by.yurhilevich.WebApp.repository.ItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ItemRepository itemRepository;

    @Override
    public List<String> getAllCategoriesInString() {
        List<Category> categories = categoryRepository.findAll();
        List<String> categoriesNames = categories.stream()
                .map(Category::getName)
                .collect(Collectors.toList());
        return categoriesNames;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Item> loadItemsByCategory(String categoryName) {
        List<Item> items = new ArrayList<>();
        if (categoryName.equals("Все")|| !categoryRepository.existsByName(categoryName)) {
            items = itemRepository.findAll();
        } else {
            System.out.println(categoryRepository.findByName(categoryName).get().getItems());
            items = itemRepository.findAllByCategories(categoryRepository.findByName(categoryName).get());
//            categoryRepository.findByName(categoryName).get().getItems();
//            System.out.println(items.toString());
        }
        System.out.println(items.size());
        return items;
    }

    @Override
    public boolean updateCategory(Long id, String categoryName) {
        if (categoryRepository.existsByName(categoryName) && !categoryRepository.existsById(id)) {
            return false;
        }
        Category category = categoryRepository.findById(id).get();
        category.setName(categoryName);
        categoryRepository.save(category);
        return true;
    }

    @Override
    public boolean createCategory(List<Long> ids, String categoryName) {
        if (categoryRepository.existsByName(categoryName)) {
            return false;
        }
        Set<Item> items = new HashSet<>();
        for (Long id : ids) {
            if (itemRepository.existsById(id)) {
                items.add(itemRepository.findById(id).get());
            }
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setItems(items);
        categoryRepository.save(category);
        return true;
    }

    @Override
    public Category getCategoryByName(String categoryName) {
        return categoryRepository.findByName(categoryName).get();
    }
}
