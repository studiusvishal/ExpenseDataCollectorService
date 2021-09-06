package com.bhavsar.vishal.service.datacollector.model;

import com.bhavsar.vishal.service.datacollector.CustomJsonDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private Date expenseDate;

    @Column(name = "description", length = 180)
    private String description;

    @Column
    private String category;

    @Column
    private double expenseAmount;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    public void setExpenseDate(final Date expenseDate) {
        this.expenseDate = expenseDate;
    }
}
