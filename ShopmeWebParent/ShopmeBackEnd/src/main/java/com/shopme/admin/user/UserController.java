package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public String listAll(Model model) {
        List<User> listUsers = userService.listAll();
        model.addAttribute("listUsers", listUsers);

        return "users";
    }

    @GetMapping("/users/new")
    public String newUser(Model model) {
        List<Role> listRoles = userService.listRoles();

        User user = new User();
        user.setEnabled(true);
        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        model.addAttribute("pageTitle", "Create New User");
        return "user_form";
    }

    @PostMapping("/users")
    public String saveUser(User user, RedirectAttributes redirectAttributes) {
        System.out.println("user = " + user);
        userService.save(user);

        redirectAttributes.addFlashAttribute("message", "The user has been saved successfully.");

        return "redirect:/users";
    }

    @GetMapping("/users/{id}")
    public String editUser(@PathVariable(name = "id") Integer id,
                           Model model,
                           RedirectAttributes redirectAttributes) {

        try {
            User user = userService.get(id);
            List<Role> listRoles = userService.listRoles();

            model.addAttribute("user", user);
            model.addAttribute("listRoles", listRoles);
            model.addAttribute("pageTitle", "Edit User (ID: " + id + ")");
            return "user_form";
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/users";
        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        try {
            userService.delete(id);
            redirectAttributes.addFlashAttribute("message",
                    "The user ID " + id + " has been deleted successfully");
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/users";
    }

    @GetMapping("/user/{id}/enabled/{status}")
    public String updateUserEnabledStatus(@PathVariable("id") Integer id,
                                          @PathVariable("status") boolean enabled,
                                          RedirectAttributes redirectAttributes){
        userService.updateUserEnabledStatus(id, enabled);
        String status = enabled ? "enabled" : "disabled";
        String message = "The user ID " + id + " has been " + status;

        return "redirect:/users";
    }
    }
