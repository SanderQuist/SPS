package Game;

import Game.Object;

import java.io.IOException;
import java.io.Serializable;

public class Schaar extends Object implements Serializable {


    public Schaar() throws IOException {
        super("Resources\\scissors.png");
    }


}