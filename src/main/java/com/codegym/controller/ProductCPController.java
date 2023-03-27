package com.codegym.controller;

import com.codegym.utils.AppUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/cp/products")
public class ProductCPController {

    @Autowired
    AppUtils appUtils;

    @GetMapping
    public ModelAndView showListProduct(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("customer", appUtils.getCustomer());
        modelAndView.setViewName("cp/product/productCP");
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("customer", appUtils.getCustomer());
        modelAndView.setViewName("cp/product/create");
        return modelAndView;
    }

//    @GetMapping("/update")
//    public ModelAndView showUpdateForm(){
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("cp/product/update");
//        return modelAndView;
//    }



}
