package prob03;

public class Book {
    private int no;

    private String title;

    private String author;

    private int stateCode;

    public Book(int no, String title, String author) {
        this.no = no;
        this.title = title;
        this.author = author;
        this.stateCode = 1;
    }

    public void rent() {
        this.stateCode = 0;
    }

    public void print() {
        System.out.print("책 제목:" + title + ", 작가:" + author + ", 대여 유무:");
        if(stateCode == 0) {
            System.out.println("대여중");
        } else {
            System.out.println("재고있음");
        }
    }
}
