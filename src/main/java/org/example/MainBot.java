package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class MainBot extends TelegramLongPollingBot {

    private static final String token = "6280544617:AAESq_Xb2SNX0_Xkwhk96EYDKo5y3YAbOcA";


    private static final String levelSuffix = "level";
    private static List<String> gifNames = new ArrayList<>();

    private static String dirImagesPath = "./images";

    private static List<String> levelCommands = new ArrayList<>();

    private static List<String> buttonCommands = new ArrayList<>() {{
        add("glory_for_ukraine");
        add("glory_for_nation");
        add("glory_for_hero");
        add("death_to_enemies");
    }};

    private static List<String> buttonLevelsCommands = new ArrayList<>() {{
        add("level-1-task");
        add("level-2-task");
        add("level-3-task");
        add("level-4-task");
    }};

    private static List<String> buttonsNames = new ArrayList<>() {{
        add("Слава Україні!");
        add("Героям Слава!");
        add("Слава нації!");
        add("Смерть ворогам!");
    }};

    private static List<List<String>> buttonsLevelNames = new ArrayList<>() {{
        // level-1
        add(List.of(
                "Сплести маскувальну сітку (+15 монет)",
                "Зібрати кошти патріотичними піснями (+15 монет)",
                "Вступити в Міністерство Мемів України (+15 монет)",
                "Запустити волонтерську акцію (+15 монет)",
                "Вступити до лав тероборони (+15 монет)"
        ));

        // level-2
        add(List.of(
                "Зібрати комарів для нової біологічної зброї (+15 монет)",
                "Пройти курс молодого бійця (+15 монет)",
                "Задонатити на ЗСУ (+15 монет)",
                "Збити дрона банкою огірків (+15 монет)",
                "Зробити запаси коктейлів Молотова (+15 монет)"
        ));

        // level-3
        add(List.of(
                "Злітати на тестовий рейд по чотирьох позиціях (+15 монет)",
                "Відвезти гуманітарку на передок (+15 монет)",
                "Знайти зрадника та здати в СБУ (+15 монет)",
                "Навести арту на орків (+15 монет)",
                "Притягнути танк трактором (+15 монет)"
        ));

        // level-4
        add(List.of(
                "Купити Джавелін (50 монет)"
        ));
    }};

    private static final int buttonCount = 3;

    private static List<Map<String, String>> buttons = new ArrayList<>() {{
        for (int i = 0; i < buttonsLevelNames.size(); i++) {
            List<String> levelButtonNames = buttonsLevelNames.get(i);
            Map<String, String> levelButtons = new HashMap<>();
            for (int j = 0; j < levelButtonNames.size(); j++) {
                if (levelButtonNames.size() >= 3) {
                    break;
                }
                String buttonName = levelButtonNames.get(j);
                levelButtons.put(buttonName, buttonLevelsCommands.get(i));
            }
            add(levelButtons);
        }
//        for (int i = 0, buttonCommandsSize = buttonCommands.size(); i < buttonCommandsSize; i++) {
////            add()
//            add(Map.of(buttonsNames.get(i), buttonCommands.get(i)));
//        }
    }};

    public static void main(String[] args) throws TelegramApiException {
        init();
        showData();

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new MainBot());
    }


    private static void showData() {
        gifNames.forEach(System.out::println);
        System.out.println();
        levelCommands.forEach(System.out::println);
    }
    private static void init() {
        setGifs();
        setLevels();
    }

    private static void setLevels() {
        levelCommands.addAll(gifNames.stream()
                .filter(gifName -> gifName.contains(levelSuffix))
                .map(s -> s.split("\\.")[0])
                .collect(Collectors.toList()));
    }

    private static void setGifs() {
        File imagesDir = new File(dirImagesPath);
        if (imagesDir.exists() && imagesDir.isDirectory()) {
            gifNames.addAll(Arrays.stream(Objects.requireNonNull(imagesDir.listFiles())).map(File::getName).sorted().collect(Collectors.toList()));
        }
    }

    @Override
    public String getBotUsername() {
        return "BanderaGuse111Bot";
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().getText().equals("/start")) {
//            sendMessageWithButtons(update, buttonsNames.get(0), 0);
        }
        if (update.hasCallbackQuery()) {
            chooseCommand(update.getCallbackQuery().getData(), update);
        }
    }

    private void chooseCommand(String data, Update update) {
        for (int i = 0; i < buttonLevelsCommands.size(); i++) {
            if (data.equals(buttonLevelsCommands.get(i))) {
//                sendButtonsWithImage()
//                sendMessageWithButtons(update, buttonsNames.get(i + 1), i + 1);
            }
            if (i >= buttonLevelsCommands.size()) {
                i = 0;
            }
        }
    }

//    private void sendMessageWithButtons(Update update, String text, int mapNumber) {
//        sendApiMethodAsync(createMessageAndAttachButtons(update, text, mapNumber));
//    }

//    private SendMessage createMessageAndAttachButtons(Update update, String text, int mapNumber) {
//        SendMessage message = createMessage(text, getChatId(update));
//        attachButtons(message, mapNumber);
//        return message;
//    }

    public Long getChatId(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getFrom().getId();
        }

        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getFrom().getId();
        }
        return null;
    }

    public SendMessage createMessage(String txt, Long chatId) {
        return createMessage().text(txt).chatId(chatId).build();
    }


    public SendMessage createMessage(String txt) {
        return createMessage().text(txt).build();
    }

    public SendMessage.SendMessageBuilder createMessage() {
        return SendMessage.builder().parseMode("markdown");
    }

    private void attachButtons(SendMessage message, int mapNumber) {
        attachButtons(message, buttons.get(mapNumber));
    }

    private void attachButtons(SendMessage sendMessage, Map<String, String> buttons) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>() {{
            buttons.forEach((key, value) -> add(Collections.singletonList(InlineKeyboardButton.builder()
                    .text(key)
                    .callbackData(value)
                    .build())));
        }};

        sendMessage.setReplyMarkup(InlineKeyboardMarkup.builder().keyboard(keyboard).build());
    }



    private void sendButtonsWithImage(String imageName, Long chatId, String text, int mapButtonsNumber) throws TelegramApiException {
        SendMessage message = createMessage(text, chatId);
        attachButtons(message, mapButtonsNumber);
        sendImage(imageName, chatId);
        executeAsync(message);
    }

    public void sendImage(String name, Long chatId) {
        SendAnimation animation = new SendAnimation();
        InputFile inputFile = new InputFile();
        inputFile.setMedia(new File("images/" + name + ".gif"));

        animation.setAnimation(inputFile);
        animation.setChatId(chatId);

        executeAsync(animation);
    }

}