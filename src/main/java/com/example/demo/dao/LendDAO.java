package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Lend;

public interface LendDAO {

 public int insert(Lend lend);

 public List<Lend> findAll();

 public List<Lend> findOne(String user_id);

 public List<Lend> findAll(String date, String room_id);

 public int update(Lend lend, Long id);

 public int delete(Lend lend);
// public int delete(Long AINum_Lend);
}