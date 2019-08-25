package com.trilogyed.adminservice.controller;

import com.trilogyed.adminservice.model.Inventory;
import com.trilogyed.adminservice.service.AdminServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/admin/inventory")
public class InventoryServiceController {

    @Autowired
    AdminServiceLayer service;

    public InventoryServiceController(AdminServiceLayer service) {
        this.service = service;
    }
    //add inventory
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Inventory createInventory(@RequestBody @Valid Inventory inventory, Principal principal) {
        return service.addInventory(inventory);
    }
    //get inventory by id
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Inventory getInventoryById(@PathVariable int id, Principal principal) {
        return service.findInventorybyId(id);
    }
    //get list of inventory
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Inventory> getAllinventories( Principal principal) {
        return service.findAllInventories();
    }
    //update inventory
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateInventory(@PathVariable int id, @RequestBody @Valid Inventory inventory, Principal principal) throws Exception{
        if(inventory.getId() == 0) {
            inventory.setId(id);
            if(id != inventory.getId()) {
                throw new IllegalArgumentException("Inventory ID on path must match the ID in the inventory object");
            }
            service.updateInventory( id, inventory);
        }
    }
    //delete inventory by id
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInventory(@PathVariable int id, Principal principal) {
        service.deleteInventory(id);
    }
}
