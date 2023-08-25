package com.example.finalproject_phase2.controller;

import com.example.finalproject_phase2.dto.specialistSuggestionDto.SpecialistSuggestionDto;
import com.example.finalproject_phase2.service.WalletService;
import com.example.finalproject_phase2.util.CheckValidation;
import com.example.finalproject_phase2.util.CustomRegex;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalTime;
import java.util.Random;

@Controller
@RequestMapping("/wallet")
public class WalletController {

    private String captchaText;
    private LocalTime localTime;
    private CustomRegex customRegex=new CustomRegex();
    private final WalletService walletService;
    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @RequestMapping(value = "/payment", method = RequestMethod.GET)
    public String payment() {
        localTime=LocalTime.now();
        return "index";
    }

    @PostMapping("/send-data")
    @ResponseBody
    public String sendData(@RequestParam String cardNumber1, @RequestParam String cardNumber2
            , @RequestParam String cardNumber3, @RequestParam String cardNumber4, @RequestParam String cvv2,
                           @RequestParam String month, @RequestParam String year,
                           @RequestParam String captcha, @RequestParam String password,
                           Model model) {
        String response;
       if (customRegex.checkOneInputIsValid(cardNumber1,customRegex.getValidDigitCardNumberPartOne())){
           if (customRegex.checkOneInputIsValid(cardNumber2,customRegex.getValidCardNumber())){
              if (customRegex.checkOneInputIsValid(cardNumber3,customRegex.getValidCardNumber())){
                  if ( customRegex.checkOneInputIsValid(cardNumber4,customRegex.getValidCardNumber())){
                        if (customRegex.checkOneInputIsValid(cvv2,customRegex.getValidCardNumber())){
                            if (customRegex.checkOneInputIsValid(month,customRegex.getValidCardNumberMonth())){
                                if (customRegex.checkOneInputIsValid(year,customRegex.getValidCardNumberYear())){
                                    if (captchaText.equals(captcha)){
                                        if (password.equals("1234")){
                                            response="true";
                                        }else response="password is incorrect";
                                    }else response="captcha is incorrect";
                                }else response="year of date is incorrect";
                            }else response="month of date is incorrect";
                        }else response="cvv2 is incorrect";
                  }else response="cardNumber is incorrect";
              }else response="cardNumber is incorrect";
           }else response="cardNumber is incorrect";
       }else response="cardNumber is incorrect";
        if (LocalTime.now().getMinute()-localTime.getMinute()>=5){
            response="false";
        }
        if (response.equals("true")){
            walletService.payWithOnlinePayment(CheckValidation.memberTypespecialist,100d);
        }
        model.addAttribute("response", response);
        return response;
          }
    @GetMapping("/after")
    public String afterPage() {
        return "afterPayment";
    }
    @GetMapping("/endTime")
    public String anotherPage() {
        return "endTime";
    }

    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String captchaText = generateCaptchaText(6); // 6 characters
        this.captchaText=captchaText;
        HttpSession session = request.getSession();
        session.setAttribute("captcha", captchaText);
        BufferedImage captchaImage = generateCaptchaImage(captchaText);
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
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 30));
        g2d.drawString(text, 30, 35);
        g2d.dispose();
        return image;
    }
    @PostMapping("/payWithWallet")
    public ResponseEntity<String> payWithWallet(@RequestBody SpecialistSuggestionDto specialistSuggestionDto) {
       return new ResponseEntity<>( walletService.payWithWallet(specialistSuggestionDto), HttpStatus.ACCEPTED);
    }
}


