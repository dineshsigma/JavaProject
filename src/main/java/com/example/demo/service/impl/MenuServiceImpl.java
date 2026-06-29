package com.example.demo.service.impl;

import com.example.demo.dto.MenuResponseDTO;
import com.example.demo.entity.Menu;
import com.example.demo.mapper.MenuMapper;
import com.example.demo.repository.MenuRepository;
import com.example.demo.service.MenuService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

	private final MenuRepository menuRepository;

	@Override
	public List<MenuResponseDTO> getAllMenus() {

		// ✅ USING JOIN FETCH QUERY
		List<Menu> menus = menuRepository.findAllWithCategories();

		return menus.stream().map(MenuMapper::toDTO).collect(Collectors.toList());
	}

}
