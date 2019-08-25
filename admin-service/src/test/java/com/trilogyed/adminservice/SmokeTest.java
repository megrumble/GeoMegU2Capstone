package com.trilogyed.adminservice;

import com.trilogyed.adminservice.controller.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmokeTest {

    @Autowired
    CustomerServiceController customerServiceController;
    @Autowired
    ProductServiceController productServiceController;
    @Autowired
    InvoiceServiceController invoiceServiceController;
    @Autowired
    InventoryServiceController inventoryServiceController;
    @Autowired
    LevelUpServiceController levelUpServiceController;

    @Test
    public void contexLoads() throws Exception {
        assertThat(customerServiceController).isNotNull();
        assertThat(productServiceController).isNotNull();
        assertThat(inventoryServiceController).isNotNull();
        assertThat(invoiceServiceController).isNotNull();
        assertThat(levelUpServiceController).isNotNull();
    }
}
