/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

/**
 *
 * @author tsowa
 */
public class T2 {
    private Integer myInt = null;
    private String myString = null;
    
    public T2() { }
    
    public T2(Integer i, String s) {
      myInt = i;
      myString = s;
    }
    
    @Override
    public String toString() {
      return "int=" + myInt + " str=" + myString;
    }
  }