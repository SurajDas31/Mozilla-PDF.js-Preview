package com.docedge.esign.controller;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

@Controller
//@RequestMapping("/")
public class _Controller {

    @GetMapping("/")
    @CrossOrigin("*")
    public String index(Model model) throws IOException {
//        JSONObject bodyObject = new JSONObject(body);
//
//        String fileBytes = bodyObject.getString("file");
//
//        String pass = bodyObject.getString("pass");
//        String reason = bodyObject.getString("reason");
//        String location = bodyObject.getString("location");
        //File file = new File("C:\\Users\\Pericent\\Downloads\\USB Based E-Sign.pdf");
        byte[] bytes = Base64.getEncoder().encode(Files.readAllBytes(Paths.get("C:\\Users\\Pericent\\Downloads\\Hersh Constructrion.pdf")));

        model.addAttribute("file", new String(bytes));
//        System.out.println(pass);
//        System.out.println(fileBytes.getBytes());
//        System.out.println(reason);
//        System.out.println(location);

        return "index";
    }

//    @GetMapping("/index")
//    public String home(Model model){
//        model.addAttribute("name","Tony");
//        return "index";
//    }
}
