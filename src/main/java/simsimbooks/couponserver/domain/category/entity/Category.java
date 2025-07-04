package simsimbooks.couponserver.domain.category.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Table(name = "categories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private final Set<Category> children = new HashSet<>();

    private Category(String name, Category parent) {
        this.name = name;
        this.parent = parent;
    }

    //정적 팩토리 메서드
    public static Category of(String name, Category parent) {
        return new Category(name, parent);
    }

    public static Category of(String name){
        return new Category(name, null);
    }

    public void changeName(@Nullable String name){
        Optional.ofNullable(name).ifPresent(n -> this.name = n);
    }

    public void changeParent(Category parent) {
        if(this.parent != null && this.parent.equals(parent)) return;
        this.parent = parent;
        parent.getChildren().add(this);
    }
}
