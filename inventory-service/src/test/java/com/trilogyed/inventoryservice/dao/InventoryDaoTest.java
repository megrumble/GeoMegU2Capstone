package com.trilogyed.inventoryservice.dao;

import com.trilogyed.inventoryservice.model.Inventory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InventoryDaoTest {

    @Autowired
    InventoryDao inventoryDao;

    @Before
    public void setUp() throws Exception {
        List<Inventory> inventoryList = inventoryDao.getAllInventories();
        inventoryList.stream().forEach(i -> inventoryDao.deleteInventory(i.getId()));
    }

    @Test
    public void createGetInventory() {
        Inventory inventory = new Inventory();
        inventory.setProductId(1);
        inventory.setQuantity(10);
        inventory = inventoryDao.createInventory(inventory);

        Inventory inventory1 = inventoryDao.getInventoryById(inventory.getId());
        assertEquals(inventory, inventory1);
    }

    @Test
    public void deleteInventory() {
        Inventory inventory = new Inventory();
        inventory.setProductId(1);
        inventory.setQuantity(10);
        inventory = inventoryDao.createInventory(inventory);

        inventoryDao.deleteInventory(inventory.getId());
        Inventory inventory1 = inventoryDao.getInventoryById(inventory.getId());
        assertNull(inventory1);

    }

    @Test
    public void getAllInventories() {

        Inventory inventory = new Inventory();
        inventory.setProductId(1);
        inventory.setQuantity(10);
        inventory = inventoryDao.createInventory(inventory);

        inventory = new Inventory();
        inventory.setProductId(12);
        inventory.setQuantity(20);
        inventory = inventoryDao.createInventory(inventory);

        List<Inventory> inventoryList = inventoryDao.getAllInventories();
        assertEquals(inventoryList.size(), 2);

    }

    @Test
    public void updateInventory() {

        Inventory inventory = new Inventory();
        inventory.setId(1);
        inventory.setProductId(1);
        inventory.setQuantity(10);
        inventory = inventoryDao.createInventory(inventory);

        inventory.setQuantity(20);
        inventoryDao.updateInventory(inventory);

        Inventory inventory1 = inventoryDao.getInventoryById(inventory.getId());
        assertEquals(inventory, inventory1);

    }
}
