/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package botsgame.bots;
import botsgame.equipment.Equipment;
/**
 *
 * @author pavel.tretyakov
 */
public class BotRemains extends Obstacles{
    
    public Equipment part;

    public BotRemains(Equipment part) {
        this.part=part;
    }

    public Equipment getPart() {
        return this.part;
    }

    
}
