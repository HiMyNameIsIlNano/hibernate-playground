package com.himynameisilnano.hibernate.ssreceipttocustomer.boundary;

import com.himynameisilnano.hibernate.JdkLoggingConfigReaderHelper;
import com.himynameisilnano.hibernate.JpaTransactionManagerTestSupplier;
import com.himynameisilnano.hibernate.ssreceipttocustomer.entity.Customer;
import com.himynameisilnano.hibernate.ssreceipttocustomer.entity.Receipt;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.UUID;

import static org.hibernate.testing.transaction.TransactionUtil.doInJPA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(JdkLoggingConfigReaderHelper.class)
class CustomerServiceTest {

    @Test
    void save_Jack_shouldOnlySaveCustomerButNoReceipt() {
        JpaTransactionManagerTestSupplier supplier = new JpaTransactionManagerTestSupplier("h2-dev");
        Customer jack = doInJPA(supplier::getFactory, entityManager -> {
            Customer _jack = new Customer("XYZ-123", "Jack");

            CustomerService customerService = new CustomerService(entityManager);
            customerService.save(_jack);

            ReceiptService receiptService = new ReceiptService(entityManager);
            List<Receipt> allReceipts = receiptService.findAllReceipts();
            assertEquals(0, allReceipts.size());

            return _jack;
        });

        assertNotNull(jack.getId());
        assertEquals("Jack", jack.getName());
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
            assertEquals(1, allReceipts.size());

            assertNotNull(receipt.getCustomer());
            assertEquals(_jack, receipt.getCustomer());

            return _jack;
        });

        assertNotNull(jack.getId());
        assertEquals("Jack", jack.getName());
    }

}