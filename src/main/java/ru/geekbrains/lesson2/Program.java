package ru.geekbrains.lesson2;

import java.util.Random;
import java.util.Scanner;

public class Program {


    private static final  int WIN_COUNT = 4;
    private static final char DOT_HUMAN = 'X';
    private static final char DOT_AI = 'O';
    private static final char DOT_EMPTY = '•';

    private static final Scanner SCANNER = new Scanner(System.in);

    private static char[][] field; // Двумерный массив хранит текущее состояние игрового поля

    private static final Random random = new Random();

    private static int fieldSizeX; // Размерность игрового поля
    private static int fieldSizeY; // Размерность игрового поля


    public static void main(String[] args) {
        while (true){
            initialize();
            printField();
            while (true){
                humanTurn();
                printField();
                if (gameCheck(DOT_HUMAN, "Вы победили!"))
                    break;
                aiTurn();
                printField();
                if (gameCheck(DOT_AI, "Компьютер победил!"))
                    break;
            }
            System.out.println("Желаете сыграть еще раз? (Y - да)");
            if (!SCANNER.next().equalsIgnoreCase("Y"))
                break;
        }
    }

    /**
     * Инициализация игрового поля
     */
    private static void initialize(){
        // Установим размерность игрового поля
        fieldSizeX = 5;
        fieldSizeY = 5;


        field = new char[fieldSizeX][fieldSizeY];
        // Пройдем по всем элементам массива
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                // Проинициализируем все элементы массива DOT_EMPTY (признак пустого поля)
                field[x][y] = DOT_EMPTY;
            }
        }
    }

    /**
     * Отрисовка игрового поля
     * //TODO: Поправить отрисовку игрового поля
     */
    private static void printField(){
        System.out.print("+");
        for (int i = 0; i < fieldSizeX * 2 + 1; i++){
            System.out.print((i % 2 == 0) ? "-" : i / 2 + 1);
        }
        System.out.println();

        for (int i = 0; i < fieldSizeX; i++){
            System.out.print(i + 1 + "|");

            for (int j = 0; j <  fieldSizeY; j++)
                System.out.print(field[i][j] + "|");

            System.out.println();
        }

        for (int i = 0; i < fieldSizeX * 2 + 2; i++){
            System.out.print("-");
        }
        System.out.println();

    }

    /**
     * Обработка хода игрока (человек)
     */
    private static void humanTurn(){
        int x, y;
        do
        {
            System.out.print("Введите координаты хода X и Y (от 1 до 3) через пробел >>> ");
            x = SCANNER.nextInt() - 1;
            y = SCANNER.nextInt() - 1;
        }
        while (!isCellValid(x, y) || !isCellEmpty(x, y));
        field[x][y] = DOT_HUMAN;
    }

    /**
     * Проверка, ячейка является пустой
     * @param x
     * @param y
     * @return
     */
    static boolean isCellEmpty(int x, int y){
        return field[x][y] == DOT_EMPTY;
    }

    /**
     * Проверка корректности ввода
     * (координаты хода не должны превышать размерность массива, игрового поля)
     * @param x
     * @param y
     * @return
     */
    static boolean isCellValid(int x, int y){
        return x >= 0 &&  x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    /**
     * Ход компьютера
     */
    private static void aiTurn(){
        int x, y;
        do
        {
            x = random.nextInt(fieldSizeX);
            y = random.nextInt(fieldSizeY);
        }
        while (!isCellEmpty(x, y));
        field[x][y] = DOT_AI;
    }

    /**
     * Проверка победы
     * TODO: Переработать метод в домашнем задании
     * @param c
     * @return
     */
//    static boolean checkWin(char c){
//        // Проверка по трем горизонталям
//        if (field[0][0] == c && field[0][1] == c && field[0][2] == c) return true;
//        if (field[1][0] == c && field[1][1] == c && field[1][2] == c) return true;
//        if (field[2][0] == c && field[2][1] == c && field[2][2] == c) return true;
//
//        // Проверка по диагоналям
//        if (field[0][0] == c && field[1][1] == c && field[2][2] == c) return true;
//        if (field[0][2] == c && field[1][1] == c && field[2][0] == c) return true;
//
//        // Проверка по трем вертикалям
//        if (field[0][0] == c && field[1][0] == c && field[2][0] == c) return true;
//        if (field[0][1] == c && field[1][1] == c && field[2][1] == c) return true;
//        if (field[0][2] == c && field[1][2] == c && field[2][2] == c) return true;
//
//        return false;
//    }

    static boolean checkWin(char c){
        int count=0;
        //проверка по горизонтали
        for (int i = 0; i < fieldSizeX; i++) {
            for (int j = 0; j < fieldSizeY-1; j++) {
                if (count == WIN_COUNT - 1) {
                    return true;
                } else {
                    if (field[i][j] == c && field[i][j + 1] == c) {
                        count++;
                    }else {
                        count=0;
                    }
                }
            }
        }
        //проверка по вертикали
        count=0;
        for (int i = 0; i < fieldSizeX; i++) {
            for (int j = 0; j < fieldSizeY - 1; j++) {
                if (count == WIN_COUNT - 1) {
                    return true;
                } else {
                    if (field[j][i] == c && field[j+1][i] == c) {
                        count++;
                    }else {
                        count=0;
                    }
                }
            }
        }
        // проверка по диагонали
        count = 0;
        for (int i = 0; i < fieldSizeX; i++) {
            for (int j = 0; j < fieldSizeY; j++) {
                if (fieldSizeX - i >= WIN_COUNT && fieldSizeY - j >= WIN_COUNT){
                    int pos=0;
                    if (i>=j){
                        pos=i;
                    }else {
                        pos=j;
                    }
                    for (int k = pos; k < fieldSizeY; k++) {
                        if (count == WIN_COUNT - 1){
                            return true;
                        }else {
                            if (field[i][j] == c && field[i + 1][j + 1] == c) {
                                count++;
                            } else {
                                count = 0;
                            }
                        }
                        i++;
                        j++;
                    }
                }


            }
        }
        for (int g = 0; g < fieldSizeX; g++) {
            for (int j = 0; j < fieldSizeY; j++) {
               int i = g;
                if (i+1>=WIN_COUNT && fieldSizeY - j >= WIN_COUNT){
                    int pos = 0;
                    if ((i+1) <= (fieldSizeY - j)){
                        pos = i+1;
                    }
                    if ((i+1) > (fieldSizeY - j)){
                        pos = fieldSizeY - j;
                    }

                    for (int k = pos; k > 0; k--) {
                        if (count == WIN_COUNT - 1){
                            return true;
                        }else {
                            if (field[i][j] == c && field[i - 1][j + 1] == c) {
                                count++;
                            } else {
                                count = 0;
                            }
                        }
                        i--;
                        j++;
                    }

                }
            }

        }


        return false;
    }

    /**
     * Проверка на ничью
     * @return
     */
    static boolean checkDraw(){
        for (int x = 0; x < fieldSizeX; x++){
            for (int y = 0; y < fieldSizeY; y++)
                if (isCellEmpty(x, y)) return false;
        }
        return true;
    }

    /**
     * Метод проверки состояния игры
     * @param c
     * @param str
     * @return
     */
    static boolean gameCheck(char c, String str){
        if (checkWin(c)){
            System.out.println(str);
            return true;
        }
        if (checkDraw()){
            System.out.println("Ничья!");
            return true;
        }

        return false; // Игра продолжается
    }

}
