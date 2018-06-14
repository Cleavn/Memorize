package cleavn.memorize.Objects;

import static android.graphics.Color.WHITE;

public class Category {
    private String categoryName, categoryDescr;
    private int _id;
    private int categoryColor;

    public Category(String categoryName, String categoryDescr){
        this.categoryName = categoryName;
        this.categoryDescr = categoryDescr;
        this.categoryColor = WHITE;
    }

    public Category(String categoryName, String categoryDescr, int categoryColor){
        this.categoryName = categoryName;
        this.categoryDescr = categoryDescr;
        this.categoryColor = categoryColor;
    }

    public Category(int _id, String categoryName, String categoryDescr, int categoryColor){
        this._id = _id;
        this.categoryName = categoryName;
        this.categoryDescr = categoryDescr;
        this.categoryColor = categoryColor;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescr() {
        return categoryDescr;
    }

    public void setCategoryDescr(String categoryDescr) {
        this.categoryDescr = categoryDescr;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public int getColor() {
        return categoryColor;
    }

    public void setCategoryColor(int categoryColor){
        this.categoryColor = categoryColor;
    }

    @Override
    public String toString() {
        return categoryName;
    }
}