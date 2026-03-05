package com.microservices.item.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/items")
public class ItemController {
    // Simple in-memory list (no database needed)
    private List<String> items = new ArrayList<>(List.of("Book", "Laptop", "Phone"));

    @GetMapping
    public List<String> getItems() {
        return items;
    }

    @PostMapping
    public ResponseEntity<String> addItem(@RequestBody Map<String, String> request) {
        String itemName = request.get("name");
        if (itemName == null || itemName.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Item name is required");
        }
        items.add(itemName);
        return ResponseEntity.status(HttpStatus.CREATED).body("Item added: " + itemName);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getItem(@PathVariable int id) {
        if (id < 0 || id >= items.size())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(items.get(id));
    }
}
