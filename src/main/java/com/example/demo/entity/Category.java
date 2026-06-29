package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;



@Entity
@Table(name = "category")
@Data
public class Category {
	
	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String categoryName;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;


}
