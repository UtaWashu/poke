package com.example.pokemon;

import java.util.Objects;

public class Pokemon {
    private String name;
    private String url;
    private String imageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        String[] urlParts = url.split("/");
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/" + urlParts[urlParts.length - 1] + ".png";
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pokemon pokemon = (Pokemon) o;
        return Objects.equals(name, pokemon.name) &&
                Objects.equals(url, pokemon.url) &&
                Objects.equals(imageUrl, pokemon.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url, imageUrl);
    }
}
