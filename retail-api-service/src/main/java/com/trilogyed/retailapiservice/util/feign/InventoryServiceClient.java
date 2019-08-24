package com.trilogyed.retailapiservice.util.feign;

import com.trilogyed.retailapiservice.models.Inventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/inventory")
@FeignClient(name = "inventory-service")
public interface InventoryServiceClient {

    @PostMapping
    public Inventory createInventory(@RequestBody @Valid Inventory inventory);

    @GetMapping("/{id}")
    public Inventory getInventoryById(@PathVariable int id);

    @GetMapping
    public List<Inventory> getAllInventories();

    @PutMapping("/{id}")
    public void updateInventory(@PathVariable int id, @RequestBody @Valid Inventory inventory);

    @DeleteMapping("/{id}")
    public void deleteInventory(@PathVariable int id);
}
