package com.himynameisilnano.hibernate.ssreceipttocustomer.boundary;

import com.himynameisilnano.hibernate.JpaTransactionManagerTestSupplier;
import com.himynameisilnano.hibernate.ssauthortobook.entity.Author;
import com.himynameisilnano.hibernate.ssreceipttocustomer.entity.Customer;
import com.himynameisilnano.hibernate.ssreceipttocustomer.entity.Receipt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.hibernate.testing.transaction.TransactionUtil.doInJPA;

class CustomerServiceTest {

    @Test
    void save_Jack_shouldOnlySaveCustomerButNoReceipt() {
        JpaTransactionManagerTestSupplier supplier = new JpaTransactionManagerTestSupplier("h2-dev");
        Customer jack = doInJPA(supplier::getFactory, entityManager -> {
            Customer _jack = new Customer("XYZ-123", "Jack");
            Receipt receipt = new Receipt(UUID.randomUUID().toString(), _jack);

            CustomerService customerService = new CustomerService(entityManager);
            customerService.save(_jack);

            ReceiptService receiptService = new ReceiptService(entityManager);
            List<Receipt> allReceipts = receiptService.findAllReceipts();
            Assertions.assertEquals(0, allReceipts.size());

            return _jack;
        });

        Assertions.assertNotNull(jack.getId());
        Assertions.assertEquals("Jack", jack.getName());
    }

    @Test
    void save_JacksReceipt_shouldSaveJackAndReceipt() {
        JpaTransactionManagerTestSupplier supplier = new JpaTransactionManagerTestSupplier("h2-dev");
        Customer jack = doInJPA(supplier::getFactory, entityManager -> {
            Customer _jack = new Customer("XYZ-123", "Jack");
            Receipt receipt = new Receipt(UUID.randomUUID().toString(), _jack);

            ReceiptService receiptService = new ReceiptService(entityManager);
            receiptService.save(receipt);

            List<Receipt> allReceipts = receiptService.findAllReceipts();
            Assertions.assertEquals(1, allReceipts.size());

            Assertions.assertNotNull(receipt.getCustomer());
            Assertions.assertEquals(_jack, receipt.getCustomer());

            return _jack;
        });

        Assertions.assertNotNull(jack.getId());
        Assertions.assertEquals("Jack", jack.getName());
    }

}