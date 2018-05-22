package Setup;

import Game.Object;
import Game.Papier;
import Game.Schaar;
import Game.Steen;

import javax.swing.*;

public class Main extends JPanel {


    public static void main(String[] args) {
        Menu menu = new Menu();
    }

    public Main () {
        Settings.getInstance();


    }

    public int compare(Object one, Object two){
        if(one == two)
            return 0;
        else if (one instanceof Papier && two instanceof Steen)
            return 1;
        else if (one instanceof Steen && two instanceof Schaar)
            return 1;
        else if (one instanceof Schaar && two instanceof Papier)
            return 1;
        else
            return 2;
    }
}
