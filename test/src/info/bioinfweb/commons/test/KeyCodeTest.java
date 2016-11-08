/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2016  Ben Stöver, Sarah Wiechers
 * <http://commons.bioinfweb.info/Java>
 * 
 * This file is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This file is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package info.bioinfweb.commons.test;


import java.awt.*;  
import java.awt.event.*;  
import javax.swing.*;  
  


public class KeyCodeTest extends KeyAdapter {  
    public static void main(String[] args) throws Exception {  
        JFrame frame = new JFrame();  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        frame.getContentPane().setFocusable(true);  
        frame.getContentPane().addKeyListener(new KeyCodeTest());  
        frame.pack();  
        frame.setSize(200, 200);  
        frame.setLocationRelativeTo(null);  
        frame.setVisible(true);  
    }  
    
  
    public void keyReleased(KeyEvent e) {  
        Component parent = e.getComponent();  
        int code = e.getKeyCode();  
        char c = e.getKeyChar();  
        String text = KeyEvent.getKeyText(code);
        String message = String.format("%d / %c: %s %d", code, c, text, e.getKeyLocation());  
        JOptionPane.showMessageDialog(parent, message);  
    }  
}  