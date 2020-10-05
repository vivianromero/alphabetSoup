package src.models;

import java.util.UUID;

public class AlphabetSoup {

    private UUID id;
    private AlphabetSoupGrid alphabetSoupGrid;
    private int posi; //posición en el arreglo de las sopas que se van creado

    public AlphabetSoup(UUID id,AlphabetSoupGrid alphabetSoupGrid) {
        this.id = id;
        this.alphabetSoupGrid = alphabetSoupGrid;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public AlphabetSoupGrid getAlphabetSoupGrid() {
        return alphabetSoupGrid;
    }

    public void setAlphabetSoupGrid(AlphabetSoupGrid alphabetSoupGrid) {
        this.alphabetSoupGrid = alphabetSoupGrid;
    }

    public int getPosi() {
        return posi;
    }

    public void setPosi(int posi) {
        this.posi = posi;
    }
}
