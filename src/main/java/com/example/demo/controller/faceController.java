package com.example.demo.controller;


import com.example.demo.entity.userfacedata;
import com.example.demo.service.UserfacedataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class faceController {

    @Autowired
    private UserfacedataService userfacedataService;


    @GetMapping("/userface")
    public List<userfacedata> getAll(){
        System.out.println("getAll called");
        return userfacedataService.findAll();
    }

    @GetMapping("/userface/{id}")
    public userfacedata getOne(@PathVariable("id") String userface_id ){
        return userfacedataService.findOne(userface_id);
    }

    @GetMapping("/userfaceid")
    public List<String> getallid(){

        System.out.println("called");
        List<String> result = userfacedataService.getid();
        System.out.println("called after"+result.size());
        //List<String> result = ""
        return result;
    }

    @PostMapping("/userface")
    public void insert(@RequestBody  userfacedata userfacedata){
        userfacedataService.insert(userfacedata);
    }

    @PutMapping("/userface/{id}")
    public void updateState(@PathVariable("id") String userface_id){
        userfacedataService.updateState(userface_id);
    }
}
