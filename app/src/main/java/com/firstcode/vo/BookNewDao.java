package com.firstcode.vo;

/**
 * Created by wangjinliang on 2016/3/11.
 */

// // bookName text, // bookPrice real,  //bookAuthor
public class BookNewDao {
    private String bookName;
    private float bookPrice;
    private String bookAuthor;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public float getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(float bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    @Override
    public String toString() {
        return "BookNewDao{" +
                "bookName='" + bookName + '\'' +
                ", bookPrice=" + bookPrice +
                ", bookAuthor='" + bookAuthor + '\'' +
                '}' + "\n";
    }
}
