package View;

import Controller.Counter;
import Model.*;

import static Controller.Controller.*;

import java.util.*;

public class View {

    //Метод вывода меню и навигации
    public static int showMenu() {
        System.out.println();
        System.out.println("В данный момент в питомнике " + Counter.animalsQuantity + " животных");
        System.out.println("""
                Выберите необходимое действие:
                1. Вывести всю информацию о всех животных
                2. Вывести всю информацию о домашних животных
                3. Вывести всю информацию о вьючных животных
                4. Завести новое животное
                5. Вывести список команд животного
                6. Обучить животное новым командам
                7. Вывести список животных по дате рождения
                8. Закончить работу""");
        try {
            Scanner scanner = new Scanner(System.in);
            int point = scanner.nextInt();
            if (point < 1 || point > 8) {
                throw new InputMismatchException();
            }
            return point;
        } catch (InputMismatchException e) {
            System.out.println("Необходимо ввести цифровое значение от 1 до 8, повторите ввод");
            return showMenu();
        }
    }

    //Метод выведения всей информации о всех животных
    public static void showAnimalsInfo() {
        for (Animal animal : allAnimals) {
            System.out.println(animal);
        }
    }

    //Метод выведения всей информации о домашних животных
    public static void showPetsInfo() {
        for (Animal animal : allAnimals) {
            if (animal.isPet()) {
                System.out.println(animal);
            }
        }
    }

    //Метод выведения всей информации о вьючных животных
    public static void showPackAnimalsInfo() {
        for (Animal animal : allAnimals) {
            if (!animal.isPet()) {
                System.out.println(animal);
            }
        }
    }

    //Метод вывода животных по дате рождения
    public static void showSortedAnimals() {
        ArrayList<Animal> temp = new ArrayList<>(allAnimals);
        temp.sort(Animal::compareTo);
        for (Animal animal : temp) {
            System.out.println(animal);
        }
    }

    //Метод вывода команд животного
    public static void showAnimalCommands() {
        System.out.println("Введите id животного, команды которого необходимо вывести:");
        Scanner scan = new Scanner(System.in);
        int identify = scan.nextInt();
        for (Animal animal : allAnimals) {
            if (animal.getId() == identify) {
                System.out.println(animal.getCommands());
            }
        }
    }
}
