import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class FuzzySearch {

    static class Match {
        String word;
        int score;

        Match(String word, int score) {
            this.word = word;
            this.score = score;
        }
    }

    public static List<String> fuzzySearch(String query, List<String> words) {
        List<Match> matches = new ArrayList<>();

        for (String word : words) {
            int score = getMatchScore(query, word);
            if (score > 0) {
                matches.add(new Match(word, score));
            }
        }

        Collections.sort(matches, Comparator.comparingInt(m -> -m.score));

        List<String> result = new ArrayList<>();
        for (Match match : matches) {
            result.add(match.word);
        }

        return result;
    }

    private static int getMatchScore(String query, String word) {
        if (query.equals(word)) {
            return 100; // exact match
        } else if (word.startsWith(query)) {
            return 50; // prefix match
        } else if (word.contains(query)) {
            return 10; // substring match
        } else {
            return 0; // no match
        }
    }

    public static void main(String[] args) {
        List<String> words = List.of("apple", "banana", "grape", "apricot", "pineapple");
        String query = "app";
        List<String> results = fuzzySearch(query, words);

        System.out.println("Search results for query '" + query + "':");
        for (String result : results) {
            System.out.println(result);
        }
    }
}