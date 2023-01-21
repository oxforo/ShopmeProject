package com.shopme.admin.brand;

import com.shopme.admin.AbstractExporter;
import com.shopme.common.entity.Brand;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BrandCsvExporter extends AbstractExporter {

    public void export(List<Brand> listBrands, HttpServletResponse response) throws IOException {
        super.setResponseHeader("brands", response, "text/csv", ".csv");

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
                CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader = {"ID", "Name"};
        String[] fieldMapping = {"id", "name"};
        csvWriter.writeHeader(csvHeader);

        for (Brand brand : listBrands) {
            csvWriter.write(brand, fieldMapping);
        }
        csvWriter.close();
    }
}
