package edu.syr.hw4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

abstract class Question { // Used to define each type of Question
    protected String questionString; // Contains string body of question

    // Constructor
    public Question(String questionString) {
        this.questionString = questionString;
    }

    public abstract void ask(); // Method to display question
    public abstract void validateResponse(String response); // Method to validate the response given to the question
}

// Enum to hold true or false options for True/False questions
enum TrueFalseOption {
    TRUE("true"),
    FALSE("false");

    // option - response given by the user
    // Made private since others shouldn't directly access it
    // Made final since once a response is entered, it couldn't be changed.
    private final String option;

    // Constructor
    TrueFalseOption(String option) {
        this.option = option;
    }

    public String getOption() { // Return response
        return option;
    }

    // Get appropriate option instance as per the response
    public static TrueFalseOption getTrueFalseOption(String option) {
        for (TrueFalseOption op : TrueFalseOption.values()) {
            if (op.getOption().equalsIgnoreCase(option.trim())) { // Trims and ignores the case of the string while finding a match
                return op;
            }
        }
        throw new IllegalArgumentException("Invalid option: " + option);
    }
}

// True/False type question
class TrueFalseQuestion extends Question {

    // Constructor
    public TrueFalseQuestion(String questionString) {
        super(questionString);
    }

    @Override
    public void ask() { // Displays the question for the user with True or False statement
        System.out.println("True or False: \n");
        System.out.println(questionString);
    }

    @Override
    public void validateResponse(String response) { // Validates the given response if it is true / false
        try {
            TrueFalseOption.getTrueFalseOption(response);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            throw new IllegalArgumentException("Your response should only be either True or False. Please try again.");
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

    @Override
    public String toString() {
        return value + " - " + name;
    }
}

// LikertScale Questions
class LiekertScaleQuestion extends Question {

    // Constructor
    public LiekertScaleQuestion(String questionString) {
        super(questionString);
    }

    @Override
    public void ask() { // Displays LikertScale options, their description and the question to the user
        System.out.println("Enter your response on a scale of 1-5: ");
        for (LikertScaleOption s : LikertScaleOption.values()) {
            System.out.println(s);
        }
        System.out.println("\n"+questionString);
    }

    @Override
    public void validateResponse(String response) { // Validates if the given response is between 1-5
        try {
            int inputValue = Integer.parseInt(response);
            LikertScale.getLikertScale(inputValue);
        } catch (NumberFormatException e) { // Cases like "five", 2.0
            throw new IllegalArgumentException("Your response should only be an Integer between 1 & 5. Please try again.");
        } catch (IllegalArgumentException e) { // If int value isn't between 1 & 5
            System.out.println(e.getMessage());
            throw new IllegalArgumentException("Your response should be between 1 and 5. Please try again.");
        }
    }
}

public class Questionnaire {
    List<Question> questions; // Holds any type of question inherited from Question class
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
            q.ask();
            String response = "";
            boolean isSuccess = false;
            while (!isSuccess) { // Iterates till an acceptable response is entered by the user for the question
                try {
                    response = reader.readLine();
                    q.validateResponse(response);
                    isSuccess = true;
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
            answers.add(response);
            System.out.println();
        }
        return answers;
    }

    public static void main(String[] args) {
        Questionnaire q = new Questionnaire();
        q.addQuestion(new TrueFalseQuestion("Are you awake?"));
        q.addQuestion(new TrueFalseQuestion("Have you had coffee?"));
        q.addQuestion(new TrueFalseQuestion("Are you ready to get to work?"));
        q.addQuestion(new LiekertScaleQuestion("CSE 687 is awesome."));
        q.addQuestion(new LiekertScaleQuestion("How would you rate your professor?"));
        List<String> answers = q.administerQuestionnaire();
        System.out.println("complete!");
        System.out.println(answers);
    }
}

