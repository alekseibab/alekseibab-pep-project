package DAO;

import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;

public class AccountDAO {

    public Account insertAccount(Account account) {

        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "INSERT INTO Account (username, password) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            return rs.next() ? new Account(rs.getInt(1), account.getUsername(), account.getPassword()) : null;
        } catch (Exception e) {return null; }

    }

    public Account getAccountByUsername(String username){

        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM Account WHERE username = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password")) : null;
        } catch (Exception e) {return null; }


    }

    public Account getAccountByID(int accountID){
        
        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM account WHERE account_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, accountID);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password")) : null;
        } catch (Exception e) {return null; }
        
    }
    
}
