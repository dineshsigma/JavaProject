package com.example.demo.mapper;

import com.example.demo.dto.*;
import com.example.demo.entity.*;

import java.util.List;
import java.util.stream.Collectors;

public class MenuMapper {

	public static MenuResponseDTO toDTO(Menu menu) {

		MenuResponseDTO dto = new MenuResponseDTO();
		dto.setId(menu.getId());
		dto.setMenuName(menu.getMenuName());

		List<CategoryDTO> categories = menu.getCategories().stream().map(cat -> {
			CategoryDTO c = new CategoryDTO();
			c.setId(cat.getId());
			c.setCategoryName(cat.getCategoryName());
			return c;
		}).collect(Collectors.toList());

		dto.setCategories(categories);

		return dto;
	}

}
