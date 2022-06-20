/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author oneil
 */
public enum Color {
    
        RED (1),
        BLUE (2),
        YELLOW (3),
        GREEN (4)
        ;
        
    private int colorValue;
    
    private Color(int value) 
    {
        this.colorValue = value;
        System.out.println("");
    }
    public int getColorValue()
    {
        return colorValue;
    }
    
}
