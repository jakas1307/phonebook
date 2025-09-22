package com.pb.phonebook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pb.phonebook.dto.DepartmentDto;
import com.pb.phonebook.service.DepartmentService;

import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {
    
    private final DepartmentService departmentService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "departments/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("department", new DepartmentDto());
        return "departments/create";
    }

    @PostMapping
    public String create(@ModelAttribute("department")
                         DepartmentDto dto,
                         RedirectAttributes redirectAttributes) {
        departmentService.createDepartment(dto);
        redirectAttributes.addFlashAttribute("successMessage", "Data departemen berhasil ditambahkan!");
        return "redirect:/departments";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        DepartmentDto dto = departmentService.getDepartmentById(id);
        model.addAttribute("department", dto);
        return "departments/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute("department")
                         DepartmentDto dto,
                         RedirectAttributes redirectAttributes) {
        departmentService.updateDepartment(id, dto);
        redirectAttributes.addFlashAttribute("successMessage", "Data departemen berhasil diperbaharui!");
        return "redirect:/departments";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        departmentService.deleteDepartment(id);
        redirectAttributes.addFlashAttribute("successMessage", "Data departemen berhasil dihapus.");
        return "redirect:/departments";
    }
}
