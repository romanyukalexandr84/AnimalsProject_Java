package Controller;

import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.Pattern;

import Model.*;

import static View.View.*;

public class Controller {
    public static ArrayList<Animal> allAnimals = new ArrayList<>();

    //Метод чтения файла с животными
    public static List<String> readFile(File file) {
        List<String> lst = new ArrayList<>();
        try (FileReader fr = new FileReader(file); BufferedReader bf = new BufferedReader(fr)) {
            String line;
            while ((line = bf.readLine()) != null) {
                lst.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found" + file.getName());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return lst;
    }

    //Метод записи данных в файл с животными
    public static void writeFile(List<String> lst, File file, Animal animal) {
        try (FileWriter fw = new FileWriter(file); BufferedWriter bf = new BufferedWriter(fw)) {
            for (String item : lst) {
                bf.write(item);
                bf.newLine();
            }
            bf.write(animal.toString());
        } catch (FileNotFoundException e) {
            System.out.println("File not found" + file.getName());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //Метод работы с пунктами меню
    public static void workWithMenu() throws IOException, ParseException {
        int choice = showMenu();
        if (choice != 8) {
            switch (choice) {
                case 1:
                    showAnimalsInfo();
                    break;
                case 2:
                    showPetsInfo();
                    break;
                case 3:
                    showPackAnimalsInfo();
                    break;
                case 4:
                    addNewAnimal();
                    break;
                case 5:
                    showAnimalCommands();
                    break;
                case 6:
                    trainNewCommands();
                    break;
                case 7:
                    showSortedAnimals();
                    break;
            }
        } else {
            return;
        }
        workWithMenu();
    }

    //Метод заполнения списка животных путем чтения из файла
    public static void fillAnimalsList() throws IOException, ParseException {
        File file = new File("Model/Database.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        List<String> fileContent = readFile(file);

        for (String item : fileContent) {
            String nextType = item.split(" ")[3];
            switch (nextType) {
                case "Dog" -> {
                    allAnimals.add(new Dog(Integer.parseInt(item.split(" ")[0]),
                            item.split(" ")[1],
                            new SimpleDateFormat("yyyy-MM-dd").parse(item.split(" ")[4]),
                            Boolean.parseBoolean(item.split(" ")[2]),
                            item.split(" ")[3],
                            new ArrayList<String>(Arrays.asList(item.split(" ")[5].split("/")))
                    ));
                    Counter.add();
                }
                case "Cat" -> {
                    allAnimals.add(new Cat(Integer.parseInt(item.split(" ")[0]),
                            item.split(" ")[1],
                            new SimpleDateFormat("yyyy-MM-dd").parse(item.split(" ")[4]),
                            Boolean.parseBoolean(item.split(" ")[2]),
                            item.split(" ")[3],
                            new ArrayList<String>(Arrays.asList(item.split(" ")[5].split("/")))
                    ));
                    Counter.add();
                }
                case "Hamster" -> {
                    allAnimals.add(new Hamster(Integer.parseInt(item.split(" ")[0]),
                            item.split(" ")[1],
                            new SimpleDateFormat("yyyy-MM-dd").parse(item.split(" ")[4]),
                            Boolean.parseBoolean(item.split(" ")[2]),
                            item.split(" ")[3],
                            new ArrayList<String>(Arrays.asList(item.split(" ")[5].split("/")))
                    ));
                    Counter.add();
                }
                case "Horse" -> {
                    allAnimals.add(new Horse(Integer.parseInt(item.split(" ")[0]),
                            item.split(" ")[1],
                            new SimpleDateFormat("yyyy-MM-dd").parse(item.split(" ")[4]),
                            Boolean.parseBoolean(item.split(" ")[2]),
                            item.split(" ")[3],
                            new ArrayList<String>(Arrays.asList(item.split(" ")[5].split("/")))
                    ));
                    Counter.add();
                }
                case "Camel" -> {
                    allAnimals.add(new Camel(Integer.parseInt(item.split(" ")[0]),
                            item.split(" ")[1],
                            new SimpleDateFormat("yyyy-MM-dd").parse(item.split(" ")[4]),
                            Boolean.parseBoolean(item.split(" ")[2]),
                            item.split(" ")[3],
                            new ArrayList<String>(Arrays.asList(item.split(" ")[5].split("/")))
                    ));
                    Counter.add();
                }
                default -> {
                    allAnimals.add(new Donkey(Integer.parseInt(item.split(" ")[0]),
                            item.split(" ")[1],
                            new SimpleDateFormat("yyyy-MM-dd").parse(item.split(" ")[4]),
                            Boolean.parseBoolean(item.split(" ")[2]),
                            item.split(" ")[3],
                            new ArrayList<String>(Arrays.asList(item.split(" ")[5].split("/")))
                    ));
                    Counter.add();
                }
            }
        }
    }

    //Метод заведения нового животного
    public static void addNewAnimal() throws ParseException, IOException {
        System.out.println("Введите id, который будет присвоен новому животному:");
        try {
            Scanner scan = new Scanner(System.in);
            int animalId = scan.nextInt();
            if (animalId < 1) {
                throw new InputMismatchException();
            }
            boolean existId = false;
            for (Animal item : allAnimals) {
                if (item.getId() == animalId) {
                    existId = true;
                    break;
                }
            }
            if (existId) {
                throw new IllegalArgumentException();
            }
            System.out.println("""
                    Введите тип нового животного (один из указанных ниже):
                    Dog || Cat || Hamster || Horse || Camel || Donkey""");
            String animalType = scan.next();
            if (!Arrays.asList("Dog", "Cat", "Hamster", "Horse", "Camel", "Donkey").contains(animalType)) {
                throw new ClassNotFoundException();
            }
            System.out.println("Введите имя нового животного:");
            String animalName = scan.next();
            if (!Pattern.matches("[a-zA-Zа-яА-Я]+", animalName)) {
                throw new RuntimeException();
            }
            System.out.println("Введите дату рождения нового животного (в формате ГГГГ-ММ-ДД):");
            String animalBirthdate = scan.next();
            if (!isDate(animalBirthdate)) {
                throw new IOException();
            }
            System.out.println("Введите список команд нового животного (через слэш /):");
            String animalCommands = scan.next();
            if (!Pattern.matches("[a-zA-Zа-яА-Я/]+", animalCommands)) {
                throw new Exception();
            }
            switch (animalType) {
                case "Dog":
                    allAnimals.add(new Dog(animalId, animalName, new SimpleDateFormat("yyyy-MM-dd").parse(animalBirthdate),
                            true, animalType, new ArrayList<String>(Arrays.asList(animalCommands.split("/")))));
                    Counter.add();
                    break;
                case "Cat":
                    allAnimals.add(new Cat(animalId, animalName, new SimpleDateFormat("yyyy-MM-dd").parse(animalBirthdate),
                            true, animalType, new ArrayList<String>(Arrays.asList(animalCommands.split("/")))));
                    Counter.add();
                    break;
                case "Hamster":
                    allAnimals.add(new Hamster(animalId, animalName, new SimpleDateFormat("yyyy-MM-dd").parse(animalBirthdate),
                            true, animalType, new ArrayList<String>(Arrays.asList(animalCommands.split("/")))));
                    Counter.add();
                    break;
                case "Horse":
                    allAnimals.add(new Horse(animalId, animalName, new SimpleDateFormat("yyyy-MM-dd").parse(animalBirthdate),
                            false, animalType, new ArrayList<String>(Arrays.asList(animalCommands.split("/")))));
                    Counter.add();
                    break;
                case "Camel":
                    allAnimals.add(new Camel(animalId, animalName, new SimpleDateFormat("yyyy-MM-dd").parse(animalBirthdate),
                            false, animalType, new ArrayList<String>(Arrays.asList(animalCommands.split("/")))));
                    Counter.add();
                    break;
                default:
                    allAnimals.add(new Donkey(animalId, animalName, new SimpleDateFormat("yyyy-MM-dd").parse(animalBirthdate),
                            false, animalType, new ArrayList<String>(Arrays.asList(animalCommands.split("/")))));
                    Counter.add();
                    break;
            }

            File file = new File("Model/Database.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            List<String> fileContent = readFile(file);
            writeFile(fileContent, file, allAnimals.get(allAnimals.size() - 1));
            System.out.println("Животное добавлено в питомник");
        } catch (InputMismatchException e) {
            System.out.println("Id должен быть в цифровом формате и больше нуля, повторите ввод");
            addNewAnimal();
        } catch (IllegalArgumentException e) {
            System.out.println("Животное с таким id уже есть, повторите ввод");
            addNewAnimal();
        } catch (ClassNotFoundException e) {
            System.out.println("Такого типа животных нет в питомнике, повторите ввод");
            addNewAnimal();
        } catch (RuntimeException e) {
            System.out.println("Имя животного должно быть в текстовом формате, повторите ввод");
            addNewAnimal();
        } catch (IOException e) {
            System.out.println("Дата должна быть в формате ГГГГ-ММ-ДД, повторите ввод");
            addNewAnimal();
        } catch (Exception e) {
            System.out.println("Команды следует вводить в текстовом формате через слэш (/), повторите ввод");
            addNewAnimal();
        }
    }

    //Метод обучения животного новым командам
    public static void trainNewCommands() throws IOException {
        System.out.println("Введите id животного, которого необходимо обучить:");
        try {
            Scanner scan = new Scanner(System.in);
            int identify = scan.nextInt();
            boolean existId = false;
            for (Animal item : allAnimals) {
                if (item.getId() == identify) {
                    existId = true;
                    break;
                }
            }
            if (!existId) {
                throw new NoSuchElementException();
            }
            System.out.println("Введите новые команды животного (через слэш /):");
            String newCommands = scan.next();
            if (!Pattern.matches("[a-zA-Zа-яА-Я/]+", newCommands)) {
                throw new RuntimeException();
            }
            File file = new File("Model/Database.txt");
            file.delete();
            file.createNewFile();
            for (Animal animal : allAnimals) {
                if (animal.getId() == identify) {
                    animal.getCommands().addAll(new ArrayList<String>(Arrays.asList(newCommands.split("/"))));
                }
                List<String> fileContent = readFile(file);
                writeFile(fileContent, file, animal);
            }
        } catch (InputMismatchException e) {
            System.out.println("Id должен быть в цифровом формате, повторите ввод");
            trainNewCommands();
        } catch (NoSuchElementException e) {
            System.out.println("Животного с указанным id не существует, повторите ввод");
            trainNewCommands();
        } catch (RuntimeException e) {
            System.out.println("Команды следует вводить в текстовом формате через слэш (/), повторите ввод");
            trainNewCommands();
        }
    }

    //Метод проверки, является ли переданное значение датой
    public static boolean isDate(String val) {
        try {
            new SimpleDateFormat("yyyy-MM-dd").parse(val);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
