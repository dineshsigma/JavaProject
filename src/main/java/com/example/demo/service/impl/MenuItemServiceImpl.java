package com.example.demo.service.impl;

// ✅ Entity Imports
import com.example.demo.entity.Menu;
import com.example.demo.entity.Category;
import com.example.demo.entity.MenuItem;

// ✅ DTO Import
import com.example.demo.dto.MenuItemRequestDTO;

// ✅ Repository Imports
import com.example.demo.repository.MenuRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.MenuItemRepository;

// ✅ Service Interface
import com.example.demo.service.MenuItemService;

// ✅ Spring Imports
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// ✅ Lombok
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;
    private final MenuItemRepository menuItemRepository;

    @Override
    @Transactional
    public String createMenuItem(MenuItemRequestDTO dto) {

        // ✅ Step 1: Get or Create Menu
        Menu menu = menuRepository.findByMenuName(dto.getMenuName())
                .orElseGet(() -> {
                    Menu m = new Menu();
                    m.setMenuName(dto.getMenuName());
                    return menuRepository.save(m);
                });

        // ✅ Step 2: Get or Create Category
        Category category = categoryRepository.findByCategoryName(dto.getCategoryName())
                .orElseGet(() -> {
                    Category c = new Category();
                    c.setCategoryName(dto.getCategoryName());
                    return categoryRepository.save(c);
                });

        // ✅ Step 3: Create MenuItem
        if (dto.getPrice() < 0) {
            throw new RuntimeException("Price cannot be negative");
        }

        MenuItem item = new MenuItem();
        item.setMenu(menu);
        item.setCategory(category);
        item.setPrice(dto.getPrice());
        item.setStatus(dto.getStatus());

        menuItemRepository.save(item);

        return "Menu Item Created Successfully";
    }
}