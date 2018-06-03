package Game;

import Game.Object;

import java.io.IOException;
import java.io.Serializable;

public class Papier extends Object implements Serializable {


    public Papier() throws IOException {
        super("SPS\\Resources\\Paper.png");
    }


}