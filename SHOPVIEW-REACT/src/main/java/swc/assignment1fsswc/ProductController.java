package swc.assignment1fsswc;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {

    @GetMapping("/products")
    public List<Product> getProducts() {
        return List.of(
            new Product(1L, "Wireless Noise-Cancelling Headphones",
                "Premium over-ear headphones with 30-hour battery and active noise cancellation.",
                299.99, 4.7, 142, "Electronics",
                "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=400&h=300&fit=crop"),
            new Product(2L, "Mechanical Keyboard",
                "TKL mechanical keyboard with RGB backlight and tactile brown switches.",
                129.99, 4.5, 87, "Electronics",
                "https://images.unsplash.com/photo-1587829741301-dc798b83add3?w=400&h=300&fit=crop"),
            new Product(3L, "Ergonomic Office Chair",
                "Lumbar support mesh chair with adjustable armrests and 5-year warranty.",
                449.00, 4.8, 33, "Furniture",
                "https://images.unsplash.com/photo-1580480055273-228ff5388ef8?w=400&h=300&fit=crop"),
            new Product(4L, "Ultrawide Monitor 34\"",
                "3440×1440 IPS panel, 144Hz refresh rate, HDR400, USB-C 90W charging.",
                699.99, 4.6, 19, "Electronics",
                "https://images.unsplash.com/photo-1527443224154-c4a3942d3acf?w=400&h=300&fit=crop"),
            new Product(5L, "Running Shoes Pro",
                "Lightweight breathable mesh with responsive foam midsole for long-distance runs.",
                119.95, 4.4, 210, "Footwear",
                "https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=400&h=300&fit=crop"),
            new Product(6L, "Stainless Steel Water Bottle",
                "Double-walled vacuum insulation, keeps drinks cold 24h or hot 12h. 32 oz.",
                34.99, 4.9, 560, "Kitchen",
                "https://images.unsplash.com/photo-1602143407151-7111542de6e8?w=400&h=300&fit=crop"),
            new Product(7L, "Smart Watch Series X",
                "Health tracking, GPS, blood oxygen, ECG, and 7-day battery life.",
                399.00, 4.6, 75, "Wearables",
                "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=400&h=300&fit=crop"),
            new Product(8L, "Portable SSD 2TB",
                "USB-C 3.2 Gen2 speeds up to 2000MB/s. Shock-resistant and compact.",
                179.99, 4.7, 98, "Storage",
                "https://images.unsplash.com/photo-1597872200969-2b65d56bd16b?w=400&h=300&fit=crop"),
            new Product(9L, "LED Desk Lamp",
                "Adjustable color temperature and brightness, USB-A charging port built-in.",
                49.99, 4.3, 305, "Lighting",
                "https://images.unsplash.com/photo-1507473885765-e6ed057f782c?w=400&h=300&fit=crop"),
            new Product(10L, "Yoga Mat Premium",
                "Non-slip 6mm thick TPE mat with alignment lines and carry strap.",
                55.00, 4.5, 180, "Fitness",
                "https://images.unsplash.com/photo-1544367567-0f2fcb009e0b?w=400&h=300&fit=crop"),
            new Product(11L, "French Press Coffee Maker",
                "12-cup borosilicate glass with stainless steel plunger. Dishwasher safe.",
                39.95, 4.6, 240, "Kitchen",
                "https://images.unsplash.com/photo-1510707577719-ae7c14805e3a?w=400&h=300&fit=crop"),
            new Product(12L, "Webcam 4K",
                "4K 30fps / 1080p 60fps with auto-framing AI, built-in stereo mic, privacy shutter.",
                199.99, 4.4, 62, "Electronics",
                "https://images.unsplash.com/photo-1587826088-57ba5c367c27?w=400&h=300&fit=crop")
        );
    }
}
