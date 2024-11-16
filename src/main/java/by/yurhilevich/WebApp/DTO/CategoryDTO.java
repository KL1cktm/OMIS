package by.yurhilevich.WebApp.DTO;

import by.yurhilevich.WebApp.models.Item;
import lombok.Data;

import java.util.List;

@Data
public class CategoryDTO {
    private Long id;
    private String name;
    private String definition;
}
