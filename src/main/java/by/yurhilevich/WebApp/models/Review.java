package by.yurhilevich.WebApp.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "definition")
    private String definition;

    @Column(name = "date")
    private Date date;

    @Column(name = "rating")
    private double rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_user_id")
    private User user;

    @OneToMany(mappedBy = "review")
    private List<Comment> comments;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Review.Status status = Status.MODERATE;

    public enum Status {
        MODERATE, SUCCESS, BAD;

        public String getDisplayRole(){
            return this.name();
        }

        public  Status fromString(String status) {
            if (status == null) {
                throw new IllegalArgumentException("Status string cannot be null");
            }
            try {
                return Status.valueOf(status.toUpperCase()); // Приводим строку к верхнему регистру
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid status value: " + status, e);
            }
        }
    }
}
