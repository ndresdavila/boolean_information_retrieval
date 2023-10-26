import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.ISO_8859_1;

public class BooleanQuery {

	public static Set<String> stopWords;
	public static Path plotPath;
	public static Map<Type, Integer> Types = new HashMap<>();
	public static Map<String, Integer> wordsCounter = new HashMap<>();
	public static Map<Type, Double> ranking = new HashMap<>();
	public static Pattern pattern = Pattern.compile(
			"mv: (\")?([^\"(]*)(\")? ?(\\([^\\d\\?]*\\))? ?(\\((\\d{4}|\\?{4})(\\/\\w*)?\\))?( \\((vg|tv|v)\\))? ?(\\{(.*)\\})?");

	public static void buildStopWords(Path path) {
		try {
			stopWords = Files.lines(path, ISO_8859_1).collect(Collectors.toSet());
		} catch (FileNotFoundException e) {
			System.err.println("ERROR: There was a problem finding the file");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("ERROR: There was a problem reading the file");
			e.printStackTrace();
		}
		stopWords.add("");
	}

	public static void buildTypes(Path plotFile) {
		try (BufferedReader reader = Files.newBufferedReader(plotFile, ISO_8859_1)) {
			String line;
			String[] splitedLine = null;

			String lastWord = null;
			while ((line = reader.readLine()) != null) {

				if (line.startsWith("MV: ")) {
					Matcher matcher = pattern.matcher(line.toLowerCase());
					if (matcher.matches()) {
						splitedLine = matcher.group(2).toLowerCase().split("[ .,:!?]");
						// TODO: repeated code
						for (int i = 0; i < splitedLine.length - 1; i++) {
							String word1 = splitedLine[i].trim();
							if (stopWords.contains(word1)) {
								i++;
								continue;
							}
							wordsCounter.compute(word1, (k, v) -> v == null ? 1 : v + 1);

							String word2 = splitedLine[i + 1].trim();
							if (stopWords.contains(word2)) {
								i += 2;
								continue;
							}

							Type Type = new Type(word1, word2);

							Types.compute(Type, (k, v) -> v == null ? 1 : v + 1);
						}

						String chapter = matcher.group(11);
						if (chapter != null && !chapter.contains("SUSPENDED")) {
							splitedLine = chapter.toLowerCase().split("[ .,:!?]");
							// TODO: repeated code
							for (int i = 0; i < splitedLine.length - 1; i++) {
								String word1 = splitedLine[i].trim();
								if (stopWords.contains(word1)) {
									i++;
									continue;
								}
								wordsCounter.compute(word1, (k, v) -> v == null ? 1 : v + 1);

								String word2 = splitedLine[i + 1].trim();
								if (stopWords.contains(word2)) {
									i += 2;
									continue;
								}

								Type Type = new Type(word1, word2);

								Types.compute(Type, (k, v) -> v == null ? 1 : v + 1);
							}
						}
					}

				} else if (line.startsWith("PL:")) {

					if (lastWord != null && !stopWords.contains(splitedLine[0])) {
						Type Type = new Type(lastWord, splitedLine[0]);

						Types.compute(Type, (k, v) -> v == null ? 1 : v + 1);
					}

					splitedLine = line.replace("PL: ", "").toLowerCase().split("[ .,:!?]");

					if (splitedLine.length == 0 || stopWords.contains(splitedLine[splitedLine.length - 1])) {
						lastWord = null;
					} else {
						lastWord = splitedLine[splitedLine.length - 1];
						wordsCounter.compute(lastWord, (k, v) -> v == null ? 1 : v + 1);
					}

					for (int i = 0; i < splitedLine.length - 1; i++) {
						// TODO: repeated code
						String word1 = splitedLine[i].trim();
						if (stopWords.contains(word1)) {
							i++;
							continue;
						}
						wordsCounter.compute(word1, (k, v) -> v == null ? 1 : v + 1);

						String word2 = splitedLine[i + 1].trim();
						if (stopWords.contains(word2)) {
							i += 2;
							continue;
						}

						Type Type = new Type(word1, word2);

						Types.compute(Type, (k, v) -> v == null ? 1 : v + 1);
					}

				} else {
					lastWord = null;
				}

			}

		} catch (FileNotFoundException e) {
			System.err.println("ERROR: There was a problem finding the file");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("ERROR: There was a problem reading the file");
			e.printStackTrace();
		}
	}

	public static void computeRanking() {
		for (java.util.Map.Entry<Type, Integer> entry : Types.entrySet()) {
			Type Type = entry.getKey();
			// TODO: it shoudn't give a NullPointerException bc if the word it's in a Type
			// that means that should be stored also alone
			double value = (2 * entry.getValue()) //<-- NullPointerException
					/ (wordsCounter.get(Type.getWord1()) + wordsCounter.get(Type.getWord2()));
			ranking.put(Type, value);
		}
	}

	public static void main(String[] args) {
		BooleanQuery.buildStopWords(Path.of("data/stopwords"));
		BooleanQuery.buildTypes(Path.of("data/plot.list"));
		BooleanQuery.computeRanking();
		ranking.entrySet().stream().sorted(Comparator.comparing(entry -> entry.getValue())).limit(1000)
				.forEach(entry -> System.out.println(
						entry.getKey().getWord1() + "\t" + entry.getKey().getWord2() + "\t" + entry.getValue()));

	}

}
