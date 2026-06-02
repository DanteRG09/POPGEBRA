/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package GlobalConfig;
import java.awt.*;
/**
 *
 * @author Dante
 */
public class TamañosColoresPosicion {
    private final int[] RTamaño = {1380,760};
    private final int [] RegPanTamaño = {420,525};
    
    private final Dimension RegPanPosición = new Dimension(420, 525);
    private final Dimension RegContFormPosicion = new Dimension(400, 500);
    private final Dimension TamañoBoton = new Dimension (75,25);
    
    private final String ColorPanel = "#E2AD3D";
    private final String ColorFondo = "#1E1E1E";
    private final String ColorTxt = "#000000";
    
    public int getTamañoVentana(int a){
        return RTamaño[a];
    }
    
    public int getTamañoPanelRegistro(int a){
        return RegPanTamaño[a];
    }
    
    
    public Dimension getPosicionDelPanelRegistro(){
        return RegPanPosición;
    }
    
    public Dimension getPosicionFormularioR(){
        return RegContFormPosicion;
    }
    
    public Dimension getTamañoBoton(){
        return TamañoBoton;
    }
    
    public String getColorPanel(){
        return ColorPanel;
    }
    
    public String getColorFondo(){
        return ColorFondo;
    }
    
    public String getColortxt(){
        return ColorTxt;
    }
    
}
