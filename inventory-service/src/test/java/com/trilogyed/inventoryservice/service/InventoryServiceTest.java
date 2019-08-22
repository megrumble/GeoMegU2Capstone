package com.trilogyed.inventoryservice.service;

import com.trilogyed.inventoryservice.dao.InventoryDao;
import com.trilogyed.inventoryservice.exception.NotFoundException;
import com.trilogyed.inventoryservice.model.Inventory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class InventoryServiceTest {
    @InjectMocks
    InventoryService inventoryService;
    @Mock
    InventoryDao inventoryDao;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        setUpInventoryDaoMock();
    }

    @Test
    public void addFindInventory() {
        Inventory inventory = new Inventory();
        inventory.setProductId(1);
        inventory.setQuantity(10);
        //test add
        inventory = inventoryService.addInventory(inventory);
        //test get 1
        Inventory fromService = inventoryService.findInventoryById(inventory.getId());
        assertEquals(inventory, fromService);

    }
    //check find all
    @Test
    public void findAllInventories() {
        List<Inventory> inventoryList = new ArrayList<>();

        Inventory inventory = new Inventory();
        inventory.setId(1);
        inventory.setProductId(1);
        inventory.setQuantity(10);
        inventoryList.add(inventory);

        Inventory inventory1 = new Inventory();
        inventory1.setId(2);
        inventory1.setProductId(2);
        inventory1.setQuantity(20);
        inventoryList.add(inventory1);

        List<Inventory> inventoryList1 = inventoryService.findAllInventories();
        assertEquals(inventoryList, inventoryList1);

    }
    //check update
    @Test
    public void updateInventory() {
        Inventory inventory = new Inventory();
        inventory.setProductId(1);
        inventory.setQuantity(10);
        inventory = inventoryService.addInventory(inventory);

        inventory.setQuantity(20);
        inventoryService.updateInventory(inventory);
        Inventory inventory1 = new Inventory();
        inventory1.setId(inventory.getId());
        inventory1.setProductId(inventory.getProductId());
        inventory1.setQuantity(inventory.getQuantity());
        verify(inventoryDao).updateInventory(inventory1);
    }
    //check delete
    @Test
    public void deleteInventory() {
        Inventory inventory = new Inventory();
        inventory.setProductId(1);
        inventory.setQuantity(10);
        inventory = inventoryService.addInventory(inventory);

        inventoryService.deleteInventory(inventory.getId());
        verify(inventoryDao).deleteInventory(inventory.getId());

    }

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void shouldThrowRuntimeExceptionWhenProductIsNull() throws Exception {
        int id = 4;
        expectedEx.expect(NotFoundException.class);
        expectedEx.expectMessage("There is no inventory with ID " + id);
        // do something that should throw the exception...
        inventoryService.findInventoryById(id);
    }

    private void setUpInventoryDaoMock() {
        Inventory inventory = new Inventory();
        inventory.setId(1);
        inventory.setProductId(1);
        inventory.setQuantity(10);

        Inventory inventory1 = new Inventory();
        inventory1.setProductId(1);
        inventory1.setQuantity(10);

        Inventory inventory2 = new Inventory();
        inventory2.setId(2);
        inventory2.setProductId(2);
        inventory2.setQuantity(20);

        List<Inventory> inventoryList = new ArrayList<>();
        inventoryList.add(inventory);
        inventoryList.add(inventory2);

        doReturn(inventory).when(inventoryDao).createInventory(inventory1);
        doReturn(inventory).when(inventoryDao).getInventoryById(1);
        doReturn(inventoryList).when(inventoryDao).getAllInventories();
        doReturn(null).when(inventoryDao).getInventoryById(4);
    }
}
