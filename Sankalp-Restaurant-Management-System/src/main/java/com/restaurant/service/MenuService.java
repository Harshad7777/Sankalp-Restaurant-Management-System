package com.restaurant.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.restaurant.model.Menu;
import com.restaurant.repository.MenuRepository;

@Service
public class MenuService {

	private final MenuRepository repository;

	@Value("${file.upload-dir}")
	private String uploadDir;

	public MenuService(MenuRepository repository) {
		this.repository = repository;
	}

	// ==========================
	// Add Menu
	// ==========================
	public String addMenu(String itemName, Integer categoryId, String description, Double price, String status,
			MultipartFile image) {

		if (repository.existsByName(itemName)) {
			return "Menu Item Already Exists";
		}

		Menu menu = new Menu();

		menu.setItemName(itemName);
		menu.setCategoryId(categoryId);
		menu.setDescription(description);
		menu.setPrice(price);
		menu.setStatus(status);
		if (image != null) {

		    System.out.println("Image Object : " + image);
		    System.out.println("Image Empty  : " + image.isEmpty());
		    System.out.println("Image Name   : " + image.getOriginalFilename());

		}

		if (image != null && !image.isEmpty()) {

		    String fileName = saveImage(image);

		    System.out.println("Saved File Name : " + fileName);

		    menu.setImage(fileName);

		} else {

		    System.out.println("No Image Received");

		    menu.setImage(null);

		}

		int value = repository.save(menu);

		return value > 0 ? "Menu Added Successfully" : "Menu Not Added";
	}

	// ==========================
	// Update Menu
	// ==========================
	public String updateMenu(int id, String itemName, Integer categoryId, String description, Double price,
			String status, MultipartFile image) {

		Menu menu = repository.findById(id);

		if (menu == null) {
			return "Menu Not Found";
		}

		menu.setItemName(itemName);
		menu.setCategoryId(categoryId);
		menu.setDescription(description);
		menu.setPrice(price);
		menu.setStatus(status);

		if (image != null && !image.isEmpty()) {

			String fileName = saveImage(image);

			menu.setImage(fileName);
		}

		int value = repository.update(menu);

		return value > 0 ? "Menu Updated Successfully" : "Menu Not Updated";
	}

	// ==========================
	// Delete Menu
	// ==========================
	public String deleteMenu(int id) {

		int value = repository.delete(id);

		return value > 0 ? "Menu Deleted Successfully" : "Menu Not Deleted";
	}

	// ==========================
	// Get Menu
	// ==========================
	public Menu getMenu(int id) {
		return repository.findById(id);
	}

	// ==========================
	// Get All Menu
	// ==========================
	public List<Menu> getAllMenu() {
		return getAllMenu(null);
	}

	public List<Menu> getAllMenu(Integer restaurantId) {
		return repository.findAll(restaurantId);
	}

	// ==========================
	// Get Menu By Category
	// ==========================
	public List<Menu> getCategoryMenu(int categoryId) {
		return repository.findByCategory(categoryId);
	}

	public com.restaurant.dto.PageResponse<Menu> getMenuFilteredPage(com.restaurant.dto.PageRequestDTO req) {
		List<Menu> content = repository.getMenuFilteredPage(req);
		long total = repository.countMenuFiltered(req);
		return new com.restaurant.dto.PageResponse<>(content, req.getPage(), req.getSize(), total);
	}

	// ==========================
	// Save Image
	// ==========================
	private String saveImage(MultipartFile file) {

	    try {

	        System.out.println("Upload Directory : " + uploadDir);

	        File folder = new File(uploadDir);

	        if (!folder.exists()) {

	            boolean created = folder.mkdirs();

	            System.out.println("Folder Created : " + created);
	        }

	        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

	        File destination = new File(folder, fileName);

	        System.out.println("Saving To : " + destination.getAbsolutePath());

	        file.transferTo(destination);

	        System.out.println("File Exists : " + destination.exists());

	        System.out.println("File Size : " + destination.length());

	        return fileName;

	    } catch (Exception e) {

	        e.printStackTrace();

	        throw new RuntimeException(e);

	    }

	}
}