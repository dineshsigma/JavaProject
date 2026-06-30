package com.example.demo.dto;
import lombok.Data;
import java.util.List;

@Data
public class MenuItemRequestDTO {

	private String menuName;
	private String categoryName;
	private Double price;
	private String status;

}
