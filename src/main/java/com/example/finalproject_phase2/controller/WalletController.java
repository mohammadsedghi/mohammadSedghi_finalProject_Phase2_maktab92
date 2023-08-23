package com.example.finalproject_phase2.controller;

import com.example.finalproject_phase2.entity.Customer;
import com.example.finalproject_phase2.service.CustomerService;
import com.example.finalproject_phase2.service.WalletService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

@Controller
@RequestMapping("/wallet")
public class WalletController {
    String captchaText;
    private final WalletService walletService;
    private final CustomerService customerService;
    @Autowired
    public WalletController(WalletService walletService, CustomerService customerService) {
        this.walletService = walletService;
        this.customerService = customerService;
    }
    @RequestMapping(value = "/payment", method = RequestMethod.GET)
    public String home() {
        return "index";
    }

    @PostMapping("/send-data")
    @ResponseBody
    public String sendData(@RequestParam String cardNumber1, @RequestParam String cardNumber2
            , @RequestParam String cardNumber3, @RequestParam String cardNumber4, @RequestParam String cvv2,
                           @RequestParam String month, @RequestParam String year,
                           @RequestParam String captcha, @RequestParam String password, Model model) {
        String response;
        if (cardNumber1 != null && !cardNumber1.isEmpty() && cardNumber2 != null && !cardNumber2.isEmpty()) {
            System.out.println(cardNumber1);
            System.out.println(cardNumber2);
            response = "Received data 1: " + cardNumber1 + ", Received data 2: " + cardNumber2;
        } else {
            return "No data received.";
        }
        model.addAttribute("response", response);
        return captchaText;
//        return "index :: #response";
    }

    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String captchaText = generateCaptchaText(6); // 6 characters
        this.captchaText=captchaText;
        // Store the CAPTCHA text in the user's session
        HttpSession session = request.getSession();
        session.setAttribute("captcha", captchaText);

        // Create an image with the CAPTCHA text
        BufferedImage captchaImage = generateCaptchaImage(captchaText);

        // Send the image to the client
        response.setContentType(MimeTypeUtils.IMAGE_PNG_VALUE);
        OutputStream out = response.getOutputStream();
        ImageIO.write(captchaImage, "png", out);
        out.close();
    }

    private String generateCaptchaText(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder captchaText = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            captchaText.append(characters.charAt(index));
        }
        return captchaText.toString();
    }

    private BufferedImage generateCaptchaImage(String text) {
        int width = 150;
        int height = 50;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // Set background color
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // Draw text
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 30));
        g2d.drawString(text, 30, 35);

        g2d.dispose();
        return image;
    }
}


