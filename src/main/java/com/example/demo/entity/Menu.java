package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "menu")
@Data
public class Menu {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	private String menuName;

	@OneToMany(mappedBy = "menu", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Category> categories;

}
