package com.trilogyed.inventoryservice.service;

import com.trilogyed.inventoryservice.dao.InventoryDao;
import com.trilogyed.inventoryservice.exception.NotFoundException;
import com.trilogyed.inventoryservice.model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class InventoryService {

    private InventoryDao dao;

    @Autowired

    public InventoryService(InventoryDao dao) {
        this.dao = dao;
    }
    //add inventory
    public Inventory addInventory(Inventory inventory){
        return dao.createInventory(inventory);
    }
    //find inventory by id
    public Inventory findInventoryById(int id) throws NotFoundException{
       Inventory inventory = dao.getInventoryById(id);
        if (inventory == null) {
            throw new NotFoundException("There is no inventory with ID " + id);
        }
        return inventory;
    }
    //get a list of all invoices
    public List<Inventory> findAllInventories() {
        return dao.getAllInventories();
    }
    //update inventory
    public void updateInventory(Inventory inventory) {
        dao.updateInventory(inventory);
    }
    //delete inventory
    public void deleteInventory(int id) {
        dao.deleteInventory(id);
    }

}
