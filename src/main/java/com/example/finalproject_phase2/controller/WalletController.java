package com.example.finalproject_phase2.controller;

import com.example.finalproject_phase2.entity.Customer;
import com.example.finalproject_phase2.service.CustomerService;
import com.example.finalproject_phase2.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/wallet")
public class WalletController {

    private final WalletService walletService;

    private final CustomerService customerService;

    @Autowired
    public WalletController(WalletService walletService, CustomerService customerService) {
        this.walletService = walletService;
        this.customerService = customerService;
    }

//    @RequestMapping("/email")
//    public ModelAndView getUser(@PathVariable String email) {
//        Customer user = customerService.findByEmail(email).get();
//        ModelAndView modelAndView = new ModelAndView("userView");
//        modelAndView.addObject("user", user);
//        return modelAndView;
//    }
   @RequestMapping(value = "/payment",method = RequestMethod.GET)
    public String home(){
        return "index";
    }
    @GetMapping ( "/send-data")
    @ResponseBody
    public String sendData(@RequestParam String data ,@RequestParam String data2 , Model model) {
        String response;
        if (data != null && !data.isEmpty()&& data2 != null && !data2.isEmpty()) {
            System.out.println(data);
            System.out.println(data2);
            response = "Received data 1: " + data + ", Received data 2: " + data2;
        } else {
            return "No data received.";
        }
        model.addAttribute("response", response);
        return "index :: #response";
    }

//    @PostMapping ( "/send-data")
//    @ResponseBody
//    public String sendData(@RequestBody DataPayload  payload  , Model model) {
//        String response;
//        if (payload.getData1() != null && !payload.getData1().isEmpty()
//                && payload.getData2() != null && !payload.getData2().isEmpty()) {
//            // Process the data as needed
//            System.out.println(payload.getData1());
//            System.out.println(payload.getData2());
//            response = "Received data 1: " + payload.getData1() + ", Received data 2: " + payload.getData2();
//        } else {
//            response = "Incomplete data received.";
//        }
//
//        model.addAttribute("response", response);
//        return "index :: #response";
//    }
//
//    public static class DataPayload {
//        private String data1;
//        private String data2;
//
//        // Getters and setters
//
//        public String getData1() {
//            return data1;
//        }
//
//        public void setData1(String data1) {
//            this.data1 = data1;
//        }
//
//        public String getData2() {
//            return data2;
//        }
//
//        public void setData2(String data2) {
//            this.data2 = data2;
//        }
//    }



//@RequestMapping(value = "/payment",method = RequestMethod.GET)
//public String showForm( @ModelAttribute("customer") Customer customer) {
//   // Customer user = customerService.findByEmail(email).get();
//   // ModelAndView modelAndView = new ModelAndView("index");
//      // modelAndView.addObject("user", user);
//    return "/WEB-INF/payment.jsp";
//}
//    @RequestMapping("/email")
//    public String getUser( String email) {
//      //  Customer user = customerService.findByEmail(email).get();
//     //   return user.getEmail();
//        return "email";
//    }
}
