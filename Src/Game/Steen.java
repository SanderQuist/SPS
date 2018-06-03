package Game;

import Game.Object;

import java.io.IOException;
import java.io.Serializable;

public class Steen extends Object implements Serializable {


    public Steen() throws IOException {
        super("SPS\\Resources\\Rock.png");
    }


}