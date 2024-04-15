package cs.cvut.fel.pjv.demo.view;

import cs.cvut.fel.pjv.demo.view.tools.Tool;

import java.util.ArrayList;

public class SpecialBlock extends Block{
    /**
     * zoznam objectov v danom bloku
     */
    ArrayList<Object> inventory = new ArrayList<>();

    public SpecialBlock(boolean isCraftable, boolean canFall, String type) {
        super(isCraftable, canFall, type);
    }

    /**
     *  Metóda na získanie lootu z SpecialBlock, vracia položku typu Item na základe pozície v inventáriu.
     * @param possition pozicia v inventary
     * @return item na danej pozicii
     */
    public Item getLoot(int possition){
        return null;
    }

    /**
     *  Metóda na získanie lootu z SpecialBlock, vracia položku typu Block na základe pozície v inventáriu.
     * @param possition pozicia v inventary
     * @return blok na danej pozicii
     */
    public Block getLoot(int possition){
        return null;
    }

    /**
     *  Metóda na získanie lootu z SpecialBlock, vracia položku typu Material na základe pozície v inventáriu.
     * @param possition pozicia v inventary
     * @return material na danej pozicii
     */
    public Material getLoot(int possition){
        return null;
    }

    /**
     *  Metóda na získanie lootu z SpecialBlock, vracia položku typu Tool na základe pozície v inventáriu.
     * @param possition pozicia v inventary
     * @return nastroj na danej pozicii
     */
    public Tool getLoot(int possition){
        return null;
    }

    /**
     * Metóda na pridávanie lootu do SpecialBlock, pridáva položku Item do inventára.
     * @param item predmet na pridanie
     */
    public void addLoot(Item item){}

    /**
     * Metóda na pridávanie lootu do SpecialBlock, pridáva položku Block do inventára.
     * @param block blok na pridanie
     */
    public void addLoot(Block block){}

    /**
     * Metóda na pridávanie lootu do SpecialBlock, pridáva položku Material do inventára.
     * @param material material na pridanie
     */
    public void addLoot(Material Material){}

    /**
     * Metóda na pridávanie lootu do SpecialBlock, pridáva položku Tool do inventára.
     * @param tool nastroj na pridanie
     */
    public void addLoot(Tool tool){}
}
