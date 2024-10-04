package edu.syr.hw5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// Generic type acceptable asnwers depending on question type
class Question<T> {
    private String q;
    private String prompt;
    private List<T> acceptableAnswers;

    public Question(String q, String prompt, List<T> acceptable) {
        this.q = q;
        this.prompt = prompt;
        this.acceptableAnswers = acceptable;
    }

    public void render(PrintStream p) {
        p.println(q);
        p.println(prompt);
    }

    public String getPrompt() {
        return this.prompt;
    }

    public boolean isAcceptableAnswer(String answer) {
        // Case insensitive search & match
        return acceptableAnswers.stream().anyMatch(a -> a.toString().equalsIgnoreCase(answer.trim()));
    }
}

class TrueFalseQuestion extends Question<String> {
    public TrueFalseQuestion(String q) {
        super(q, TrueFalseOption.getPrompt(), TrueFalseOption.getAcceptableAnswers());
    }
}

enum TrueFalseOption {
    TRUE("True", Arrays.asList("True", "T")), // acceptable values for True option
    FALSE("False", Arrays.asList("False", "F")); // acceptable values for False option

    private final String name;
    private final List<String> acceptableValues;
    // All acceptable answers for all options
    private static final List<String> ACCEPTABLE_ANSWERS = Arrays.stream(TrueFalseOption.values())
                                        .flatMap(option -> option.getAcceptableValuesForOption().stream())
                                        .collect(Collectors.toList());
    private static final String PROMPT = "True or False: ";

    // Constructor
    TrueFalseOption(String name, List<String> acceptableValues) {
        this.name = name;
        this.acceptableValues = acceptableValues;
    }

    public String getName() { // Return name
        return name;
    }

    public List<String> getAcceptableValuesForOption() { // Return acceptable values for this option
        return acceptableValues;
    }

    // Create prompt listing acceptable options for True/False
    public static String getPrompt() {
        return PROMPT;
    }

    // Get appropriate enum instance from a given string value
    public static TrueFalseOption getTrueFalseOption(String option) {
        for (TrueFalseOption op : TrueFalseOption.values()) {
            if (op.acceptableValues.stream().anyMatch(val -> val.equalsIgnoreCase(option.trim()))) {
                return op;
            }
        }
        throw new IllegalArgumentException("Invalid option: " + option);
    }

    // Get all acceptable answers as a flat list using Arrays.stream
    public static List<String> getAcceptableAnswers() {
        return ACCEPTABLE_ANSWERS;
    }

    // Check if the given value is a valid True/False option
    public static boolean isValid(String value) {
        boolean res = getAcceptableAnswers().contains(value.trim().toLowerCase());
        if (!res) {
            System.out.println("Please enter a valid option: " + getAcceptableAnswers());
        }
        return res;
    }

    @Override
    public String toString() {
        return name;
    }

}

class LikertScaleQuestion extends Question<Integer> {
    public LikertScaleQuestion(String q) {
        super(q, LikertScaleOption.getPrompt(), LikertScaleOption.getAcceptableAnswers());
    }

    @Override
    public boolean isAcceptableAnswer(String answer) {
        try {
            int value = Integer.parseInt(answer.trim());
            return LikertScaleOption.isValid(value);
        } catch (NumberFormatException e) {
            System.out.println("Please enter an integer only");
            return false;
        }
    }
}

// Enum for LikertScale options 1-5
enum LikertScaleOption {
    STRONGLY_DISAGREE(1, "Strongly Disagree"),
    SOMEWHAT_DISAGREE(2, "Somewhat Disagree"),
    NEUTRAL(3, "Neutral"),
    SOMEWHAT_AGREE(4, "Somewhat Agree"),
    STRONGLY_AGREE(5, "Strongly Agree");

    // Holds user's response
    // Private since we don't need to allow others access to it
    // Final since once a user enters an option, it shouldn't be changed
    private final int value; // 1-5
    private final String name; // Respective names / agreement levels
    private static final List<Integer> ACCEPTABLE_ANSWERS = Arrays.stream(LikertScaleOption.values())
                                                                    .map(LikertScaleOption::getValue)
                                                                    .toList();
    private static final String PROMPT = "Enter your response on a scale of 1-5:\n" +
                                            Arrays.stream(LikertScaleOption.values())
                                                    .map(LikertScaleOption::toString)
                                                    .collect(Collectors.joining("\n"));

    // Constructor
    LikertScaleOption(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() { // Return value
        return value;
    }

    public String getName() { // Return name / description of the LikertScale option
        return name;
    }

    // Return appropriate option from given value
    public static LikertScaleOption getLikertScale(int value) {
        for (LikertScaleOption op : LikertScaleOption.values()) {
            if (op.getValue() == value) {
                return op;
            }
        }
        throw new IllegalArgumentException("Unknown LikertScale value: " + value);
    }

    // Creates a prompt for 1-5
    public static String getPrompt(){
        return PROMPT;
    }

    public static List<Integer> getAcceptableAnswers() {
        return ACCEPTABLE_ANSWERS;
    }

    public static boolean isValid(int value) {
        if (!ACCEPTABLE_ANSWERS.contains(value)) {
            System.out.println("Please enter an integer in " + ACCEPTABLE_ANSWERS);
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return value + " - " + name;
    }
}

public class Questionnaire {
    List<Question> questions;
    public Questionnaire() {
        questions = new ArrayList<>();
    }
    public void addQuestion(Question q) {
        questions.add(q);
    }
    public List<String> administerQuestionnaire() {
        List<String> answers = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        for (Question q: questions) {
            q.render(System.out);
            String response = "";
            do {
                try {
                    response = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } while (!q.isAcceptableAnswer(response));
            answers.add(response);
        }
        return answers;
    }

    public static void main(String[] args) {
        Questionnaire q = new Questionnaire();
        q.addQuestion(new TrueFalseQuestion("Are you awake?"));
        q.addQuestion(new TrueFalseQuestion("Have you had coffee?"));
        q.addQuestion(new TrueFalseQuestion("Are you ready to get to work?"));
        q.addQuestion(new LikertScaleQuestion("How's the josh?"));
        List<String> answers = q.administerQuestionnaire();
        System.out.println("complete!");
        System.out.println(answers);
    }
}

