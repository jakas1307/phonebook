package com.pb.phonebook.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pb.phonebook.dto.LocationDto;
import com.pb.phonebook.dto.UserDto;
import com.pb.phonebook.service.CompanyService;
import com.pb.phonebook.service.DepartmentService;
import com.pb.phonebook.service.LocationService;
import com.pb.phonebook.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CompanyService companyService;
    private final DepartmentService departmentService;
    private final LocationService locationService;

    @GetMapping
    public String listUsers(@RequestParam(required = false) String keyword, Model model) {
        System.out.println("DEBUG KEYWORD: " + keyword);

        List<UserDto> users;
        if (keyword == null || keyword.isEmpty()) {
            users = userService.getAllUsers();
        } else {
            users = userService.searchUsers(keyword);
        }

        model.addAttribute("users", users);
        model.addAttribute("keyword", keyword);
        return "users/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("user", new UserDto());
        model.addAttribute("companies", companyService.getAllCompanies());
        model.addAttribute("departments", departmentService.getAllDepartments());

        List<String> subregions = locationService.getAllLocations()
                                                 .stream()
                                                 .map(LocationDto::getSubregion)
                                                 .filter(sr -> sr != null && !sr.isEmpty())
                                                 .distinct()
                                                 .toList();
        
        System.out.println("DEBUG Locations: " + locationService.getAllLocations());
        System.out.println("DEBUG Subregions: " + subregions);
        
        model.addAttribute("subregions", subregions);
        model.addAttribute("locations", List.of());

        return "users/create";
    }

    @ResponseBody
    @GetMapping("/locations-by-subregion")
    public List<LocationDto> getLocationBySubregion(@RequestParam String subregion) {
        return locationService.getLocationsBySubregion(subregion);
    }

    @PostMapping
    public String create(@ModelAttribute("user") UserDto dto,
            RedirectAttributes redirectAttributes) {
        userService.createUser(dto);
        redirectAttributes.addFlashAttribute("successMessage", "Data user berhasil ditambahkan!");
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        UserDto dto = userService.getUserById(id);
        model.addAttribute("user", dto);
        model.addAttribute("companies", companyService.getAllCompanies());
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("locations", locationService.getAllLocations());

        List<String> subregions = locationService.getAllLocations()
                                                 .stream()
                                                 .map(LocationDto::getSubregion)
                                                 .filter(sr -> sr != null && !sr.isEmpty())
                                                 .distinct()
                                                 .toList();
        
        System.out.println("DEBUG Locations: " + locationService.getAllLocations());
        System.out.println("DEBUG Subregions: " + subregions);
        
        model.addAttribute("subregions", subregions);
        model.addAttribute("locations", List.of());
        
        return "users/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id, @ModelAttribute("user") UserDto dto,
            RedirectAttributes redirectAttributes) {
        userService.updateUser(id, dto);
        redirectAttributes.addFlashAttribute("successMessage", "Data User berhasil diperbaharui!");
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("successMessage", "Data User berhasil dihapus.");
        return "redirect:/users";
    }
}