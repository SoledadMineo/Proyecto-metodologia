/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Chory77
 */
public class Loader {
    
    public static BufferedImage ImageLoader (String patch){
        
        try {
           return ImageIO.read(Loader.class.getResource(patch));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    
    }


}
    

