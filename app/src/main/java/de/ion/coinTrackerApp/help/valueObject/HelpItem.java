package de.ion.coinTrackerApp.help.valueObject;

public class HelpItem {
    private final String question;
    private final String answer;

    /**
     * @param question
     * @param answer
     */
    public HelpItem(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    /**
     * @return question
     */
    public String getQuestion() {
        return this.question;
    }

    /**
     * @return answer
     */
    public String getAnswer() {
        return this.answer;
    }
}
