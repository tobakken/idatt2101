package Arbeidskrav_4;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Stack;
import java.util.stream.Collectors;

public class MainOppgave2 {
    public static void main(String[] args) {
        String filSomSjekkes = lesJavafil("TestFile.txt");
        parantesSjekk(filSomSjekkes);
    }

    public static void parantesSjekk(String sjekk) {
        Stack<String> stakk = new Stack<>();

        for (int i = 0; i < sjekk.length(); i++) {
            String checkChar = Character.toString(sjekk.charAt(i));
            if (checkChar.matches("[({\\[]")) {
                stakk.push(checkChar);
            }
            if (checkChar.matches("[)}\\]]")) {
                switch (checkChar) {
                    case ")":
                        if (!stakk.empty()) {
                            if (stakk.peek().equals("(")) {
                                stakk.pop();
                            } else {
                                System.out.println("For mange: " + stakk.peek());
                            }
                        } else {
                            System.out.println("For mange: )");
                        }
                        break;
                    case "}":
                        if (!stakk.empty()) {
                            if (stakk.peek().equals("{")) {
                                stakk.pop();
                            } else {
                                System.out.println("For mange: " + stakk.peek());
                            }
                        } else {
                            System.out.println("For mange: }");
                        }
                        break;
                    case "]":
                        if (!stakk.empty()) {
                            if (stakk.peek().equals("[")) {
                                stakk.pop();
                            } else {
                                System.out.println("For mange: " + stakk.peek());
                            }
                        } else {
                            System.out.println("For mange: ]");
                        }
                        break;
                    default:
                        System.out.println("Something wrong: too many right-parenteces or brackets");
                }
            }
        }
        if (stakk.empty()) {
            System.out.println("Alt bra");
        }
    }

    public static String lesJavafil(String filnavn) {
        InputStream is = MainOppgave2.class.getResourceAsStream(filnavn);
        return new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
    }

}
