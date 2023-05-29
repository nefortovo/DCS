package ru.liftcom.bot.component;

import lombok.Getter;
import lombok.SneakyThrows;
import org.checkerframework.checker.units.qual.K;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.liftcom.bot.service.Producer;
import ru.liftcom.database.entity.CustomOrder;
import ru.liftcom.database.entity.CustomUser;
import ru.liftcom.database.service.OrderService;
import ru.liftcom.database.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
public class BotComponent extends TelegramLongPollingBot {

    private Message requestMessage = new Message();
    private final SendMessage response = new SendMessage();
    private final Producer producerService;

    private final String botUsername;
    private final String botToken;

    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public BotComponent(TelegramBotsApi telegramBotsApi,
                        @Value("${bot.name}") String botUsername,
                        @Value("${bot.key}") String botToken,
                        Producer producerService,
                        UserService userService,
                        OrderService orderService) throws TelegramApiException {
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.producerService = producerService;
        this.userService = userService;
        this.orderService = orderService;

        telegramBotsApi.registerBot(this);
    }

    private ReplyKeyboardMarkup setMarkup(){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<KeyboardRow>();
        KeyboardRow row = new KeyboardRow();
        row.add("Получить заказ");
        row.add("Кол-во заказов");
        keyboardRows.add(row);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        replyKeyboardMarkup.setResizeKeyboard(true);

        return replyKeyboardMarkup;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if(!(update.hasMessage() || requestMessage.hasText())){
            return;
        }

        requestMessage = update.getMessage();
        response.setChatId(requestMessage.getChatId().toString());
        String userName = requestMessage.getChat().getUserName();

        CustomUser entity = userService.findByName(userName);
        if (entity == null){
            defaultMsg(response, "Неавторизованный вход");
            return;
        }
        else if(entity.getChatId() == null && !requestMessage.getText().equals("/start")){
            needReg(response);
            return;
        }

        if(requestMessage.getText().equals("/start")){
            defaultMsg(response, "вы успешно зарегистрированы");
            entity.setChatId(requestMessage.getChatId().toString());
            userService.saveUser(entity);
        }
        else if (requestMessage.getText().equals("Получить заказ")){
            CustomOrder customOrder = orderService.getOrder();
            if (customOrder == null){
                defaultMsg(response, "нет доступных заказов");
                return;
            }
            defaultMsg(response, "заказ:");
            defaultMsg(response, customOrder.getName());
            defaultMsg(response, customOrder.getPhone());
        }
        else if (requestMessage.getText().equals("Кол-во заказов")){
            defaultMsg(response, "заказов: " + orderService.amount());
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    private void needReg(SendMessage response) throws TelegramApiException {
        response.setText("Введите /start чтобы начать");
        execute(response);
    }

    private void defaultMsg(SendMessage response, String msg) throws TelegramApiException {
        ReplyKeyboardMarkup replyKeyboardMarkup = setMarkup();
        response.setReplyMarkup(replyKeyboardMarkup);
        response.setText(msg);
        execute(response);
    }

    public void sendToAll(String msg) throws TelegramApiException{
        List<CustomUser> customUsers = userService.getAll();
        for(CustomUser i : customUsers){
            String chatId = i.getChatId();
            if(i.getChatId() != null){
                response.setChatId(chatId);
                defaultMsg(response, msg);
            }
        }
    }

}
