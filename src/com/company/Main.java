package com.company;

import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static String[][] bars = {
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

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print("Моля въведете географска дължина: ");
        double userLatitude = getDoubleInput();
        System.out.print("Моля въведете географска ширина: ");
        double userLongitude = getDoubleInput();
        System.out.println("Моля въведете опция");
        System.out.println("(1) за списък на всички барове подредени по разстояние от вас(в м.).");
        System.out.println("(2) за списък на всички барове които са отворени в момента.)");
        System.out.println("(3) за карта.");
        int choice = getIntegerInput();
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

    public static void listOfAll(double userLatitude, double userLongitude) {
        int[] distanceOfBars = new int[bars.length];
        for (int dataIndex = 0; dataIndex < bars.length; dataIndex++) {
            double barLat = getBarCoordinates(getBarCoordinates(dataIndex), 0);
            double barLon = getBarCoordinates(getBarCoordinates(dataIndex), 1);
            bars[dataIndex][1] = "" + getDistance(userLatitude, userLongitude, barLat, barLon);
            distanceOfBars[dataIndex] = Integer.parseInt(bars[dataIndex][1]);
        }

        System.out.println("№ \t\t\t\t\t" + "ИМЕ НА БАРА\t\t\t\t" + " РАЗСТОЯНИЕ ДО БАРА\t\t\t\t\t\t" + " ОТВАРЯ В\t\t\t\t\t\t" + " ЗАТВАРЯ В");
        print2DArray(sortBarsByDistance(sortDistance(distanceOfBars)));
    }

    public static void listOpen() {
        int[] openingTimeOfBars = new int[bars.length];
        int[] closingTimeOfBars = new int[bars.length];

        for (int dataIndex = 0; dataIndex < bars.length; dataIndex++) {
            bars[dataIndex][3] = "" + getClosingTimeInMin(getBarClosingTime(dataIndex));
            bars[dataIndex][2] = "" + getOpeningTimeInMin(getBarOpeningTime(dataIndex));
            openingTimeOfBars[dataIndex] = Integer.parseInt(getBarOpeningTime(dataIndex));
            closingTimeOfBars[dataIndex] = Integer.parseInt(getBarClosingTime(dataIndex));
        }
        System.out.println("№ \t\t\t\t\t" + "ИМЕ НА БАРА\t\t\t\t\t\t\t" + " ОТВАРЯ В\t\t\t\t\t\t\t" + " ЗАТВАРЯ В");
        print2DArray(sortDataByClosingTime(sortTimeInMin(closingTimeOfBars), openingTimeOfBars, closingTimeOfBars));
    }

    public static void map(double userLatitude, double userLongitude) {
        int[][][] mapOfCoordinates = getMapOfCoordinates(userLatitude, userLongitude);
        char[][] map = new char[mapOfCoordinates.length][mapOfCoordinates[0].length];
        loadMap(mapOfCoordinates, map);
        printMap(map);
        printMapLegend(mapOfCoordinates);
    }

    public static String[][] sortBarsByDistance(int[] distance) {
        String[][] sorted = new String[bars.length][bars[0].length];
        for (int i = 0; i < bars.length; i++) {
            for (int j = 0; j < bars.length; j++) {
                if (bars[j][1].equals(getDistanceOfBars(distance)[i])) {
                    sorted[i] = bars[j];
                }
            }
        }
        return sorted;
    }

    public static String[] getDistanceOfBars(int[] distance) {
        String[] distanceOfBars = new String[distance.length];
        for (int i = 0; i < distance.length; i++) {
            distanceOfBars[i] = "" + distance[i];
        }
        return distanceOfBars;
    }

    public static int[] sortDistance(int[] distance) {
        for (int i = 1; i < distance.length; i++) {
            int j = i;
            while (j > 0 && distance[j - 1] > distance[j]) {
                int swap = distance[j - 1];
                distance[j - 1] = distance[j];
                distance[j] = swap;
                j = j - 1;
            }
        }

        return distance;
    }

    public static double getBarCoordinates(String text, int element) {
        return Double.parseDouble(splitText(text, ", ")[element]);
    }

    public static String[] splitText(String text, String splitBy) {
        String[] splitElement = text.split(splitBy);

        return splitElement;
    }

    public static int getDistance(double userLatitude, double userLongitude, double barLatitude, double barLongitude) { //distance is in m.
        int radius = 6371;
        double difLat = Math.toRadians(barLatitude - userLatitude);
        double difLon = Math.toRadians(barLongitude - userLongitude);
        double a = Math.sin(difLat / 2) * Math.sin(difLat / 2) + Math.cos(Math.toRadians(userLatitude))
                * Math.cos(Math.toRadians(barLatitude)) * Math.sin(difLon / 2) * Math.sin(difLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = radius * c;

        return (int) (d * 1000);
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

    // OPEN BARS

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

    public static String[][] sortDataByClosingTime(int[] sortedTime, int[] openingTime, int[] closingTime) { //TODO check openBar length
        String[][] openBars;
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

    public static int getOpenBarsLength(int[] openingTime, int[] closingTime) {
        int counter = 0;
        for (int i = 0; i < openingTime.length; i++) {
            if (openingTime[i] <= getCurrentTimeInMin() && closingTime[i] >= getCurrentTimeInMin()) {
                counter++;
            }
        }
        return counter;
    }

    public static int getCurrentTimeInMin() {
        Date currentTime = new Date();
        int hour = currentTime.getHours();
        int min = currentTime.getMinutes();
        int currentTimeInMin = hour * 60 + min;

        return currentTimeInMin;
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

    //MAP
    private static void loadMap(int[][][] mapOfCoordinates, char[][] map) {
        char barNum = 'a';
        for (int i = 0; i < mapOfCoordinates.length; i++) {
            for (int j = 0; j < mapOfCoordinates[i].length; j++) {
                for (int k = 0; k < bars.length; k++) {
                    if (amIHere(i, j, mapOfCoordinates)) {
                        map[i][j] = 'x';
                        break;
                    } else if (isBarHere(i, j, k, mapOfCoordinates)) {
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
        int startLat = lat - (map.length / 2); //if square
        int startLon = lon - (map.length / 2);
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
        System.out.println("x-\t" + "ти си тук");
        for (int i = 0; i < mapOfCoordinates.length; i++) {
            for (int j = 0; j < mapOfCoordinates[i].length; j++) {
                for (int k = 0; k < bars.length; k++) {
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
                System.out.print(map[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public static boolean isBarHere(int index, int elementIndex, int indexForData, int[][][] mapOfCoordinates) {
        if (mapOfCoordinates[index][elementIndex][0] == getBarCoordinatesAsInt(bars[indexForData][1], 0) &&
                mapOfCoordinates[index][elementIndex][1] == getBarCoordinatesAsInt(bars[indexForData][1], 1)) {

            return true;
        }
        return false;
    }

    public static boolean amIHere(int index, int elementIndex, int[][][] map) { //TODO RENAME
        if (map[index][elementIndex][0] == map[map.length / 2][map[index].length / 2][0] &&
                map[index][elementIndex][1] == map[map.length / 2][map[index].length / 2][1]) {

            return true;
        }
        return false;
    }

    public static String[] intToStringArray(int[] intArray) {
        String[] stringArray = new String[intArray.length];
        for (int i = 0; i < intArray.length; i++) {
            stringArray[i] = "" + intArray[i];
        }
        return stringArray;
    }

    public static double getDoubleInput() {
        String input = scanner.nextLine();
        while (!isNumeric(input)) {
            input = scanner.nextLine();
        }

        return Double.parseDouble(input);
    }

    public static int getIntegerInput() {
        String input = scanner.nextLine();
        while (!isNumeric(input)) {
            input = scanner.nextLine();
        }
        return Integer.parseInt(input);
    }

    public static boolean isNumeric(String string) {
        String regex = "[0-9]+[\\.]?[0-9]*";
        return Pattern.matches(regex, string);
    }

    public static String getBarClosingTime(int i) {
        return bars[i][3];
    }

    public static String getBarOpeningTime(int i) {
        return bars[i][2];
    }

    public static String getBarCoordinates(int i) {
        return bars[i][1];
    }

    public static String getBarName(int i) {
        return bars[i][0];
    }

}
