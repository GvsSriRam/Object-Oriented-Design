package edu.syr.hw4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

abstract class Question {
    protected String questionString;

    public Question(String questionString) {
        this.questionString = questionString;
    }

    public abstract void ask();
    public abstract void validateResponse(String response);
}

class TrueFalseQuestion extends Question {

    public TrueFalseQuestion(String questionString) {
        super(questionString);
    }

    @Override
    public void ask() {
        System.out.println("True or False: ");
        System.out.println(questionString);
    }

    @Override
    public void validateResponse(String response) {
        if (!response.trim().equalsIgnoreCase("true") && !response.trim().equalsIgnoreCase("false")) {
            throw new IllegalArgumentException("Your response should only be either True or False. Please try again.");
        }
    }
}

class LiekertScaleQuestion extends Question {
    public LiekertScaleQuestion(String questionString) {
        super(questionString);
    }

    @Override
    public void ask() {
        System.out.println("Enter your response on a scale of 1-5: ");
        System.out.println("1 - Strongly Disagree");
        System.out.println("2 - Somewhat Disagree");
        System.out.println("3 - Neutral");
        System.out.println("4 - Somewhat Agree");
        System.out.println("5 - Strongly Agree");
        System.out.println(questionString);
    }

    @Override
    public void validateResponse(String response) {
        try {
            if (Integer.parseInt(response) < 1 || Integer.parseInt(response) > 5) {
                throw new IllegalArgumentException("Your response should be between 1 and 5. Please try again.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Your response should only be an Integer between 1 & 5. Please try again.");
        }
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
            q.ask();
            String response = "";
            boolean isSuccess = false;
            while (!isSuccess) {
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
        q.addQuestion(new LiekertScaleQuestion("How good is OOD course?"));
        q.addQuestion(new LiekertScaleQuestion("How would you rate your professor?"));
        List<String> answers = q.administerQuestionnaire();
        System.out.println("complete!");
        System.out.println(answers);
    }
}

