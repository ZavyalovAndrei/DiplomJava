package ru.netology.graphics.server;

import ru.netology.graphics.image.Converter;

public class Main {
    public static void main(String[] args) throws Exception {
        Converter converter = new Converter();
        String url = "https://i.ibb.co/6DYM05G/edu0.jpg";
        String imgTxt = converter.convert(url);
        System.out.println(imgTxt);

        GServer server = new GServer(converter); // Создаём объект сервера
        server.start(); // Запускаем

        //Или то же, но с сохранением в файл:

//        PrintWriter fileWriter = new PrintWriter(new File("converted-image.txt", ""));
//        converter.setMaxWidth(200);
//        converter.setMaxHeight(200);
//        fileWriter.write(converter.convert("https://shoppingcenter.ru/wp-content/uploads/2019/08/sekret-merlin-monro.jpg"));
//        fileWriter.close();

    }
}