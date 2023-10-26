import static java.nio.charset.StandardCharsets.ISO_8859_1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class foo {

	public static List<MV> movies = null;

	public static String ej = "MV: \"#7DaysLater\" (2013)\r\n\n"
			+ "PL: #7dayslater is an interactive comedy series featuring an ensemble cast of\n"
			+ "MV: \"#BlackLove\" (2015) {Bringing Sexy Back (#1.3)}\r\n" + "MV: \"$24 in 24\" (2012)\r\n"
			+ "MV: \"'Allo 'Allo!\" (1982)\r\n" + "MV: \"'Allo 'Allo!\" (1982) {A Winkle in Time (#9.6)}\r\n"
			+ "MV: \"Karma Klub\" (????)\r\n" + "MV: A Self-Made Failure (1924)\r\n"
			+ "MV: Her Crowning Glory (1913/II)\r\n" + "MV: Jurassic: The Hunted (2009) (VG)\r\n"
			+ "MV: Juror #9 (????) (TV)\r\n" + "MV: Jury Duty: The Comedy (1990) (TV)\r\n"
			+ "MV: Just Another Day (2006) (TV)\r\n" + "MV: Just Another Day (2009/I)\r\n"
			+ "MV: Just Be: Vibrations (2012) (V)\r\n" + "MV: Just Another Girl on the I.R.T. (1992)\r\n"
			+ "MV: Just Dance: Disney Party (2012) (VG)\r\n" + "MV: J�rg Ratgeb - Maler (1978)\r\n"
			+ "MV: K-Past (2008) {{SUSPENDED}}";

	public static void main(String[] args) throws IOException {
		Pattern pattern = Pattern.compile(
				"MV: (\")?([^\"(]*)(\")? ?(\\((\\d{4}|\\?{4})(\\/\\w*)?\\))?( \\((VG|TV|V)\\))? ?(\\{(.*)\\})?");
		try (BufferedReader reader = Files.newBufferedReader(Paths.get("plot - copia.list"), ISO_8859_1)) {
			String line;
			String[] splited_line = null;

			while ((line = reader.readLine()) != null) {
				Matcher matcher = pattern.matcher(line);
				if(line.startsWith("MV")) System.out.println("\n");
				for (int j = 0; j < matcher.groupCount() + 1; j++) {
					if (matcher.matches()) {
						 System.out.println(matcher.group(j));
					}
				}
			}
		} catch (FileNotFoundException e) {

			System.err.println("ERROR: There was a problem finding the file");
			e.printStackTrace();
		}
	}

//		 HashMap<String, HashSet<Integer>> people = new HashMap<>();
//
//
//		    // Add keys and values (Name, Age)
//		 	if (people.get("John") == null) {
//		 		people.put("John", new HashSet<Integer>());
//		 		people.get("John").add(31);
//		 	}
//		 	else {
//		 		people.get("John").add(31);
//		 	}
//		   /* people.put("John", 32);
//		    people.put("Steve", 30);
//		    people.put("Angie", 33);*/
//		    if (people.get("John") == null) {
//		 		people.put("John", new HashSet<Integer>(1));
//		 	}
//		 	else {
//		 		people.get("John").add(35);
//		 	}
//		    
//		    if (people.get("John") == null) {
//		 		people.put("John", new HashSet<Integer>(1));
//		 	}
//		 	else {
//		 		people.get("John").add(35);
//		 	}
//
//		    for (String i : people.keySet()) {
//		      System.out.println("key: " + i + " value: " + people.get(i));
//		    }
//		
//		String[] splited_line = null;
//		MV mv = new MV();
//		List<String> plot = new ArrayList<>();
//		for (String line : ej.split("\n")) {
//
//			if (line.startsWith("MV: ")) {
//				
//				
//				splited_line = line.replace("MV: ", "").toLowerCase().split("[({]");
//				System.out.println(splited_line.length);
//				for(String element: splited_line) {
//					element = element.trim().replaceAll("[)}]", "");
//					System.out.println(element);
//				}
//
//			} else if(line.startsWith("PL:")) {
//				// make the token list
//				// TODO stop words? how to keep the order (match "the lord" but not just "lord") but make the storage smaller?
//				
//				splited_line = line.replace("PL: ", "").toLowerCase().split("[ .,:!?]");
//				for(String word: splited_line) {
//					plot.add(word.trim());
//				}
//				
//			
//			} else if(line.startsWith("-")) {
//				//mv.setPlot(plot);
//				movies.add(mv);
//				mv = new MV();
//				plot = new ArrayList<>();
//				
//			} else {
//				continue;
//			}
//
//		}

}
