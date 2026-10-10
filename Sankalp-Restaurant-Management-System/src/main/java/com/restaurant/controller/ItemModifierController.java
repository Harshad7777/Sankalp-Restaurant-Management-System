package com.restaurant.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.model.ItemModifier;
import com.restaurant.model.ModifierOption;
import com.restaurant.service.ItemModifierService;

@RestController
@RequestMapping({"/modifiers", "/api/modifiers"})
@CrossOrigin(origins = "*")
public class ItemModifierController {

    private final ItemModifierService modifierService;

    public ItemModifierController(ItemModifierService modifierService) {
        this.modifierService = modifierService;
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<List<ItemModifier>> getModifiersByItemId(@PathVariable int itemId) {
        return ResponseEntity.ok(modifierService.getModifiersByItemId(itemId));
    }

    @PostMapping
    public ResponseEntity<?> createModifier(@RequestBody ItemModifier modifier) {
        int id = modifierService.saveModifier(modifier);
        return ResponseEntity.ok(Map.of("success", true, "message", "Item modifier created", "modifierId", id));
    }

    @PostMapping("/{modifierId}/option")
    public ResponseEntity<?> createOption(@PathVariable int modifierId, @RequestBody ModifierOption option) {
        int optionId = modifierService.saveOption(modifierId, option);
        return ResponseEntity.ok(Map.of("success", true, "message", "Modifier option added", "optionId", optionId));
    }

    @DeleteMapping("/{modifierId}")
    public ResponseEntity<?> deleteModifier(@PathVariable int modifierId) {
        boolean deleted = modifierService.deleteModifier(modifierId);
        return ResponseEntity.ok(Map.of("success", deleted, "message", deleted ? "Modifier deleted" : "Modifier not found"));
    }
}
