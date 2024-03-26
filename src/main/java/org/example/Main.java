package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main{

    public static void main(String[] args) {
        while (true) {
            try {userData();
                break;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Неверный формат числа. " + e.getMessage());
                System.out.println("Пожалуйста, введите данные заново.");
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage());
                System.out.println("Пожалуйста, введите данные заново.");
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static void userData() throws IllegalArgumentException, ParseException, NumberFormatException {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        format.setLenient(false);
        String surname = "";
        String name = "";
        String dName = "";
        Date db = null;
        long phoneNumber = 0;
        char gender = ' ';

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите данные: Фамилия Имя Отчество дата_рождения(формат dd.mm.yyyy) номер_телефона пол(формат m/f)");
        String data = scanner.nextLine();

        String[] parts = data.split(" ");

        if (parts.length < 6) {
            throw new IllegalArgumentException("Неверное количество данных");
        }
        if (parts.length > 6) {
            throw new IllegalArgumentException("Неверное количество данных");
        }

        for (String part : parts) {
            if (Pattern.matches("\\d{2}\\.\\d{2}\\.\\d{4}", part)) {

                try {
                    if (db == null) {
                        db = format.parse(part);
                    } else {
                        throw new IllegalArgumentException("Дата рождения уже задана");
                    }
                } catch (ParseException e) {
                    throw new IllegalArgumentException("Неверный формат даты рождения. Используйте формат dd.MM.yyyy");
                }
            }
            else if (Pattern.matches("\\d+", part)) {
                if (phoneNumber == 0) {
                    phoneNumber = Long.parseLong(part);
                } else {
                    throw new IllegalArgumentException("Вы ввели номер телефона несколько раз!");
                }
            } else if (Pattern.matches("[mf]", part)) {
                if (gender == ' ') {
                    gender = part.charAt(0);
                } else {
                    throw new IllegalArgumentException("Пол уже задан!");
                }
            } else {
                if (surname.isEmpty()) {
                    surname = part;
                } else if (name.isEmpty()) {
                    name = part;
                } else if (dName.isEmpty()) {
                    dName = part;
                }
            }
        }
        if (gender != 'm' && gender != 'f') {
            throw new IllegalArgumentException("Неправильный формат пола. Пол должен соответствовать формату 'm' " +
                    "или 'f'!");
        }

        if (surname.isEmpty() || name.isEmpty() || dName.isEmpty() || db == null ||
                phoneNumber == 0 || gender == ' ') {
            throw new IllegalArgumentException("Не все данные были введены или данные оставлены 'по умолченаию'!");
        }

        if (!surname.matches("[A-Za-zА-Яа-я]+")) {
            throw new IllegalArgumentException("Неправильный формат фамилии");
        }
        if (!name.matches("[A-Za-zА-Яа-я]+")) {
            throw new IllegalArgumentException("Неправильный формат имени");
        }
        if (!dName.matches("[A-Za-zА-Яа-я]+")) {
            throw new IllegalArgumentException("Неправильный формат отчества");
        }

        String formattedDate = (db != null) ? format.format(db) : "N/A";

        String output =  surname + " " + name + " " + dName + " " + format.format(db)
                + " " + phoneNumber + " " + gender;

        try {
            File file = new File("C:\\Users\\mrdav\\IdeaProjects\\exciskl\\src\\main\\java\\org\\example\\userInfo" + surname + ".txt");
            if (file.createNewFile()) {
                System.out.println("Файл успешно создан");
            } else {
                System.out.println("Файл уже существует и будет изменён");
            }
        } catch (IOException e) {
            System.out.println("Ошибка при создании файла! Проверьте стэктрейc");
            e.printStackTrace();
        }

        FileWriter writer = null;

        try {
            writer = new FileWriter("C:\\Users\\mrdav\\IdeaProjects\\exciskl\\src\\main\\java\\org\\example\\userInfo" + surname + ".txt", true);
            writer.write(output);
            writer.write("\n");
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
}