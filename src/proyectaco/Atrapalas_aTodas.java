/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectaco;

import com.sun.jdi.connect.spi.Connection;

/**
 *
 * @author N
 */
public class Atrapalas_aTodas {
  
  public void nombre(String n)throws Exception
    {
        String numeros="1,2,3,4,5,6,7,8,9,0";
        if(n.equals("")){
                throw new Exception("Ingrese un nombre valido");
           
        }
        if(n.contains(numeros)){
            throw new Exception("Ingrese un nombre valido");
        }
        
    }
    public void password(String n)throws Exception
    {
        if(n.equals("")||n.length()<10){
             throw new Exception("Password no valido");
        }
    }
    
    public int tipo(String t) throws Exception{
        if(t.equals("Administrador")){
                  return 1;
              }if(t.equals("Cliente")){
                  return 2;
              }
              if(t.isBlank()){
                  throw new Exception("ERROR. Seleccione el tipo de usuario");
              }
              return 0;
    }
    public void id(String id)throws Exception{//revisar otras validaciones para id
        int i=Integer.parseInt(id);
        if(i==0){
            throw new Exception("ERROR. Ingrese un id valido");
        }
    }
    
    public void producto(String p)throws Exception{
      
        if(p.equals(" ")){
            throw new Exception("ERROR. Ingrese un producto valido");
        }
    }
    public void descripcion(String d) throws Exception{
        if(d.equals("")||d.length()<10||d.matches("1234567890")){
            throw new Exception("ERROR. Ingrese una descripcion valida");
        }
    }
    public void precio(String p) throws Exception{
        float precio=Float.parseFloat(p);
        if(p.equals("")|| precio<0||precio>10000){
            throw new Exception("ERROR. Precio no valido");
        }
    }
    public void cantidad(String c) throws Exception{
        int l=c.length(), s[]=new int[10];
        int cant=Integer.parseInt(c);
        if(c.equals("")||cant<1||cant>5000){
            throw new Exception("ERROR. Cantidad de productos no valida");
        }
    }
    public void repetidos(String c, String n) throws Exception{
        if(c.equals(n)||n.equals(c)){
            throw new Exception("ERROR. Los nombres son iguales");
        }
    }
}
    

