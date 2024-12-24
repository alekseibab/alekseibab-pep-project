package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO dao;

    public AccountService(AccountDAO dao) {
        this.dao = dao;
    }

    public Account registerAccount (String username, String password) {
        return (username == null 
            || username.isBlank() 
            || password.length() < 4 
            || dao.getAccountByUsername(username) != null)
            ? null
            : dao.insertAccount(new Account(username, password));
    }

    public Account login(String username, String password) {
        Account account = dao.getAccountByUsername(username);
        return (account != null && account.getPassword().equals(password)) ? account : null;

    }
    
}
