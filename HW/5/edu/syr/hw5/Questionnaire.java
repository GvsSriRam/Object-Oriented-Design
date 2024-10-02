package edu.syr.hw5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        if (acceptableAnswers.get(0) instanceof String) {
            System.out.println("string values check");
            return acceptableAnswers.stream().anyMatch(a -> a.toString().equalsIgnoreCase(answer.trim()));
        } else if (acceptableAnswers.get(0) instanceof Integer) {
            System.out.println("integer values check");
            try{
                int value = Integer.parseInt(answer.trim());
                return acceptableAnswers.contains(value);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        else {
            return false;
        }
    }
}

class TrueFalseQuestion extends Question<String> {
    public TrueFalseQuestion(String q) {
        super(q, "True or False: ", Arrays.asList("True", "False", "true", "false", "t", "f"));
    }
}

class LikertScaleQuestion extends Question<Integer> {
    public LikertScaleQuestion(String q) {
        super(q, LikertScaleOption.createPrompt(), LikertScaleOption.getAcceptableAnswers());
    }

    @java.lang.Override
    public boolean isAcceptableAnswer(String answer) {
        System.out.println("Overridden method");
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

    public static String createPrompt(){
        StringBuilder sb = new StringBuilder("Enter your response on a scale of 1-5:\n");
        for (LikertScaleOption option : LikertScaleOption.values()){
            sb.append(option.toString()).append("\n");
        }
        return sb.toString();
    }

    public static List<Integer> getAcceptableAnswers() {
        ArrayList<Integer> answers = new ArrayList<>();
        for (LikertScaleOption option : LikertScaleOption.values()) {
            answers.add(option.getValue());
        }
        return answers;
    }

    public static boolean isValid(int value){
        return getAcceptableAnswers().contains(value);
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
//        q.addQuestion(new TrueFalseQuestion("Are you awake?"));
//        q.addQuestion(new TrueFalseQuestion("Have you had coffee?"));
//        q.addQuestion(new TrueFalseQuestion("Are you ready to get to work?"));
        q.addQuestion(new LikertScaleQuestion("How's the josh?"));
        List<String> answers = q.administerQuestionnaire();
        System.out.println("complete!");
        System.out.println(answers);
    }
}

