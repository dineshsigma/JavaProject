package com.example.demo.controller;


//✅ DTO
import com.example.demo.dto.MenuItemRequestDTO;

//✅ Service
import com.example.demo.service.MenuItemService;

//✅ ApiResponse
import com.example.demo.entity.ApiResponse;

//✅ Spring
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//✅ HTTP
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

//✅ Lombok
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/menu-items")
@RequiredArgsConstructor
public class MenuItemController {

	private final MenuItemService service;

	@PostMapping
	public ResponseEntity<ApiResponse<String>> create(@RequestBody MenuItemRequestDTO dto) {

		try {
			String result = service.createMenuItem(dto);

			return ResponseEntity.ok(new ApiResponse<>(200, result, null));

		} catch (Exception ex) {

			return ResponseEntity.status(500).body(new ApiResponse<>(400, ex.getMessage(), null));
		}
	}

}
