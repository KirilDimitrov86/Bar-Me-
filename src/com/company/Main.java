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
        print2DArray(sortBarsByDistance(sortDistance(distanceOfBars)));
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

        print2DArray(sortDataByClosingTime(sortTimeInMin(closingTimeOfBars), openingTimeOfBars, closingTimeOfBars));
    }

    public static void map(double userLat, double userLon) { // TODO rename latAndLon
        int[][][] mapOfCoordinates = getMapOfCoordinates(userLat, userLon);
        char[][] map = new char[mapOfCoordinates.length][mapOfCoordinates[0].length];
        loadMap(mapOfCoordinates, map);
        printMap(map);
        printMapLegend(mapOfCoordinates);
    }

    public static double getBarCoordinates(String text, int element) {
        return Double.parseDouble(splitText(text, ", ")[element]);
    }

    public static String[] splitText(String text, String splitBy) {
        String[] splitElement = text.split(splitBy);

        return splitElement;
    }

    public static void print2DArray(String[][] toByPrinted) {
        for (int i = 0; i < toByPrinted.length; i++) {
            System.out.print(i + 1 + "\t ");
            for (int j = 0; j < toByPrinted[i].length; j++) {
                System.out.print("|\t\t\t\t " + toByPrinted[i][j] + "\t\t\t\t");
            }
            System.out.println();
        }
    }

    public static String[][] sortBarsByDistance(double[] distance) {
        String[][] sorted = new String[data.length][data[0].length];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data.length; j++) {
                if (data[j][1].equals(distanceOfBars(distance)[i])) {
                    sorted[i] = data[j];
                }
            }
        }
        return sorted;
    }

    public static double[] sortDistance(double[] distance) {
        for (int i = 1; i < distance.length; i++) {
            int j = i;
            while (j > 0 && distance[j - 1] > distance[j]) {
                double swap = distance[j - 1];
                distance[j - 1] = distance[j];
                distance[j] = swap;
                j = j - 1;
            }
        }

        return distance;
    }

    public static String[] distanceOfBars(double[] distance) {
        String[] distanceOfBars = new String[distance.length];
        for (int i = 0; i < distance.length; i++) {
            distanceOfBars[i] = "" + distance[i];
        }
        return distanceOfBars;
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

    // OPEN BARS
    public static String[][] sortDataByClosingTime(int[] sortedTime, int[] openingTime, int[] closingTime) { //TODO check openBar length
        String[][] openBars = new String[getOpenBarsLength(openingTime, closingTime)][3];
        openBars = getOpenBars(openingTime, closingTime);
        String[][] sorted = new String[openBars.length][3];
        int counter = 0;
        for (int i = 0; i < sortedTime.length; i++) {
            for (int j = 0; j < openBars.length; j++) {
                if (openBars[j][2] == null) {
                    break;
                }
                if (openBars[j][2].equals(intToStringArray(sortedTime)[i])) {
                    sorted[counter] = openBars[j];
                    sorted[counter][1] = getTimeInHours(openBars[j][1]);
                    sorted[counter][2] = getTimeInHours(openBars[j][2]);
                    counter++;
                    break;
                }
            }
        }
        return sorted;
    }

    public static int[] sortTimeInMin(int[] time) {
        for (int i = 1; i < time.length; i++) {
            int j = i;
            while (j > 0 && time[j - 1] > time[j]) {
                int swap = time[j - 1];
                time[j - 1] = time[j];
                time[j] = swap;
                j = j - 1;
            }
        }

        return time;
    }

    public static int getOpeningTimeInMin(String element) {
        int hours = Integer.parseInt(splitText(element, ":")[0]);
        int min = Integer.parseInt(splitText(element, ":")[1]);

        int totalMin = hours * 60 + min;

        return totalMin;
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

    public static String getTimeInHours(String element) {
        int totalMin = Integer.parseInt(element);
        int hours = totalMin / 60;
        int min = totalMin % 60;
        if (hours >= 24) {
            hours -= 24;
        }
        if (min < 10) {
            return "" + hours + ":" + min + "0" + " ч";
        }
        return "" + hours + ":" + min + " ч";
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

    public static String getBarClosingTime(int i) {
        return data[i][3];
    }

    public static String getBarOpeningTime(int i) {
        return data[i][2];
    }

    public static String getBarCoordinates(int i) {
        return data[i][1];
    }

    public static String getBarName(int i) {
        return data[i][0];
    }

    //MAP
    private static void loadMap(int[][][] mapOfCoordinates, char[][] map) {
        char barNum = 'a';
        for (int i = 0; i < mapOfCoordinates.length; i++) {
            for (int j = 0; j < mapOfCoordinates[i].length; j++) {
                for (int k = 0; k < data.length; k++) {
                    if (amIHere(i, j, mapOfCoordinates)) {
                        map[i][j] = 'x';
                        break;
                    }else if (isBarHere(i, j, k, mapOfCoordinates)) {
                        map[i][j] = barNum;
                        barNum++;
                        break;
                    } else {
                        map[i][j] = 32;
                    }
                }
            }
        }
    }

    public static int[][][] getMapOfCoordinates(double userLat, double userLon) { //change map size and distance between cells
        int[][][] map = new int[50][50][2];       // decide map size and map dimensions
        userLat *= 10000;
        userLon *= 10000;
        int lat = (int) userLat;
        int lon = (int) userLon;
        int startLat = lat - ((map.length / 2) * 1); //if square
        int startLon = lon - ((map.length / 2) * 1);
        map[0][0][0] = startLat;
        map[0][0][1] = startLon;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (j == 0) {
                    map[i][j][0] = startLat;
                    map[j][i][1] = startLon;
                } else {
                    map[i][j][0] = map[i][j - 1][0] + 1;
                    map[j][i][1] = map[j - 1][i][1] + 1;
                }
            }
        }
        return map;
    }

    public static int getBarCoordinatesAsInt(String text, int element) {
        double coordinates = Double.parseDouble(splitText(text, ", ")[element]);
        coordinates *= 10000;
        return (int) coordinates;
    }

    public static void printMapLegend(int[][][] mapOfCoordinates) {
        char barSymbol = 'a';
        System.out.println("x-\t"+"ти си тук");
        for (int i = 0; i < mapOfCoordinates.length; i++) {
            for (int j = 0; j < mapOfCoordinates[i].length; j++) {
                for (int k = 0; k < data.length; k++) {
                    if (isBarHere(i, j, k, mapOfCoordinates)) {
                        System.out.println(barSymbol + "-\t" + getBarName(k));
                        barSymbol++;
                    }
                }
            }
        }
    }

    public static void printMap(char map[][]) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j]+ "\t");
            }
            System.out.println();
        }
    }

    public static boolean amIHere(int index, int elementIndex, int[][][] map) { //TODO RENAME
        if (map[index][elementIndex][0] ==map[map.length/2][map[index].length/2][0]  &&  // check for near coordinates not seam
                map[index][elementIndex][1] ==map[map.length/2][map[index].length/2][1] ) { //check index out of bounce

            return true;
        }
        return false;
    }

    public static boolean isBarHere(int index, int elementIndex, int indexForData, int[][][] mapOfCoordinates) { //TODO RENAME
        if (mapOfCoordinates[index][elementIndex][0] == getBarCoordinatesAsInt(data[indexForData][1], 0) &&  // check for near coordinates not seam
                mapOfCoordinates[index][elementIndex][1] == getBarCoordinatesAsInt(data[indexForData][1], 1)) { //check index out of bounce

            return true;
        }
        return false;
    }
}
