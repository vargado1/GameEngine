package cs.cvut.fel.pjv.demo.controller;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;

public class Controller {
    /**
     * Metóda pre obsluhu udalosti pohybu doprava.
     *
     * @return EventHandler pre udalosť pohybu doprava.
     */
    public EventHandler<KeyEvent> moveRight(){
        return null;
    }

    /**
     * Metóda pre obsluhu udalosti pohybu doľava.
     *
     * @return EventHandler pre udalosť pohybu doľava.
     */
    public EventHandler<KeyEvent> moveLeft(){
        return null;
    }

    /**
     * Metóda pre obsluhu udalosti stavby.
     *
     * @return EventHandler pre udalosť stavby.
     */
    public EventHandler<MouseDragEvent> build(){
        return null;
    }

    /**
     * Metóda pre obsluhu udalosti výroby predmetu.
     *
     * @return EventHandler pre udalosť výroby predmetu.
     */
    public EventHandler<MouseEvent> craftItem(){return null;}

    /**
     * Metóda pre obsluhu udalosti útoku na nepriateľa.
     *
     * @return EventHandler pre udalosť útoku na nepriateľa.
     */
    public EventHandler<MouseEvent> attacEnemy(){return null;}

    /**
     * Metóda pre obsluhu udalosti odstránenia bloku.
     *
     * @return EventHandler pre udalosť odstránenia bloku.
     */
    public EventHandler<MouseEvent> removeBlock(){return null;}

    /**
     * Metóda pre obsluhu udalosti odomknutia portálu.
     *
     * @return EventHandler pre udalosť odomknutia portálu.
     */
    public EventHandler<MouseDragEvent> unlockPortal(){return null;}
}
