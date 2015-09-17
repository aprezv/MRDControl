/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MRDControl;

/**
 *
 * @author Armando
 */
public class Params {

    private int doorUp;
    private int doorDown;

    public int getDoorUp() {
        return doorUp;
    }

    public void setDoorUp(int doorUp) {
        if (doorUp == 0) {
            this.doorUp = 5000;
        } else {
            this.doorUp = doorUp*60000;
        }
    }

    public int getDoorDown() {
        return doorDown;
    }

    public void setDoorDown(int doorDown) {
        if (doorDown == 0) {
            this.doorDown = 5000;
        } else {
            this.doorDown = doorDown*60000;
        }
    }

}
