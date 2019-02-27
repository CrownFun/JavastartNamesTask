/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.filewicz;

import com.opencsv.CSVReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class OpenCsv {

    private final String SAMPLE_CSV_FILE_PATH = "names.csv";

    private List<String> loadCsvFile() throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
        CSVReader csvReader = new CSVReader(reader);
        List<String> names = new ArrayList<>();
        String[] nextRecord;
        while ((nextRecord = csvReader.readNext()) != null) {
            names.add(nextRecord[3]);
        }
        Collections.sort(names);
        System.out.println("List contains " + names.size() + " names ");
         System.out.println("----------------------------------------------------");
        return names;
    }

    private List<Map.Entry<String, Integer>> countOccurancesOfNames(List<String> names) {
        TreeMap<String, Integer> namesMap = new TreeMap<>();
        int repeatName = 1;
        for (int i = 0; i < names.size() - 1; i++) {
            if (names.get(i).equals(names.get(i + 1))) {
                repeatName++;
            } else {
                namesMap.put(names.get(i), repeatName);
                repeatName = 1;
            }
        }
        namesMap.put(names.get(names.size() - 1), repeatName);
        List<Map.Entry<String, Integer>> wynik = namesMap.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).collect(Collectors.toList());
        return wynik;
    }

    private void finTopName(List<Map.Entry<String, Integer>> wynik) {
        System.out.println("The Most Popular Name : " + wynik.get(0));
        System.out.println("----------------------------------------------------");
    }

    private void finTop10Names(List<Map.Entry<String, Integer>> numberOfOccurances) {
        System.out.println("Top 10 popular names: ");

        for (int i = 0; i < 10; i++) {
            System.out.println(numberOfOccurances.get(i));
        }
        System.out.println("----------------------------------------------------");
    }

    private List<Map.Entry<String, Integer>> countOccurancesOfLetters(List<String> listOfNames) {
        TreeMap<String, Integer> letters = new TreeMap<>();
        int repeatLetter = 1;
        for (int i = 1; i < listOfNames.size(); i++) {
            if (listOfNames.get(i).charAt(0) == listOfNames.get(i - 1).charAt(0)) {
                repeatLetter++;
            } else {
                letters.put(listOfNames.get(i - 1).substring(0, 1), repeatLetter);
                repeatLetter = 1;
            }
        }
        letters.put(listOfNames.get(listOfNames.size() - 1).substring(0, 1), repeatLetter);
        List<Map.Entry<String, Integer>> occurancesOfletters = letters.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).collect(Collectors.toList());
        return occurancesOfletters;
    }

    private void findTop3Letters(List<Map.Entry<String, Integer>> occurancesOfletters) {
        System.out.println("top 3 most popular letters with the number of first names starting with the given letter:");

        for (int i = 0; i < 3; i++) {
            System.out.println(occurancesOfletters.get(i));
        }

    }

    public static void main(String[] args) throws IOException {

        long start = System.nanoTime();
        
        OpenCsv csv = new OpenCsv();

        List<String> listOfNames = csv.loadCsvFile();

        List<Map.Entry<String, Integer>> occurancesOfNames = csv.countOccurancesOfNames(listOfNames);

        csv.finTopName(occurancesOfNames);

        csv.finTop10Names(occurancesOfNames);

        List<Map.Entry<String, Integer>> occurancesOfLetters = csv.countOccurancesOfLetters(listOfNames);

        csv.findTop3Letters(occurancesOfLetters);

        long end = System.nanoTime();
        
       System.out.println("Time Execution: " + ((end - start) / 1000000) + " ms");
    }

}
