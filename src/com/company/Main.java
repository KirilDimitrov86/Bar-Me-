package com.company;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static String[][] data = {
            {"Тракийска принцеса", "43.204797, 23.552098", "8:30", "0:00"},
            {"Бижу", "43.205215, 23.556284", "6:00", "2:00"},
            {"Винарна", "43.205612, 23.554023", "10:30", "1:30"},
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
        double userLatitude = Double.parseDouble(scanner.nextLine());
        double userLongitude = Double.parseDouble(scanner.nextLine());
        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1:
                listOfAll(userLatitude, userLongitude);
                break;
            case 2:
                listOpen(userLatitude, userLongitude);
                break;
            case 3:
                map(userLatitude, userLongitude);
                break;
            default:
                System.out.println("Invalid input");
        }

    }

    public static double distance(double userLat, double userLon, double barLat, double barLon) { //distance is in km.

        int radius = 6371; //km

        double difLat = Math.toRadians(barLat - userLat);
        double difLon = Math.toRadians(barLon - userLon);
        double a = Math.sin(difLat / 2) * Math.sin(difLat / 2) + Math.cos(Math.toRadians(userLat))
                * Math.cos(Math.toRadians(barLat)) * Math.sin(difLon / 2) * Math.sin(difLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = radius * c;

        return d;
    }

    private static void map(double userLat, double userLon) {
    }

    private static void listOpen(double userLat, double userLon) {
    }

    private static void listOfAll(double userLat, double userLon) {


        for (int dataIndex = 0; dataIndex < data.length; dataIndex++) {
            double barLat = getBarCoordinates(dataIndex, 0);
            double barLon = getBarCoordinates(dataIndex, 1);
            data[dataIndex][1] = "" + distance(userLat, userLon, barLat, barLon);

        }
    }

    private static void printData(String[][] sortedData) {
        System.out.println(Arrays.toString(sortedData));
    }

//    private static String[][] sortDataByDistance() {
//        for (int i = 1; i < data.length; i++) {
//            int j = i;
//            data [j][1]= Double.parseDouble();
//            data[j][1] =
//            while (j > 0 && data[j - 1][1] > data[j][1]) {
//                int swap = array[j - 1];
//                array[j - 1] = array[j];
//                array[j] = swap;
//                j = j - 1;
//            }
//        }
//
//        return data ;
//    }


    private static double getBarCoordinates(int dataIndex, int latAndLonIndex) {  // TO DO rename latAndLonIndex
        return Double.parseDouble(splitLatAndLon(dataIndex)[latAndLonIndex]);
    }

    public static String[] splitLatAndLon(int dataIndex) {
        String[] latAndLon = data[dataIndex][1].split(", ");

        return latAndLon;
    }
}
