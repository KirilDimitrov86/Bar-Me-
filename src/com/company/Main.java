package com.company;

import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.util.zip.DataFormatException;

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
                listOpen();
                break;
            case 3:
                map(userLatitude, userLongitude);
                break;
            default:
                System.out.println("Invalid input");
        }

    }

    public static void listOfAll(double userLat, double userLon) { // TODO rename latAndLon

        double[] distanceOfBars = new double[data.length];
        for (int dataIndex = 0; dataIndex < data.length; dataIndex++) {
            double barLat = getBarCoordinates(getBarCoordinates(dataIndex), 0);
            double barLon = getBarCoordinates(getBarCoordinates(dataIndex), 1);
            data[dataIndex][1] = "" + distance(userLat, userLon, barLat, barLon);
            distanceOfBars[dataIndex] = Double.parseDouble(data[dataIndex][1]);
        }
        print2DArray(sortDataByDistance(sortDistance(distanceOfBars)));
    }

    public static void listOpen() {
        int[] openingTimeOfBars = new int[data.length];
        int[] closingTimeOfBars = new int[data.length];

        for (int dataIndex = 0; dataIndex < data.length; dataIndex++) {
            data[dataIndex][3] = "" + getClosingTimeInMin(getBarClosingTime(dataIndex));
            data[dataIndex][2] = "" + getOpeningTimeInMin(getBarOpeningTime(dataIndex));

            openingTimeOfBars[dataIndex] = Integer.parseInt(getBarOpeningTime(dataIndex));
            closingTimeOfBars[dataIndex] = Integer.parseInt(getBarClosingTime(dataIndex));

        }


        print2DArray(getOpenBars(openingTimeOfBars, closingTimeOfBars));
        // print2DArray(sortDataByClosingTime(sortClosingTime(closingTimeOfBars)));

    }


    public static void map(double userLat, double userLon) { // TODO rename latAndLon
    }

    public static double[] sortDistance(double[] data) {  //TODO rename data
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

    public static double getBarCoordinates(String text, int element) {
        return Double.parseDouble(splitText(text, ", ")[element]);
    }

    public static String[] splitText(String text, String splitBy) {
        String[] splitElement = text.split(splitBy);

        return splitElement;
    }

    public static String[] distanceOfBars(double[] data) {//TODO rename data
        String[] distanceOfBars = new String[data.length];
        for (int i = 0; i < data.length; i++) {
            distanceOfBars[i] = "" + data[i];
        }
        return distanceOfBars;
    }

    public static void print2DArray(String[][] toByPrinted) {
        for (int i = 0; i < toByPrinted.length; i++) {
            System.out.print(i + 1 + "\t ");
            for (int j = 0; j < toByPrinted[i].length; j++) {
                System.out.print("| " + toByPrinted[i][j] + "\t\t\t\t\t\t\t\t");
            }
            System.out.println();
        }
    }

    public static void printArray(int[] toByPrinted) {
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

    public static String[][] sortDataByDistance(double[] a) { // TODO rename a
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


    // OPEN BARS
    public static String[][] sortDataByClosingTime(int[] sortedTime) {

        String[][] sorted = new String[data.length][3];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data.length; j++) {
                if (sorted[j][0] == null) {
                    if (data[i][3].equals(intToStringArray(sortedTime)[j])) { 
                        sorted[j] = data[i];
                        break;
                    }
                }
            }
        }
        return sorted;
    }

    public static int[] sortClosingTime(int[] data) {  //TODO rename data =  TIME IN MIN, SORT_TIME- TO USE IN OPEN TIME
        for (int i = 1; i < data.length; i++) {
            int j = i;
            while (j > 0 && data[j - 1] > data[j]) {
                int swap = data[j - 1];
                data[j - 1] = data[j];
                data[j] = swap;
                j = j - 1;
            }
        }

        return data;
    }

    public static int getClosingTimeInMin(String element) {
        int hours = Integer.parseInt(splitText(element, ":")[0]);
        int min = Integer.parseInt(splitText(element, ":")[1]);

        if (hours < 12) {
            hours += 24;
        }
        int totalMin = hours * 60 + min;

        return totalMin;
    }

    public static int getOpeningTimeInMin(String element) {
        int hours = Integer.parseInt(splitText(element, ":")[0]);
        int min = Integer.parseInt(splitText(element, ":")[1]);

        int totalMin = hours * 60 + min;

        return totalMin;
    }

    public static String[] intToStringArray(int[] intArray) {
        String[] stringArray = new String[intArray.length];
        for (int i = 0; i < intArray.length; i++) {
            stringArray[i] = "" + intArray[i];
        }
        return stringArray;
    }

    public static int getCurrentTimeInMin() {
        Date currentTime = new Date();
        int hour = currentTime.getHours();
        int min = currentTime.getMinutes();
        int currentTimeInMin = hour * 60 + min;

        return currentTimeInMin;
    }

    public static int getOpenBarsLength(int[] openingTime, int[] closingTime) {
        int counter = 0;
        for (int i = 0; i < openingTime.length; i++) {
            if (openingTime[i] <= getCurrentTimeInMin() && closingTime[i] >= getCurrentTimeInMin()) {
                counter++;
            }
        }
        return counter;
    }

    public static String[][] getOpenBars(int[] openingTime, int[] closingTime) {
        String[][] openBar = new String[getOpenBarsLength(openingTime, closingTime)][3];
        int counter = 0;
        for (int i = 0; i < openingTime.length; i++) {
            if (openingTime[i] <= getCurrentTimeInMin() && closingTime[i] >= getCurrentTimeInMin()) {
                openBar[counter][0] = getBarName(i);
                openBar[counter][1] = getBarOpeningTime(i);
                openBar[counter][2] = getBarClosingTime(i);
                counter++;
            }
        }
        return openBar;
    }

    private static String getBarClosingTime(int i) {
        return data[i][3];
    }

    private static String getBarOpeningTime(int i) {
        return data[i][2];
    }

    private static String getBarCoordinates(int i) {
        return data[i][1];
    }

    private static String getBarName(int i) {
        return data[i][0];
    }

}
