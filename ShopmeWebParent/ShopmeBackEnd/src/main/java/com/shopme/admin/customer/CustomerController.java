package com.shopme.admin.customer;

import com.shopme.admin.export.CustomerCsvExporter;
import com.shopme.common.entity.Country;
import com.shopme.common.entity.Customer;
import com.shopme.common.exception.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class CustomerController {

    @Autowired CustomerService customerService;

    @GetMapping("/customers")
    public String listFirstPage(Model model) {
        return listByPage(1, model, "firstName", "asc", null);
    }

    @GetMapping("/customers/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
                             String sortField, String sortDir, String keyword) {

        if (sortField ==  null || sortField.isEmpty()) {
            sortField = "firstName";
        }
        if (sortDir ==  null || sortDir.isEmpty()) {
            sortDir = "asc";
        }


        Page<Customer> page = customerService.listByPage(pageNum, sortField, sortDir, keyword);
        List<Customer> listCustomers = page.getContent();

        long startCount = (pageNum - 1) * CustomerService.CUSTOMERS_PER_PAGE + 1;
        long endCount = startCount + CustomerService.CUSTOMERS_PER_PAGE - 1;
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listCustomers", listCustomers);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);
        model.addAttribute("moduleURL", "/customers");

        return "customers/customers";
    }


    @GetMapping("/customers/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {

        List<Customer> listCustomer = customerService.listAll();
        CustomerCsvExporter exporter = new CustomerCsvExporter();
        exporter.export(listCustomer, response);
    }

    @GetMapping("/customers/{id}/enabled/{status}")
    public String updateUserEnabledStatus(@PathVariable("id") Integer id,
                                          @PathVariable("status") boolean enabled,
                                          RedirectAttributes redirectAttributes) {

        customerService.updateUserEnabledStatus(id, enabled);
        String status = enabled ? "enabled" : "disabled";
        String message = "The Customer ID " + id + " has been " + status;

        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/customers";
    }

    @GetMapping("/customers/detail/{id}")
    public String viewCustomer(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Customer customer = customerService.get(id);
            model.addAttribute("customer", customer);

            return "customers/customer_detail_modal";
        } catch (CustomerNotFoundException exception) {
            redirectAttributes.addFlashAttribute("message", exception.getMessage());
            return "redirect:/customers";
        }
    }

    @GetMapping("/customers/{id}")
    public String editCustomer(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Customer customer = customerService.get(id);
            List<Country> countries = customerService.listAllCountries();

            model.addAttribute("listCountries", countries);
            model.addAttribute("customer", customer);
            model.addAttribute("pageTitle", String.format("Edit Customer (ID: %d)", id));

            return "customers/customer_form";
        } catch (CustomerNotFoundException exception) {
            redirectAttributes.addFlashAttribute("message", exception.getMessage());
            return "redirect:/customers";
        }
    }

    @PostMapping("/customers/save")
    public String saveCustomer(Customer customer, RedirectAttributes redirectAttributes) {
        customerService.save(customer);
        redirectAttributes.addFlashAttribute("message"
                , "The customer ID " + customer.getId() + " has been updated successfully");
        return "redirect:/customers";
    }

    @GetMapping("/customers/delete/{id}")
    public String deleteCustomer(@PathVariable(name = "id") Integer id,
                                RedirectAttributes redirectAttributes) throws CustomerNotFoundException {
        customerService.delete(id);

        redirectAttributes.addFlashAttribute("message",
                "The Customer ID " + id + " has been deleted successfully");

        return "redirect:/customers";
    }
}
