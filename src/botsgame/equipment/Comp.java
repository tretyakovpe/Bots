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

    public class Comp{
        public String name;
        public int viewDistance;
        public void deskComp(){
            this.name="Десктоп";
            this.viewDistance=100;
        }
        public void militaryComp(){
            this.name="Военный компьютер";
            this.viewDistance=200;
        }
        public void nasaComp(){
            this.name="Спутниковое управление";
            this.viewDistance=500;
        }
    }
