package com.example.demo.controller;

import com.example.demo.dto.MenuResponseDTO;
import com.example.demo.service.MenuService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.example.demo.entity.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuController {

	private final MenuService menuService;

	@GetMapping("/with-categories")
	public ResponseEntity<ApiResponse<List<MenuResponseDTO>>> getMenusWithCategories() {
		try {
			List<MenuResponseDTO> menus = menuService.getAllMenus();

			if (menus == null || menus.isEmpty()) {
				return ResponseEntity.ok(new ApiResponse<>(201, "No menu data found", menus));
			}

			return ResponseEntity.ok(new ApiResponse<>(200, "Menus fetched successfully", menus));

		} catch (Exception ex) {

			return ResponseEntity.status(500).body(
					new ApiResponse<List<MenuResponseDTO>>(500, "Something went wrong: " + ex.getMessage(), null));

		}

	}

}
