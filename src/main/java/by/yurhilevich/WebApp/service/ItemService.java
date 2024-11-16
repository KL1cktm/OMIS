package by.yurhilevich.WebApp.service;

import by.yurhilevich.WebApp.models.Item;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface ItemService {
    List<Item> getAllItems();
    Item getItemById(long id);
    Long getItem();
    void deleteItemById(long id);
    void addItem(String itemName, String itemDefinition, Double price, List<String> categories);

}
