package com.trilogyed.invoiceservice.service;

import com.trilogyed.invoiceservice.dao.InvoiceDao;
import com.trilogyed.invoiceservice.dao.InvoiceDaoJdbcTemplateImpl;
import com.trilogyed.invoiceservice.dao.InvoiceItemDao;
import com.trilogyed.invoiceservice.dao.InvoiceItemDaoJdbcTemplateImpl;
import com.trilogyed.invoiceservice.dto.Invoice;
import com.trilogyed.invoiceservice.dto.InvoiceItem;
import com.trilogyed.invoiceservice.viewmodel.InvoiceViewModel;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class InvoiceServiceTest {

    InvoiceService invoiceService;
    InvoiceDao invoiceDao;
    InvoiceItemDao invoiceItemDao;

    @Before
    public void setUp() throws Exception {
        setUpInvoiceDaoMock();

        invoiceService = new InvoiceService(invoiceDao, invoiceItemDao);
    }

    @Test
    public void addFindInvoice() {
        List<InvoiceItem> invoiceItems = new ArrayList<>();

        InvoiceItem invItem = new InvoiceItem();
        invItem.setInvoiceItemId(9);
//        invItem.setInvoiceId(1);
        invItem.setInventoryId(5);
        invItem.setQuantity(20);
        invItem.setUnitPrice(new BigDecimal(5.00).setScale(2, RoundingMode.HALF_UP));
        invoiceItems.add(invItem);

        InvoiceItem invItem2 = new InvoiceItem();
        invItem2.setInvoiceItemId(10);
//        invItem2.setInvoiceId(1);
        invItem2.setInventoryId(3);
        invItem2.setQuantity(12);
        invItem2.setUnitPrice(new BigDecimal(3.00).setScale(2, RoundingMode.HALF_UP));
        invoiceItems.add(invItem2);

        InvoiceViewModel ivm = new InvoiceViewModel();
        ivm.setCustomerId(12);
        ivm.setPurchaseDate(LocalDate.now());
        ivm.setInvoiceItemsList(invoiceItems);

        ivm = invoiceService.addInvoice(ivm);

        InvoiceViewModel ivm2 = invoiceService.findInvoice(ivm.getInvoiceId());

        assertEquals(ivm, ivm2);

    }

    @Test
    public void findAllInvoices() {
        List<InvoiceViewModel> viewModels = new ArrayList<>();
        List<InvoiceItem> invoiceItemsInvoice1 = new ArrayList<>();
        List<InvoiceItem> invoiceItemInvoice2 = new ArrayList<>();

        InvoiceItem invItem6 = new InvoiceItem();
        invItem6.setInvoiceItemId(10);
        invItem6.setInvoiceId(2);
        invItem6.setInventoryId(7);
        invItem6.setQuantity(15);
        invItem6.setUnitPrice(new BigDecimal(3.00).setScale(2, RoundingMode.HALF_UP));
        invoiceItemInvoice2.add(invItem6);

        InvoiceItem invItem2 = new InvoiceItem();
        invItem2.setInvoiceItemId(9);
        invItem2.setInvoiceId(1);
        invItem2.setInventoryId(5);
        invItem2.setQuantity(20);
        invItem2.setUnitPrice(new BigDecimal(5.00).setScale(2, RoundingMode.HALF_UP));
        invoiceItemsInvoice1.add(invItem2);

        InvoiceItem invItem4 = new InvoiceItem();
        invItem4.setInvoiceItemId(10);
        invItem4.setInvoiceId(1);
        invItem4.setInventoryId(3);
        invItem4.setQuantity(12);
        invItem4.setUnitPrice(new BigDecimal(3.00).setScale(2, RoundingMode.HALF_UP));
        invoiceItemsInvoice1.add(invItem4);
        /////////////////////////////////////////////////////////////////////////InvoiceItems above this line
        InvoiceViewModel ivm1 = new InvoiceViewModel();
        ivm1.setInvoiceId(1);
        ivm1.setCustomerId(12);
        ivm1.setPurchaseDate(LocalDate.now());
        ivm1.setInvoiceItemsList(invoiceItemsInvoice1);
        viewModels.add(ivm1);

        InvoiceViewModel ivm2 = new InvoiceViewModel();
        ivm2.setInvoiceId(2);
        ivm2.setCustomerId(12);
        ivm2.setPurchaseDate(LocalDate.of(2019, 04, 06));
        ivm2.setInvoiceItemsList(invoiceItemInvoice2);
        viewModels.add(ivm2);

        List<InvoiceViewModel> ivmList = invoiceService.findAllInvoices();

        assertEquals(viewModels, ivmList);

    }

    @Test
    public void findInvoiceByCustomerId() {
        List<InvoiceViewModel> viewModels = new ArrayList<>();
        List<InvoiceItem> invoiceItemsInvoice1 = new ArrayList<>(); //lists that will be added to InvoiceViewModels
        List<InvoiceItem> invoiceItemInvoice2 = new ArrayList<>();  //lists that will be added to InvoiceViewModels

        InvoiceItem invItem6 = new InvoiceItem();
        invItem6.setInvoiceItemId(10);
        invItem6.setInvoiceId(2);
        invItem6.setInventoryId(7);
        invItem6.setQuantity(15);
        invItem6.setUnitPrice(new BigDecimal(3.00).setScale(2, RoundingMode.HALF_UP));
        invoiceItemInvoice2.add(invItem6);

        InvoiceItem invItem2 = new InvoiceItem();
        invItem2.setInvoiceItemId(9);
        invItem2.setInvoiceId(1);
        invItem2.setInventoryId(5);
        invItem2.setQuantity(20);
        invItem2.setUnitPrice(new BigDecimal(5.00).setScale(2, RoundingMode.HALF_UP));
        invoiceItemsInvoice1.add(invItem2);

        InvoiceItem invItem4 = new InvoiceItem();
        invItem4.setInvoiceItemId(10);
        invItem4.setInvoiceId(1);
        invItem4.setInventoryId(3);
        invItem4.setQuantity(12);
        invItem4.setUnitPrice(new BigDecimal(3.00).setScale(2, RoundingMode.HALF_UP));
        invoiceItemsInvoice1.add(invItem4);
        /////////////////////////////////////////////////////////////////////////InvoiceItems above this line
        InvoiceViewModel ivm1 = new InvoiceViewModel();
        ivm1.setInvoiceId(1);
        ivm1.setCustomerId(12);
        ivm1.setPurchaseDate(LocalDate.now());
        ivm1.setInvoiceItemsList(invoiceItemsInvoice1);
        viewModels.add(ivm1);

        InvoiceViewModel ivm2 = new InvoiceViewModel();
        ivm2.setInvoiceId(2);
        ivm2.setCustomerId(12);
        ivm2.setPurchaseDate(LocalDate.of(2019, 04, 06));
        ivm2.setInvoiceItemsList(invoiceItemInvoice2);
        viewModels.add(ivm2);

        List<InvoiceViewModel> ivmList = invoiceService.findInvoiceByCustomerId(12);

        assertEquals(viewModels, ivmList);

    }

    @Test
    public void updateInvoice() {
        List<InvoiceItem> invoiceItems = new ArrayList<>();

        InvoiceItem invItem = new InvoiceItem();
        invItem.setInvoiceItemId(9);
//        invItem.setInvoiceId(1);
        invItem.setInventoryId(5);
        invItem.setQuantity(20);
        invItem.setUnitPrice(new BigDecimal(5.00).setScale(2, RoundingMode.HALF_UP));
        invoiceItems.add(invItem);

        InvoiceItem invItem2 = new InvoiceItem();
        invItem2.setInvoiceItemId(10);
//        invItem2.setInvoiceId(1);
        invItem2.setInventoryId(3);
        invItem2.setQuantity(12);
        invItem2.setUnitPrice(new BigDecimal(3.00).setScale(2, RoundingMode.HALF_UP));
        invoiceItems.add(invItem2);

        InvoiceViewModel ivm = new InvoiceViewModel();
        ivm.setInvoiceId(1);
        ivm.setCustomerId(12);
        ivm.setPurchaseDate(LocalDate.now());
        ivm.setInvoiceItemsList(invoiceItems);

        invoiceService.updateInvoice(ivm);

        verify(invoiceDao).updateInvoice(any(Invoice.class));


    }

    @Test
    public void deleteInvoice() {

        invoiceService.deleteInvoice(1);
//        verify(invoiceItemDao).deleteInvoiceItem(any(Integer.class));
        verify(invoiceDao).deleteInvoice(any(Integer.class));

    }

    public void setUpInvoiceDaoMock(){
        invoiceDao = mock(InvoiceDaoJdbcTemplateImpl.class);
        invoiceItemDao = mock(InvoiceItemDaoJdbcTemplateImpl.class);

        List<Invoice> invoiceList = new ArrayList<>();
        List<InvoiceItem> invoiceItemsInvoice1 = new ArrayList<>();
        List<InvoiceItem> invoiceItemInvoice2 = new ArrayList<>();

        Invoice invoice1 = new Invoice();
        invoice1.setCustomerId(12);
        invoice1.setPurchaseDate(LocalDate.now());

        Invoice invoice2 = new Invoice();
        invoice2.setInvoiceId(1);
        invoice2.setCustomerId(12);
        invoice2.setPurchaseDate(LocalDate.now());
        invoiceList.add(invoice2);

        Invoice invoice3 = new Invoice();
        invoice3.setCustomerId(12);
        invoice3.setPurchaseDate(LocalDate.of(2019, 04, 06));

        Invoice invoice4 = new Invoice();
        invoice4.setInvoiceId(2);
        invoice4.setCustomerId(12);
        invoice4.setPurchaseDate(LocalDate.of(2019, 04, 06));
        invoiceList.add(invoice4);
        ///////////////////////////////////////////////////////////////////////////////////invoices above this line
        InvoiceItem invItem1 = new InvoiceItem();
        invItem1.setInvoiceId(1);
        invItem1.setInventoryId(5);
        invItem1.setQuantity(20);
        invItem1.setUnitPrice(new BigDecimal(5.00).setScale(2, RoundingMode.HALF_UP));

        InvoiceItem invItem2 = new InvoiceItem();
        invItem2.setInvoiceItemId(9);
        invItem2.setInvoiceId(1);
        invItem2.setInventoryId(5);
        invItem2.setQuantity(20);
        invItem2.setUnitPrice(new BigDecimal(5.00).setScale(2, RoundingMode.HALF_UP));
        invoiceItemsInvoice1.add(invItem2);

        InvoiceItem invItem3 = new InvoiceItem();
        invItem3.setInvoiceId(1);
        invItem3.setInventoryId(3);
        invItem3.setQuantity(12);
        invItem3.setUnitPrice(new BigDecimal(3.00).setScale(2, RoundingMode.HALF_UP));

        InvoiceItem invItem4 = new InvoiceItem();
        invItem4.setInvoiceItemId(10);
        invItem4.setInvoiceId(1);
        invItem4.setInventoryId(3);
        invItem4.setQuantity(12);
        invItem4.setUnitPrice(new BigDecimal(3.00).setScale(2, RoundingMode.HALF_UP));
        invoiceItemsInvoice1.add(invItem4);

        InvoiceItem invItem5 = new InvoiceItem();
        invItem5.setInvoiceId(2);
        invItem5.setInventoryId(7);
        invItem5.setQuantity(15);
        invItem5.setUnitPrice(new BigDecimal(3.00).setScale(2, RoundingMode.HALF_UP));

        InvoiceItem invItem6 = new InvoiceItem();
        invItem6.setInvoiceItemId(10);
        invItem6.setInvoiceId(2);
        invItem6.setInventoryId(7);
        invItem6.setQuantity(15);
        invItem6.setUnitPrice(new BigDecimal(3.00).setScale(2, RoundingMode.HALF_UP));
        invoiceItemInvoice2.add(invItem6);


        doReturn(invoice2).when(invoiceDao).createInvoice(invoice1);
        doReturn(invoice4).when(invoiceDao).createInvoice(invoice4);
        doReturn(invoice2).when(invoiceDao).getInvoice(1);
        doReturn(invItem2).when(invoiceItemDao).createInvoiceItem(invItem1);
        doReturn(invItem4).when(invoiceItemDao).createInvoiceItem(invItem3);
        doReturn(invItem6).when(invoiceItemDao).createInvoiceItem(invItem5);
        doReturn(invoiceList).when(invoiceDao).getAllInvoices();
        doReturn(invoiceList).when(invoiceDao).getInvoicesByCustomerId(12);
        doReturn(invoiceItemsInvoice1).when(invoiceItemDao).getInvoiceItemByInvoiceId(1);
        doReturn(invoiceItemInvoice2).when(invoiceItemDao).getInvoiceItemByInvoiceId(2);
    }
}