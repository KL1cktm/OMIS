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

@Service
@Transactional
public class ItemServiceImpl implements ItemService {
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public Item getItemById(long id) {
        return itemRepository.findById(id).get();
    }

    @Override
    public Long getItem(){
        return itemRepository.findAll().get(0).getId();
    }

    @Override
    public void deleteItemById(long id) {
        if(itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
        }
    }

    @Override
    public void addItem(String itemName, String itemDefinition, Double price, List<String> categories) {
        Set<Category> currentCategories = new HashSet<>();
        for (String category : categories){
            if (categoryRepository.existsByName(category)) {
                currentCategories.add(categoryRepository.findByName(category).get());
            }
        }
        Item item = new Item();
        item.setName(itemName);
        item.setDefinition(itemDefinition);
        item.setPrice(price);
        item.setCategories(currentCategories);
        itemRepository.save(item);
    }
}
