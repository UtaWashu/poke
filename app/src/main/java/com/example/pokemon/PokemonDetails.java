package com.example.pokemon;

import java.util.List;

public class PokemonDetails {
    private int height;
    private int weight;
    private List<Ability> abilities;


    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }
    private List<Type> types;

    public List<Type> getTypes() {
        return types;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

}


