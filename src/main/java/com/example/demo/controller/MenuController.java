package com.example.demo.controller;

import com.example.demo.dto.MenuResponseDTO;
import com.example.demo.service.MenuService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuController {

	private final MenuService menuService;

	@GetMapping("/with-categories")
	public List<MenuResponseDTO> getMenusWithCategories() {
		return menuService.getAllMenus();
	}

}
