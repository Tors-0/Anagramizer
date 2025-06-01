package io.github.Tors_0;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeSet;

public class Main {

    public static final TreeSet<String> dictionary = new TreeSet<>();
    public static final File dictionaryFileLocation =
            new File("words_alpha.txt");

    public static void print(Object o, boolean ln) {
        if (ln) System.out.println(o);
        else System.out.print(o);
    }

    public static void main(String[] args) {
        readDictionary();

        Scanner console = new Scanner(System.in);

        print("Enter word to begin anagramization: ", false);
        String base = console.next();
        console.nextLine();

        print("Minimum matches required to count: ", false);
        int matches = console.nextInt();
        console.nextLine();

        AnagramFinder searcher = new AnagramFinder(base, matches);

        searcher.search();
        searcher.printMe();
        try {
            searcher.writeToFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void readDictionary() {
        Scanner dictionaryFileReader;
        try {
            dictionaryFileReader = new Scanner(
                    new FileInputStream(dictionaryFileLocation));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(
                    "Failed to initialize dictionary, please try re-downloading program from source or opening a GitHub issue");
        }

        while (dictionaryFileReader.hasNextLine()) {
            dictionary.add(dictionaryFileReader.nextLine());
        }
    }

}