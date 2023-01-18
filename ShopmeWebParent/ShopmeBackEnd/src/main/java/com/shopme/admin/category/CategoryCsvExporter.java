package com.shopme.admin.category;

import com.shopme.admin.AbstractExporter;
import com.shopme.common.entity.Category;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class CategoryCsvExporter extends AbstractExporter {

    public void export(List<Category> listCategories, HttpServletResponse response) throws IOException {
        super.setResponseHeader("categories", response, "text/csv", ".csv");

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
                CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader = {"ID", "Name"};
        String[] fieldMapping = {"id", "name"};
        csvWriter.writeHeader(csvHeader);

        for (Category category : listCategories) {
            String originName = category.getName();
            category.setName(originName.replaceAll("--","  "));
            csvWriter.write(category, fieldMapping);
        }
        csvWriter.close();
    }
//
//    private void listSubCategoriesCsvForm(ICsvBeanWriter csvWriter, Category parent, int subLevel) throws IOException {
//
//        int newSubLevel = subLevel + 1;
//        Set<Category> children = parent.getChildren();
//
//        for (Category subCategory : children) {
//            String name = "";
//            for (int i = 0; i < newSubLevel; i++) {
//                name += "  ";
//            }
//            name += subCategory.getName();
//            csvWriter.write(subCategory.getId(), name);
//
//            listSubCategoriesCsvForm(csvWriter, subCategory, newSubLevel);
//        }
//    }
}
