package com.recipes.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "RECIPES")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

    @Id @GeneratedValue private int id;

    @NotEmpty private String name;

    @JsonFormat(pattern = "dd‐MM‐yyyy HH:mm") private LocalDateTime createdAt;

    private boolean vegetarian;

    private int nbPeople;

    @ElementCollection @CollectionTable(name = "INGREDIENT") @LazyCollection(LazyCollectionOption.FALSE)
    private List<String> ingredients;

    @ElementCollection @CollectionTable(name = "INSTRUCTION") @LazyCollection(LazyCollectionOption.FALSE)
    private List<String> instructions;

}
