package cleavn.memorize.Objects;

public class Card {
    private String question, answer;
    private int _id;
    private int categoryId;

    public Card(String question, String answer, int categoryId){
        this.question = question;
        this.answer = answer;
        this.categoryId = categoryId;
    }

    public Card(int _id, String question, String answer, int categoryId){
        this._id = _id;
        this.question = question;
        this.answer = answer;
        this.categoryId = categoryId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
