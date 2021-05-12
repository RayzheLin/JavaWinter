package com.example.demo.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.dao.LendDAO;
import com.example.demo.entity.Lend;

@RestController
public class LendController {
    @Autowired
    LendDAO dao;


    @GetMapping(value = {"/lendingsheet"})
    public List<Lend> retrieveLend() throws SQLException{
        return dao.findAll();
    }

    @GetMapping(value = {"/lendingsheet/{date}/{roomid}"})
    public List<Lend> retrieveAllLend (@PathVariable("date") String date, @PathVariable("roomid") String roomid) throws SQLException{
        return dao.findAll(date, roomid);
    }

    @GetMapping(value = {"/lendingsheet/{user_id}"})
    public List<Lend> retrieveOneLend (@PathVariable("user_id") String user_id) throws SQLException{
        return dao.findOne(user_id);
    }

    @PostMapping(value = "/lendingsheet")
    public void processFormCreate(@RequestBody Lend lend) throws SQLException {
        dao.insert(lend);
    }

    @PutMapping(value = "/lendingsheet/{id}")
    public void processFormUpdate(@PathVariable("id") Long id, @RequestBody Lend lend) throws SQLException {
        dao.update(lend, id);
    }


    @DeleteMapping(value = "/lendingsheet")
    public void deleteLend(@RequestBody Lend lend) throws SQLException{
        dao.delete(lend);
    }

//    @DeleteMapping(value = "/lendingsheet/{id}")
//    public void deleteLend(@PathVariable("id") Long id) {
//        dao.delete(id);
//    }



}