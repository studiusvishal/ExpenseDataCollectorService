package com.bhavsar.vishal.service.datacollector.db.entity.expense;

import com.bhavsar.vishal.service.datacollector.CustomJsonDateDeserializer;
import com.bhavsar.vishal.service.datacollector.db.entity.user.User;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "expense_data_table")
@Entity
public class ExpenseRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long expenseId;

    @Column
    private Date expenseDate;

    @Column(name = "description", length = 180)
    private String description;

    @Column
    private double expenseAmount;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    public void setExpenseDate(final Date expenseDate) {
        this.expenseDate = expenseDate;
    }

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryRecord categoryRecord;
}
