package spring.jpa.standard.mapping.example.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

//@Entity
public class ExampleCategory {
    @Id @GeneratedValue
    @Column(name = "CATEGORY_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private ExampleCategory parent; // 상위 카테고리

    @OneToMany(mappedBy = "parent")
    private List<ExampleCategory> child = new ArrayList<>();

    private String name;

    @ManyToMany
    @JoinTable(name = "CATEGORY_ITEM",
            joinColumns = @JoinColumn(name = "CATEGORY_ID"),
            inverseJoinColumns = @JoinColumn(name = "ITEM_ID")
    )
    private List<ExampleItem> items = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExampleCategory getParent() {
        return parent;
    }

    public void setParent(ExampleCategory parent) {
        this.parent = parent;
    }

    public List<ExampleCategory> getChild() {
        return child;
    }

    public void setChild(List<ExampleCategory> child) {
        this.child = child;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ExampleItem> getItems() {
        return items;
    }

    public void setItems(List<ExampleItem> items) {
        this.items = items;
    }
}
