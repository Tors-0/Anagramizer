package io.github.Tors_0;

import io.github.Tors_0.Anagramizer.BuildConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static io.github.Tors_0.Main.dictionary;
import static io.github.Tors_0.Main.print;

public class AnagramFinder {
    private final String source;
    private final int minMatches;
    private final TreeSet<String> dictionaryInstance;
    private TreeSet<String> anagramList = new TreeSet<>();
    private boolean searchComplete = false;

    public AnagramFinder(String source, int matches) {
        this.source = source;
        this.minMatches = matches;

        dictionaryInstance = dictionary.stream()
                .filter(s -> s.length() <= source.length())
                .collect(Collectors.toCollection(TreeSet::new));
    }

    public void search() {
        print("Anagramizing " + source, false);
        if (searchComplete) {
            print("...Done", true);
            return;
        }

        findPermutations(source, "");

        anagramList = anagramList.stream().parallel()
                .filter(anagram -> {
                    int matches = 0;
                    for (String s : dictionaryInstance) {
                        if (anagram.contains(s)) {
                            matches++;
                            if (matches >= minMatches) {
                                print(".", false);
                                return true;
                            }
                        }
                    }
                    return false;
                })
                .collect(Collectors.toCollection(TreeSet::new));

        searchComplete = true;
    }

    /**
     * Function to print all the permutations of str
     * @author <a href="https://www.geeksforgeeks.org/print-all-permutations-of-a-string-in-java/">GeeksForGeeks</a>
     */
    private void findPermutations(String str, String ans)
    {

        // If string is empty
        if (str.isEmpty()) {
            //// modified from source
            anagramList.add(ans);
            return;
        }

        for (int i = 0; i < str.length(); i++) {

            // ith character of str
            char ch = str.charAt(i);

            // Rest of the string after excluding
            // the ith character
            String ros = str.substring(0, i) +
                    str.substring(i + 1);

            // Recursive call
            findPermutations(ros, ans + ch);
        }
    }

    public void printMe() {
        if (searchComplete) {
            Iterator<String> iter = anagramList.iterator();
            for (int i = 0; i < Math.min(anagramList.size(), 10); i++) {
                print(iter.next(), true);
            }
            print("And " + (anagramList.size() - Math.min(anagramList.size(), 10) + " more..."), true);
        } else {
            print("Search not yet completed", true);
        }
    }

    public void writeToFile() throws IOException {
        File output = new File(source + "Anagrams_level" + minMatches + "_" + BuildConfig.APP_VERSION + ".txt");
        if (output.createNewFile()) {
            PrintWriter anagramFileWriter = new PrintWriter(new FileOutputStream(output));

            anagramList.iterator().forEachRemaining(anagramFileWriter::println);

            anagramFileWriter.close();
        }
    }
}
