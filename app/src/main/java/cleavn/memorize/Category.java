package cleavn.memorize;

import static android.graphics.Color.WHITE;

public class Category {
    private String categoryName, categoryDescr;
    private int _id;
    private int categoryColor;

    public Category(String _categoryname, String categoryDescr){
        this._id = 0;
        this.categoryName = _categoryname;
        this.categoryDescr = categoryDescr;
        this.categoryColor = WHITE;
    }

    public Category(String _categoryname, String categoryDescr, int categoryColor){
        this._id = 0;
        this.categoryName = _categoryname;
        this.categoryDescr = categoryDescr;
        this.categoryColor = categoryColor;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String _categoryname) {
        this.categoryName = _categoryname;
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
}