package com.company;

import java.util.Scanner;

public class Main {
    public static String[][] data = {
            {"Тракийска принцеса", "43.204797", "23.552098", "8:30", "0:00"},
            {"Бижу", "43.205215", "23.556284", "6:00", "2:00"},
            {"Винарна", "43.205612", "23.554023", "10:30", "1:30"},
            {"Корона", "43.203101, 23.549178", "8:00", "0:00"},
            {"Карамел", "43.201959, 23.549060", "8:00", "0:00"},
            {"Герана", "43.201783, 23.549771", "17:00", "0:00"},
            {"Нашенци", "43.200133, 23.556595", "11:00", "23:00"},
            {"Златен дракон", "43.205154, 23.548038", "11:30", "22:00"},
            {"Наполи", "43.206990, 23.546638", "11:00", "23:00"},
            {"Вили", "43.206077, 23.552352", "8:00", "0:00"},
            {"Пинтата", "43.206276, 23.551410", "11:00", "0:00"},
            {"Ола", "43.204952, 23.553547", "7:00", "23:00"},
            {"Славиански кът", "43.207988, 23.548769", "7:00", "23:00"},
            {"Българе", "43.199471, 23.558079", "10:00", "1:00"}
    };

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String userLatitude = scanner.nextLine();
        String userLongitude = scanner.nextLine();
        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1:
                listOfAll();
                break;
            case 2:
                listOpen();
                break;
            case 3:
                map();
                break;
            default:
                System.out.println("Invalid input");
        }

    }

//    public static void barInfo() {
//        String[] tracianPrinsses = {"Тракийска принцеса", "43.204797", "23.552098", "8:30", "0:00"};//1
//        String[] biju = {"Бижу", "43.205215", "23.556284", "6:00", "2:00"};//2
//        String[] wineHouse = {"Винарна", "43.205612", "23.554023", "10:30", "1:30"};//3
//        String[] corona = {"Корона", "43.203101, 23.549178", "8:00", "0:00"};//4
//        String[] caramel = {"Карамел", "43.201959, 23.549060", "8:00", "0:00"};//5
//        String[] gerana = {"Герана", "43.201783, 23.549771", "17:00", "0:00"};//6
//        String[] nashenci = {"Нашенци", "43.200133, 23.556595", "11:00", "23:00"};//7
//        String[] goldenDragon = {"Златен дракон", "43.205154, 23.548038", "11:30", "22:00"};//8
//        String[] napoli = {"Наполи", "43.206990, 23.546638", "11:00", "23:00"};//9
//        String[] vili = {"Вили", "43.206077, 23.552352", "8:00", "0:00"};//10
//        String[] pintata = {"Пинтата", "43.206276, 23.551410", "11:00", "0:00"};//11
//        String[] ola = {"Ола", "43.204952, 23.553547", "7:00", "23:00"};//12
//        String[] slavianskiKyt = {"Славиански кът", "43.207988, 23.548769", "7:00", "23:00"};//13
//        String[] bulgare = {"Българе", "43.199471, 23.558079", "10:00", "1:00"};//14
//
//
//    }
    public static double distance(double lat1, double lon1, double lat2, double lon2){ //distance is in km.

        int radius = 6371; //km

               double difLat = Math.toRadians(lat2-lat1);
        double difLon = Math.toRadians(lon2-lon1);
        double a = Math.sin(difLat/2) * Math.sin(difLat/2) + Math.cos(Math.toRadians(lat1))
        * Math.cos(Math.toRadians(lat2)) * Math.sin(difLon/2) * Math.sin(difLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = radius * c;

        return d;
    }

    private static void map() {
    }

    private static void listOpen() {
    }

    private static void listOfAll() {
    }
}
