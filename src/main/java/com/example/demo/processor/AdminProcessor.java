package com.example.demo.processor;

import org.springframework.stereotype.Component;
import com.example.demo.entity.Admin;
import org.springframework.batch.item.ItemProcessor;

@Component
public class AdminProcessor implements ItemProcessor<Admin, Admin> {

	@Override
	public Admin process(Admin item) {

		item.setEmail(item.getEmail().toLowerCase());

		return item;
	}

}
