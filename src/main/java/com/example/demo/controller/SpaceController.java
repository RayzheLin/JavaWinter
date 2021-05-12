package com.example.demo.controller;

import com.example.demo.entity.userfacedata;
import com.example.demo.service.UserfacedataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@CrossOrigin
public class SpaceController {

    @Autowired
    private UserfacedataService userfacedataService;

    @GetMapping("/Space")
    public List<userfacedata> showIn() throws SQLException {
        return userfacedataService.findIn();
    }
}
