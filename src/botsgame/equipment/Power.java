/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package botsgame.equipment;

/**
 *
 * @author pavel.tretyakov
 */
    public class Power extends Equipment{
        public String name;
        public int power;

        public void nuclearReactor(){
            this.name = "РИТЭГ";
            this.power = 5;
            this.durability = 20;
        }
        public void dieselEngine(){
            this.name = "Дизель-генератор";
            this.power = 3;
            this.durability = 30;
        }
        public void solarPlate() {
            this.name = "Солнечная батарея";
            this.power = 1;
            this.durability = 10;
        }
    }
