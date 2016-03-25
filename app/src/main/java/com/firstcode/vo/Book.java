package com.firstcode.vo;

/**
 * Created by wangjinliang on 2016/3/10.
 */
public class Book {
    public String getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPages() {

        return pages;
    }

    public String getName() {
        return name;
    }

    private String author;
    private double price;
    private int pages;
    private String name;

    @Override
    public String toString() {
        return "Book{" +
                "author='" + author + '\'' +
                ", price=" + price +
                ", pages=" + pages +
                ", name='" + name + '\'' +
                '}' + "\n";
    }
}
