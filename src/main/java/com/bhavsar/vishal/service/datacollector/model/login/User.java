package com.bhavsar.vishal.service.datacollector.model.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "\"user\"")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;
}
