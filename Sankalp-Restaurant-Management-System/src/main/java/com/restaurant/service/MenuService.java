package com.restaurant.service;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.restaurant.model.Menu;
import com.restaurant.repository.MenuRepository;
import com.restaurant.service.storage.MediaStorageService;

@Service
public class MenuService {

	private static final Logger logger = LoggerFactory.getLogger(MenuService.class);

	private final MenuRepository repository;

	@Value("${file.upload-dir:${user.home}/rms-uploads}")
	private String uploadDir;

	@Autowired(required = false)
	private MediaStorageService mediaStorageService;

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

		if (image != null && !image.isEmpty()) {
			String fileName = saveImage(image);
			menu.setImage(fileName);
		} else {
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
			String originalName = file.getOriginalFilename() != null ? file.getOriginalFilename() : "menu.jpg";
			String cleanName = originalName.replaceAll("[^a-zA-Z0-9._-]", "_");
			String fileName = UUID.randomUUID() + "_" + cleanName;

			if (mediaStorageService != null) {
				return mediaStorageService.store(fileName, file.getBytes(), file.getContentType());
			}

			File folder = new File(uploadDir);
			if (!folder.exists()) {
				folder.mkdirs();
			}

			File destination = new File(folder, fileName);
			file.transferTo(destination);
			logger.info("Menu image saved locally at: {}", destination.getAbsolutePath());
			return fileName;
		} catch (Exception e) {
			throw new RuntimeException("Failed to save menu image: " + e.getMessage(), e);
		}
	}
}
