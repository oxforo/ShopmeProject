package com.shopme.admin.export;

import com.shopme.admin.AbstractExporter;
import com.shopme.common.entity.Customer;
import com.shopme.common.entity.User;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CustomerCsvExporter extends AbstractExporter {
    public void export(List<Customer> listCustomers, HttpServletResponse response) throws IOException {
        super.setResponseHeader("customers", response, "text/csv", ".csv");

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
                CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader = {"ID", "FirstName", "Last Name", "E-mail", "City", "State", "Country", "Enabled"};
        String[] fieldMapping = {"id", "firstName", "lastName", "email", "city", "state", "country", "enabled"};
        csvWriter.writeHeader(csvHeader);

        for (Customer customer : listCustomers) {
            csvWriter.write(customer, fieldMapping);
        }

        csvWriter.close();
    }
}
