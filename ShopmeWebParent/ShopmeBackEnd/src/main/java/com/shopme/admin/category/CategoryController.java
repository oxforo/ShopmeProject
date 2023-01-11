package com.shopme.admin.category;

import com.shopme.admin.FileUploadUtil;
import com.shopme.common.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/categories")
    public String listFirstPage(Model model) {
        return listByPage(1, model, "id", "asc", null);
    }

    @GetMapping("/categories/page/{pageNum")
    public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
                             @Param("sortField") String sortField,
                             @Param("sortDir") String sortDir,
                             @Param("keyword") String keyword) {
        Page<Category> page = categoryService.listByPage(pageNum, sortField, sortDir, keyword);
        List<Category> listCategories = page.getContent();

        long startCount = (pageNum - 1) * CategoryService.CATEGORIES_PER_PAGE * 1;
        long endCount = startCount + CategoryService.CATEGORIES_PER_PAGE - 1;

        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listUsers", listCategories);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);

        return "users/users";
    }

    @GetMapping("/categories/new")
    public String newCategories(Model model) {

        return "categories/category_form";
    }

    @PostMapping("/categories")
    public String saveCategories(Category category, RedirectAttributes redirectAttributes,
                                 @RequestParam("image")MultipartFile multipartFile) throws IOException {

        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            category.setImage(fileName);
            Category savedCategory = categoryService.save(category);

            String uploadDir = "category-photos/" + savedCategory.getId();

            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            if (category.getImage().isEmpty()) {
                category.setImage(null);
            }
            categoryService.save(category);
        }
        redirectAttributes.addFlashAttribute("message", "The user has been saved successfully.");

        return getRedirectURLtoAffectedCategory(category);
    }

    private String getRedirectURLtoAffectedCategory(Category category) {
        Integer firstId = category.getId();
        return "redirect:/users/page/1?sortField=id&sortDir=asc&keyword=" + firstId;
    }

    @GetMapping("/categories/{id}")
    public String editCategory(@PathVariable(name = "id") Integer id,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        try {
            Category category = categoryService.get(id);
            model.addAttribute("category", category);
            model.addAttribute("pageTitle", "Edit Category (ID: " + id + ")");

            return "categories/categories_form";
        } catch (CategoryNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/categories";
        }
    }

    @GetMapping("/categories/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        try {
            categoryService.delete(id);
            redirectAttributes.addFlashAttribute("message",
                    "The category ID " + id + " has been deleted successfully");
        } catch (CategoryNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/categories";
    }

    @GetMapping("/categories/{id}/enabled/{status}")
    public String updateUserEnabledStatus(@PathVariable("id") Integer id,
                                          @PathVariable("status") boolean enabled,
                                          RedirectAttributes redirectAttributes) {

        categoryService.updateUserEnabledStatus(id, enabled);
        String status = enabled ? "enabled" : "disabled";
        String message = "The user ID " + id + " has been " + status;

        return "redirect:/categories";
    }
}
