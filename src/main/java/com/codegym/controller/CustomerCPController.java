package com.codegym.controller;

import com.codegym.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/cp/customers")
public class CustomerCPController {

    @Autowired
    AppUtils appUtils;


    @GetMapping
    public ModelAndView showListCustomer(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("customer", appUtils.getCustomer());
        modelAndView.setViewName("cp/customer/customerCP");

        return modelAndView;
    }


//    @GetMapping("/update")
//    public ModelAndView showUpdateForm(){
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("cp/customer/updateCustomer");
//        return modelAndView;
//    }



}
