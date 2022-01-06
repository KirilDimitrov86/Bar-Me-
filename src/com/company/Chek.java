package com.company;

import java.util.Scanner;

public class Chek {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("> Matrix <");

        System.out.print("Insert number [1-20]: ");
        String numberAsText = scanner.nextLine();
        int number = Integer.parseInt(numberAsText);

        System.out.println("Result: ");

        for (int rows = 0; rows < number; rows++) {
            for (int column = rows + 1; column <= number + rows; column++) {
                System.out.print(column);
            }
            System.out.println("");
        }
    }
}