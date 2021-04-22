package com.example.demo.lmpl;

import com.example.demo.entity.userfacedata;
import com.example.demo.service.UserfacedataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class userfacedatalmpl implements UserfacedataService {

    @Autowired
    DataSource dataSource;


    @Override
    public List<userfacedata> findAll() {
        List<userfacedata> userfacedata = new ArrayList<userfacedata>();
        try{
            Connection conn = dataSource.getConnection();
            String sql = "select * from userfacedata";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                userfacedata.add(getdata(rs));
            }
        }catch(Exception e) {
            //something wrong
            System.out.println(e);
        }
        return  userfacedata;
    }

    @Override
    public userfacedata findOne(String userface_id) {
        userfacedata userfacedata = new userfacedata();
        try {
            Connection conn = dataSource.getConnection();
            String sql = "select * from userfacedata where UserFace_Id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, userface_id );
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                userfacedata=getdata(rs);
            };


        } catch (Exception e) {
            e.printStackTrace();
        }

        return userfacedata;
    }

    @Override
    public List<String> getid() {
        List<String> allid = new ArrayList<>();
        try{
            Connection conn = dataSource.getConnection();
            String sql = "select UserFace_Id from userfacedata";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                allid.add(rs.getString("UserFace_Id"));
            }
        }catch(Exception e) {
            //something wrong
            System.out.println(e);
        }
        return  allid;


    }

    public userfacedata getdata(ResultSet rs) throws SQLException{

        return new userfacedata(
                rs.getString("UserFace_Id"),
                rs.getString("UserFace_Mail"),
                rs.getString("UserFace_Role"),
                rs.getString("UserFace_State"),
                rs.getString("UserFace_Password"),
                rs.getString("UserFace_Name")
                );
    }

    public int insert(userfacedata userfacedata){
        int result = 0;
        try {
            Connection conn = dataSource.getConnection();
            String sql = "insert into userfacedata(UserFace_id,UserFace_Mail,UserFace_Role,UserFace_State,UserFace_Name,UserFace_Password)values(?,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,userfacedata.getUserFace_Id());
            stmt.setString(2,userfacedata.getUserFace_Mail());
            stmt.setString(3,userfacedata.getUserFace_Role());
            stmt.setString(4,userfacedata.getUserFace_State());
            stmt.setString(5,userfacedata.getUserFace_Name());
            stmt.setString(6,userfacedata.getUserFace_Password());
            result = stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }

    public int updateState(String userface_id){
        int result = 0 ;
        try{
            Connection conn = dataSource.getConnection();
            String sql = "update userfacedata set UserFace_State = 0 where UserFace_Id = ? ";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,userface_id);
            result = stmt.executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }

}
