package com.trilogyed.inventoryservice.controller;

import com.trilogyed.inventoryservice.model.Inventory;
import com.trilogyed.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryServiceController {

    @Autowired
    InventoryService service;

    public InventoryServiceController(InventoryService service) {
        this.service = service;
    }
    //add inventory
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Inventory createInventory(@RequestBody @Valid Inventory inventory) {
        return service.addInventory(inventory);
    }
    //get inventory by id
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Inventory getInventoryById(@PathVariable int id) {
        return service.findInventoryById(id);
    }
    //get list of inventory
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Inventory> getAllinventories() {
       return service.findAllInventories();
    }
    //update inventory
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateInventory(@PathVariable int id, @RequestBody @Valid Inventory inventory) throws Exception{
        if(inventory.getId() == 0) {
            inventory.setId(id);
            if(id != inventory.getId()) {
                throw new IllegalArgumentException("Inventory ID on path must match the ID in the inventory object");
            }
            service.updateInventory(inventory);
        }
    }
    //delete inventory by id
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInventory(@PathVariable int id) {
        service.deleteInventory(id);
    }



}
