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

    private static void listOfAll(double userLat, double userLon) { // TODO rename latAndLon

        double[] distanceOfBars = new double[data.length];
        for (int dataIndex = 0; dataIndex < data.length; dataIndex++) {
            double barLat = getBarCoordinates(dataIndex, 0);
            double barLon = getBarCoordinates(dataIndex, 1);
            data[dataIndex][1] = "" + distance(userLat, userLon, barLat, barLon);
            distanceOfBars[dataIndex] = Double.parseDouble(data[dataIndex][1]);
        }

        print2DArray(sortDataByDistance(data,sortDataByDistance(distanceOfBars)));
    }

    private static void listOpen(double userLat, double userLon) { // TODO rename latAndLon

        double[] closingTimeOfBars = new double[data.length];
        for (int dataIndex = 0; dataIndex < data.length; dataIndex++){
            double barLat = getBarCoordinates(dataIndex, 0);
            double barLon = getBarCoordinates(dataIndex, 1);
            data[dataIndex][1] = "" + distance(userLat, userLon, barLat, barLon);
            closingTimeOfBars[dataIndex] = Double.parseDouble(data[dataIndex][data.length-1]);
        }
        print2DArray(sortDataByDistance(data,sortDataByDistance(closingTimeOfBars)));
    }

    private static void map(double userLat, double userLon) { // TODO rename latAndLon
    }

    private static double[] sortDataByDistance(double[] data) {  //TODO rename data
        for (int i = 1; i < data.length; i++) {
            int j = i;
            while (j > 0 && data[j - 1] > data[j]) {
                double swap = data[j - 1];
                data[j - 1] = data[j];
                data[j] = swap;
                j = j - 1;
            }
        }

        return data;
    }
    
    private static double getBarCoordinates(int arrayIndex, int elementIndex) {
        return Double.parseDouble(splitArrayElement(arrayIndex,1,", ")[elementIndex]);
    }

    public static String[] splitArrayElement(int arrayIndex, int elementIndex, String splitBy ) { // TODO rename
        String[] latAndLon = data[arrayIndex][elementIndex].split(splitBy);

        return latAndLon;
    }

    public static String[] distanceOfBars(double[] data) {
        String[] distanceOfBars = new String[data.length];
        for (int i = 0; i < data.length; i++) {
            distanceOfBars[i] = "" + data[i];
        }
        return distanceOfBars;
    }

    public static void print2DArray(String[][] toByPrinted) {
        for (int i = 0; i < toByPrinted.length; i++) {
            System.out.print(i+1 + "\t ");
            for (int j = 0; j < toByPrinted[i].length; j++) {
                System.out.print("| " + toByPrinted[i][j] + "\t\t\t\t\t\t\t\t");
            }
            System.out.println();
        }
    }

    public static void printArray(double[] toByPrinted) {
        for (int i = 0; i < toByPrinted.length; i++) {
            System.out.println("| " + toByPrinted[i] + "\t\t\t\t\t\t\t\t");
        }
    }

    public static double distance(double userLat, double userLon, double barLat, double barLon) { //distance is in m.

        int radius = 6371; //km

        double difLat = Math.toRadians(barLat - userLat);
        double difLon = Math.toRadians(barLon - userLon);
        double a = Math.sin(difLat / 2) * Math.sin(difLat / 2) + Math.cos(Math.toRadians(userLat))
                * Math.cos(Math.toRadians(barLat)) * Math.sin(difLon / 2) * Math.sin(difLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = radius * c;

        return d * 1000;
    }

    public static String[][] sortDataByDistance(String[][] data, double[] a) { // TODO rename
        String[][] sorted = new String[data.length][data[0].length];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data.length; j++) {
                if (data[j][1].equals(distanceOfBars(a)[i])) {
                   sorted[i] = data[j];
                }
            }
        }
        return sorted;
    }
}
