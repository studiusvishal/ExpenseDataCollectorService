package com.bhavsar.vishal.service.datacollector.db.entity.expense;

import com.bhavsar.vishal.service.datacollector.db.entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "category_table")
@Entity
public class CategoryRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "categoryRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExpenseRecord> expenseRecordList;
}
