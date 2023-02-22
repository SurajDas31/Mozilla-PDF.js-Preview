package com.docedge.esign.controller;

import com.docedge.esign.service.SignService;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class _RestController {

    @Autowired
    private SignService signService;

    @GetMapping("/")
    public String hello() {
        return "Service is running";
    }

    @PostMapping("/")
    public ResponseEntity getSigned(@RequestBody String body) throws IOException {

        //System.out.println("Pass: " + body);
        JSONObject bodyObject = new JSONObject(body);

        String fileBytes = bodyObject.getString("file");

        String pass = bodyObject.getString("pass");
        String reason = bodyObject.getString("reason");
        String location = bodyObject.getString("location");


        System.out.println(pass);
        System.out.println(fileBytes.getBytes());
        System.out.println(reason);
        System.out.println(location);

        File file = null;
        File signedFile = null;
        try {

            file = File.createTempFile("original", ".pdf");
            try (FileOutputStream fos = new FileOutputStream(file);) {
                // To be short I use a corrupted PDF string, so make sure to use a valid one if you want to preview the PDF file

                byte[] decoder = Base64.getDecoder().decode(fileBytes);

                fos.write(decoder);
                System.out.println("PDF File Saved");
            }

            signedFile = File.createTempFile("signed", ".pdf");
            signService.getSigned(file, signedFile, pass, reason, location);

            if(signedFile.length() == 0){
                return new ResponseEntity<>(new String("500 Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return ResponseEntity.ok()
                    .body(Base64.getEncoder().encode(Files.readAllBytes(Paths.get(signedFile.getAbsolutePath()))));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            if (file != null)
                FileUtils.forceDelete(file);
            if (signedFile != null && signedFile.length() != 0)
                FileUtils.forceDelete(signedFile);
        }
    }
}
