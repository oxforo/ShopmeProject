package com.shopme.admin.shippingrate;

import com.shopme.admin.paging.PagingAndSortingHelper;
import com.shopme.admin.paging.PagingAndSortingParam;
import com.shopme.common.entity.Country;
import com.shopme.common.entity.ShippingRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ShippingRateController {

    @Autowired
    private ShippingRateService shippingRateService;

    private final String INITIAL_PATH = "/shipping_rates/page/1?sortField=country&sortDir=asc";
    @GetMapping("/shipping_rates")
    public String listFirstPage() {
        return "redirect:" + INITIAL_PATH;
    }

    @GetMapping("/shipping_rates/page/{pageNum}")
    public String listByPage(
            @PagingAndSortingParam(listName = "listShippingRate", moduleURL = "/shipping_rates") PagingAndSortingHelper helper,
            @PathVariable(name = "pageNum") int pageNum) {


        shippingRateService.listByPage(pageNum, helper);

        return "shipping_rates/shipping_rates";
    }

    @GetMapping("/shipping_rates/{id}/codSupported/{codSupported}")
    public String updateShippingRateEnabledStatus(@PathVariable("id") Integer id,
                                                  @PathVariable("codSupported") boolean codSupported,
                                                  RedirectAttributes redirectAttributes) {

        shippingRateService.updateUserEnabledStatus(id, codSupported);
        String message = "COD support for shipping rate ID " + id + " has been updated.";

        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:" + INITIAL_PATH;
    }

    @GetMapping("/shipping_rates/new")
    public String newShippingRate(Model model) {

        List<Country> listCountries = shippingRateService.listAllCountries();

        model.addAttribute("shippingRate", new ShippingRate());
        model.addAttribute("listCountries", listCountries);
        model.addAttribute("pageTitle", "New Rate");

        return "shipping_rates/shipping_rates_form";
    }

    @PostMapping("/shipping_rates")
    public String saveShippingRate(ShippingRate shippingRate, RedirectAttributes redirectAttributes) {
        try {
            shippingRateService.save(shippingRate);
            redirectAttributes.addFlashAttribute("message"
                    , "The shipping rate has been saved successfully.");
        } catch (ShippingRateDuplicateException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:" + INITIAL_PATH;
    }
    @GetMapping("/shipping_rates/{id}")
    public String editShippingRate(@PathVariable("id") Integer id, Model model,
                                   RedirectAttributes redirectAttributes) {

        List<Country> listCountries = shippingRateService.listAllCountries();
        try {
            ShippingRate shippingRate = shippingRateService.get(id);

            model.addAttribute("shippingRate", shippingRate);
            model.addAttribute("listCountries", listCountries);
            model.addAttribute("pageTitle", "Manage Shipping Rates | Edit Rate (ID: " + id + ")");

            return "shipping_rates/shipping_rates_form";
        } catch (ShippingRateNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());

            return "redirect:" + INITIAL_PATH;
        }
    }

    @GetMapping("/shipping_rates/delete/{id}")
    public String deleteCategory(@PathVariable(name = "id") Integer id,
                                 RedirectAttributes redirectAttributes) {
        try {
            shippingRateService.delete(id);
            redirectAttributes.addFlashAttribute("message",
                    "The shipping rate ID " + id + " has been deleted successfully");
        } catch (ShippingRateNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:" + INITIAL_PATH;
    }
}
