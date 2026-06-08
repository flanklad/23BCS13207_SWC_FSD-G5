package swc.assignment1fsswc;

public class Product {
    private Long id;
    private String title;
    private String description;
    private double price;
    private double rating;
    private int stock;
    private String category;
    private String thumbnail;

    public Product(Long id, String title, String description, double price,
                   double rating, int stock, String category, String thumbnail) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.rating = rating;
        this.stock = stock;
        this.category = category;
        this.thumbnail = thumbnail;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public double getRating() { return rating; }
    public int getStock() { return stock; }
    public String getCategory() { return category; }
    public String getThumbnail() { return thumbnail; }
}
