package com.example.demo.dto;

import lombok.Data;
import java.util.List;

@Data
public class MenuResponseDTO {

	private String id;
	private String menuName;
	private List<CategoryDTO> categories;

}
