package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Model.Message;
import DAO.AccountDAO;
import DAO.MessageDAO;
import Service.AccountService;
import Service.MessageService;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService = new AccountService(new AccountDAO());
    MessageService messageService = new MessageService(new MessageDAO(), new AccountDAO());

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

    public Javalin startAPI() {

        

        Javalin app = Javalin.create();

        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{id}", this::getMessageByIdHandler);
        app.delete("/messages/{id}", this::deleteMessageHandler);
        app.patch("/messages/{id}", this::updateMessageHandler);
        app.get("/accounts/{id}/messages", this::getMessagesByUserIdHandler);


        app.get("example-endpoint", this::exampleHandler);

        return app;

    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */





    private void exampleHandler(Context context) {

        context.json("sample text");

    }

    private void registerHandler(Context context) {
        Account account = context.bodyAsClass(Account.class);
        Account created = accountService.registerAccount(account.getUsername(), account.getPassword());
        if (created != null) {
            context.json(created);
        } else {
            context.status(400).result("");
        }
    }

    private void loginHandler (Context context) {
        Account account = context.bodyAsClass(Account.class);
        Account loggedIn = accountService.login(account.getUsername(), account.getPassword());
        if (loggedIn != null) {
            context.json(loggedIn);
        } else {
            context.status(401).result("");
        }
    }

    private void createMessageHandler (Context context) {
        Message message = context.bodyAsClass(Message.class);
        Message created = messageService.createMessage(message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
        if (created != null) {
            context.json(created);
        } else {
            context.status(400).result("");
        }

    }

    private void getAllMessagesHandler (Context context) {
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }
    
    private void getMessageByIdHandler (Context context) {
        int id = Integer.parseInt(context.pathParam("id"));
        Message message = messageService.getMessageById(id);
        if (message != null) {
            context.json(message);
        } else {
            context.status(200).result("");
        }
    }

    private void deleteMessageHandler (Context context) {
        int id = Integer.parseInt(context.pathParam("id"));
        Message deletedMessage = messageService.getMessageById(id);
        if (deletedMessage != null && messageService.deleteMessage(id)) {
            context.json(deletedMessage);
        } else {
            context.status(200).result("");
        }
    }

    private void updateMessageHandler (Context context) {
        int id = Integer.parseInt(context.pathParam("id"));
        Message existingMessage = messageService.getMessageById(id);

        if (existingMessage == null) {
            context.status(400).result("");
            return;
        }

        Message updatedMessageData = context.bodyAsClass(Message.class);
        existingMessage.setMessage_text(updatedMessageData.getMessage_text());

        Message updatedMessage = messageService.updatMessage(existingMessage);


        if (updatedMessage != null) {
            context.json(updatedMessage);
        } else {
            context.status(400).result("");
        }
    }

    private void getMessagesByUserIdHandler (Context context) {
        int id = Integer.parseInt(context.pathParam("id"));
        List<Message> messages = messageService.getMessagesByUserId(id);
        context.json(messages);
    }

}