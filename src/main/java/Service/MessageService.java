package Service;

import DAO.MessageDAO;
import Model.Message;
import DAO.AccountDAO;
import java.util.List;


public class MessageService {
    private MessageDAO dao;
    private AccountDAO accountDAO;

    public MessageService(MessageDAO dao, AccountDAO accountDAO) {
        this.dao = dao;
        this.accountDAO = accountDAO;
    }

    public Message createMessage(int userId, String text, long time) {
        return (text == null || text.isBlank() || text.length() > 255 || accountDAO.getAccountByID(userId) == null )
                ? null
                :dao.insertMessage(new Message(userId, text, time));

    }

    public Message updatMessage(Message message) {
        if (message.getMessage_text() == null || message.getMessage_text().isBlank() || message.getMessage_text().length() > 255) {
            return null;
        }
        boolean isUpdated = dao.updateMessage(message);
        return isUpdated ? message : null;
    }

    public List<Message> getAllMessages() {return dao.getAllMessages(); }

    public Message getMessageById(int id) {return dao.getMessageById(id); }

    public boolean deleteMessage(int id) {return dao.deleteMessage(id); }

    public List<Message> getMessagesByUserId(int userId) {return dao.getMessagesByUserId(userId); }
    
}
