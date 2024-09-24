/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectaco;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author LenovoLap
 */
public class ClaseMaestra {
    private final Connection con;
    public ArrayList < ArrayList<Object> > listaProductos = new ArrayList< >(); 
    //Necesitamos una lista de listas para cada nodo (producto) y que guarde los datos de cada uno
    public  ArrayList < ArrayList<Object> > listaUsuario = new ArrayList< >();
    public  ArrayList < ArrayList<Object> > listaOfertas = new ArrayList< >();
    
    
    int claveusuario;
    //Para obtener solo productos con el id
    private final ArrayList<Object> ID_P = new ArrayList();
    private String idp[];
    //obtener pedidos
    
   private final ArrayList<Object> PEDIDO = new ArrayList();
    private String pedir[];
   
    public ClaseMaestra(Connection con){
        this.con = con;
        
    }
    
    //CHECAR TODO TIPO DE EXCEPCIONES
    
    public void altaUsuario(String nom, String pass, int tipo){ 
        try{
            Statement sts = con.createStatement();
            sts.addBatch("INSERT INTO usuarios (nombre, password, tipo, cantidad_gastada_acumulada) VALUES ( '" + nom +"', '" + pass + "', " + tipo + ", " + 0 + " )");
            sts.executeBatch();
            JOptionPane.showInternalMessageDialog(null, "Cuenta creada exitosamente!",
             "Cuenta", JOptionPane.INFORMATION_MESSAGE);
            sts.execute("Select * from usuarios WHERE nombre = '" + nom + "' ");
            ResultSet rs = sts.getResultSet(); rs.next();
            JOptionPane.showInternalMessageDialog(null, "Tu ID de usuario es: " + rs.getInt("id_usuario"),
             "Cuenta", JOptionPane.INFORMATION_MESSAGE);
            new Inicio(con).setVisible(true);
        }catch(SQLException e){
            System.out.println("Error" + e);
        }
    }
    
    public void bajaUsuario(int id){    //Checar que no tenga deudas
        try{
            Statement sts = con.createStatement();
            
            String eliminar = "DELETE FROM usuarios WHERE id_usuario = " + id + " ";
            sts.executeUpdate(eliminar);
            JOptionPane.showInternalMessageDialog(null, "Cuenta eliminada exitosamente!",
             "Cuenta", JOptionPane.INFORMATION_MESSAGE);
        }catch(SQLException ex){
            System.out.println(ex.getNextException());
        }
    }
    
    public void modificacionUsuario(int op, int id, String actual, String nuevo/*, int idUs, String dato*/){//El id de usuario se va a pasar dependiendo lo q tenga el textField
        //System.out.println("Ingrese 1 para modificar su nombre o 2 para modificar su contraseña");     //poner para que salga bonito
        try{
            Statement sts = con.createStatement();
        if(op == 1){
            
            sts.execute("Select * from usuarios WHERE id_usuario = " + claveusuario + " ");
            ResultSet rs = sts.getResultSet(); rs.next();
            System.out.println(rs.getString("nombre"));
            System.out.println("Ingrese el nuevo nombre: ");
            String nom = "Daniel Venegas"; //Esto se cambia para despues y q el usuario meta su nombre
            sts.executeUpdate("Update usuario set nombre = '" + nuevo + "' where id_usuario = " + claveusuario + " ");
        }else if(op == 2){ 
            System.out.println("Su contrasenia actual: ");
            sts.execute("Select * from usuarios WHERE id_usuario = " + claveusuario + " ");
            ResultSet rs = sts.getResultSet();
            System.out.println(rs.getString("password"));
            System.out.println("Ingrese la nueva contrasenia: ");
            String contra = "Piedra"; //Esto se cambia para despues y q el usuario meta su nombre
            sts.executeUpdate("Update usuarios set password = '" + nuevo + "' where id_usuario = " + claveusuario + " ");
        }
        }catch(SQLException ex){
            System.out.println(ex.getNextException());
        }
        
    }
    
     public void consultaUsuario(){ //SSobrecarga para mostrar a todos los usuarios
                int reg = 0;
        try{
            getUsuario().clear();
            Statement sts = con.createStatement();
            sts.execute("Select * from alumno");
            ResultSet rs = sts.getResultSet();
            while(rs.next()){
                getUsuario().add(new ArrayList<>());
                getUsuario().get(reg).add(rs.getInt("id"));
                getUsuario().get(reg).add(rs.getString("nombre"));
                getUsuario().get(reg).add(rs.getString("password"));
                reg++;
            }
            Iterator miIterator = getUsuario().iterator();
            while(miIterator.hasNext()){
                System.out.println(miIterator.next() + "\t");
            }
        }catch(SQLException ex){
            JOptionPane.showInternalMessageDialog(null, "ERROR"+ex,"Informacion", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void consultaUsuario(int x){ //Para saber si son todos (admin) o solo el de cliente
        int reg = 0;
        try{
            listaUsuario.clear(); //Para que no se repitan los datos anteriores
            Statement sts = con.createStatement();
            if(x==1){
                sts.execute("Select * from usuarios");
            }else{
                sts.execute("Select * from usuarios WHERE id_usuario = " + claveusuario + " ");
            }
            
            ResultSet rs = sts.getResultSet();
            while(rs.next()){
                listaUsuario.add(new ArrayList<>());
                listaUsuario.get(reg).add(rs.getInt("id_usuario"));
                listaUsuario.get(reg).add(rs.getString("nombre"));
                listaUsuario.get(reg).add(rs.getString("password"));
                listaUsuario.get(reg).add(rs.getInt("tipo"));
                reg++;
            }
            Iterator miIterator = listaUsuario.iterator();
            while(miIterator.hasNext()){
                System.out.println(miIterator.next() + "\t");
            }
        }catch(SQLException ex){
            System.out.println(ex.getNextException());
        }
    }
    
    public void consultaAlmacen(){
        int reg = 0;
        try{
            listaProductos.clear(); //Para que no se repitan los datos anteriores
            Statement sts = con.createStatement();
            sts.execute("Select * from almacen");
            ResultSet rs = sts.getResultSet();
            while(rs.next()){
                listaProductos.add(new ArrayList<>());
                listaProductos.get(reg).add(rs.getInt("id_producto"));
                listaProductos.get(reg).add(rs.getString("nombre_producto"));
                listaProductos.get(reg).add(rs.getString("descripcion"));
                listaProductos.get(reg).add(rs.getFloat("precio"));
                listaProductos.get(reg).add(rs.getInt("cantidad"));
                reg++;
            }
            Iterator miIterator = listaProductos.iterator();
            while(miIterator.hasNext()){
                System.out.println(miIterator.next() + "\t");
            }
        }catch(SQLException ex){
            System.out.println(ex.getNextException());
        }
    }
    public boolean consultaAlmacen(int id){//para buscar producto de venta
        boolean encontro=false;
         String resul[] = new String[5];
        try{
            Statement sts = con.createStatement();
            sts.execute("Select * from almacen where id_producto = " + id + "");    //como es un entero la matricula no se le pone comilla simple
            ResultSet rs = sts.getResultSet();
            while(rs.next()){ 
              encontro=true;
            }
        }catch(SQLException ex){
            System.out.println(ex.getNextException());
        }catch(NumberFormatException ex){
            System.out.println(ex);
        }
        return encontro;
    }
    
    
     public boolean consultaPedido(int id){//para buscar producto de venta
        boolean encontro=false;
         String resul[] = new String[5];
        try{
            Statement sts = con.createStatement();
            sts.execute("Select * from  almacen  where id_pedido = " + id + "");    //como es un entero la matricula no se le pone comilla simple
            ResultSet rs = sts.getResultSet();
            while(rs.next()){ 
              encontro=true;
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.getNextException(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }catch(NumberFormatException ex){
           JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return encontro;
    }
     
     
    public String vende(int id){//busca precio de venta
         String encontro=" ";
         String resul[] = new String[5];
        try{
            Statement sts = con.createStatement();
            sts.execute("Select * from almacen where id_producto = " + id + "");    //como es un entero la matricula no se le pone comilla simple
            ResultSet rs = sts.getResultSet();
            while(rs.next()){ 
              encontro = rs.getString("precio");
            }
        }catch(SQLException ex){
            System.out.println(ex.getNextException());
        }catch(NumberFormatException ex){
            System.out.println(ex);
        }
        return encontro;
    }
    
     public final DefaultTableModel actualizaTablaAlmacen(){
        DefaultTableModel modelo = new DefaultTableModel();
        Object datos[] = new Object[3];
        modelo.addColumn("id_producto");
        modelo.addColumn("nombre_producto");
        modelo.addColumn("descripcion");
        modelo.addColumn("precio");
        modelo.addColumn("cantidad");
        consultaAlmacen();
        for(int j =0; j<getAlmacen().size(); j++){
            datos[0] = getAlmacen().get(j).get(0);
            datos[1] = getAlmacen().get(j).get(1);
            datos[2] = getAlmacen().get(j).get(2);
            datos[3] = getAlmacen().get(j).get(3);
            datos[4] = getAlmacen().get(j).get(4);
            modelo.addRow(datos);
        }
        return modelo;

    }
      public ArrayList<ArrayList<Object>> getAlmacen() {
        return listaProductos;
    }
    
    
   public void modificarOfertas(int op, int id){
       
       int id_producto = 9; //Recibir del usuario
       if(op == 1){ //El usuario quiere eliminar oferta
           try{
            Statement sts = con.createStatement();
            sts.execute("Select * from ofertas WHERE id_producto  = " + id_producto + " " );
            ResultSet rs = sts.getResultSet(); rs.next();
            
            sts.executeUpdate("Update almacen set precio = " + rs.getFloat("precio") + " where id_producto = " + id_producto + " ");
            
            String eliminar = "DELETE FROM ofertas WHERE id_producto = " + id_producto + " ";
            sts.executeUpdate(eliminar);
            JOptionPane.showInternalMessageDialog(null, "Oferta eliminada exitosamente!",
             "Oferta", JOptionPane.INFORMATION_MESSAGE);
            }catch(SQLException e){
                System.out.println("Error" + e);
            }
       }else{
           try{
            Statement sts = con.createStatement();
            sts.execute("Select * from almacen WHERE id_producto  = " + id_producto + " " );
            ResultSet rs = sts.getResultSet(); rs.next();   //PEDIR AL USUARIO NUEVO DESCUENTO
            float nuevo_desc = 10, precio = rs.getFloat("precio"); 
            nuevo_desc /= 100;

            sts.executeUpdate("Update ofertas set descuento = " + nuevo_desc*100 + " where id_producto = " + id_producto + " ");
            sts.executeUpdate("Update ofertas set precio_final = " + (precio + precio*nuevo_desc) + " where id_producto = " + id_producto + " ");
            sts.executeUpdate("Update almacen set precio = " + (precio - precio*nuevo_desc) + " where id_producto = " + id_producto + " ");
            }catch(SQLException e){
                System.out.println("Error" + e);
            }
       }
   }
   
   public void crearOfertas(int id_producto, float descuento){
       try{
            Statement sts = con.createStatement();
            sts.execute("Select * from almacen WHERE id_producto  = " + id_producto + " " );
            ResultSet rs = sts.getResultSet(); rs.next();
            
            float desc = descuento; //Se le va a pedir al usuario, será >0 y <100
            desc /= 100;
            float pactual = rs.getFloat("precio");
            
            sts.addBatch("INSERT INTO ofertas (id_producto, precio, descuento, precio_final) VALUES ( " + id_producto +", " + pactual + ", " + desc*100 + ", " + (pactual- pactual * desc) + " )");
            sts.executeBatch();
            sts.executeUpdate("Update almacen set precio = " + (pactual- pactual * desc) + " where id_producto = " + id_producto + " ");
        }catch(SQLException e){
            System.out.println("Error" + e);
        }
   }
   
   public void mostrarOfertas(){ //El x es para el id de la oferta
       int reg = 0;
        try{
            listaOfertas.clear(); //Para que no se repitan los datos anteriores
            Statement sts = con.createStatement();
            sts.execute("Select * from ofertas");
            ResultSet rs = sts.getResultSet();
            while(rs.next()){
                listaOfertas.add(new ArrayList<>());
                listaOfertas.get(reg).add(rs.getInt("id_producto"));
                listaOfertas.get(reg).add(rs.getFloat("precio"));
                listaOfertas.get(reg).add(rs.getFloat("descuento"));
                listaOfertas.get(reg).add(rs.getFloat("precio_final"));
                reg++;
            }
            Iterator miIterator = listaOfertas.iterator();
            while(miIterator.hasNext()){
                System.out.println(miIterator.next() + "\t");
            }
        }catch(SQLException ex){
            System.out.println(ex.getNextException());
        }
   }
   
public void reporteVentas(){
       int reg = 0;
        try{
            listaOfertas.clear(); //Para que no se repitan los datos anteriores
            Statement sts = con.createStatement();
            sts.execute("Select * from almacen");
            ResultSet rs = sts.getResultSet();
            while(rs.next()){
                listaOfertas.add(new ArrayList<>());
                listaOfertas.get(reg).add(rs.getString("nombre_producto"));
                listaOfertas.get(reg).add(rs.getInt("cantidad_vendida_total"));
                reg++;
            }
            
            ArrayList<Object>  temp = new ArrayList();
            
            for(int i = 0; i < listaOfertas.size()-1; i++) {
                for (int j = i+1; j < listaOfertas.size(); j++) {
                    if((Integer)listaOfertas.get(i).get(1) > (Integer)listaOfertas.get(j).get(1)){
                        temp = listaOfertas.get(i);
                        listaOfertas.set(i, listaOfertas.get(j));
                        listaOfertas.set(j, temp);
                    }
                }
            }
            
            Iterator miIterator = listaOfertas.iterator();
            while(miIterator.hasNext()){
                System.out.println(miIterator.next() + "\t");
            }
        }catch(SQLException ex){
            System.out.println(ex.getNextException());
        }
        Grafica g = new Grafica();
        g.pastel(listaOfertas);
   }

   public void pedidos(int id_producto, int cantidad_producto, int id_universal, int id_usu){ //CHECAR COMO PONER EL ID UNIVERSAL DE LOS PRODUCTOS
       try{
            Statement sts = con.createStatement();
            sts.execute("Select * from almacen WHERE id_producto = " + id_producto + " ");
            ResultSet rs = sts.getResultSet(); rs.next();
            int i = rs.getInt("id_producto"); String n = rs.getString("nombre_producto");
            String d = rs.getString("descripcion");
            float p = rs.getFloat("precio");
            int can = rs.getInt("cantidad");
            int cantp = rs.getInt("cantidad_vendida_total");
            float pacum = rs.getFloat("cantidad_total_ganada");
            if(can >= cantidad_producto){
                sts.executeUpdate("Update almacen set cantidad_vendida_total = " + (cantidad_producto + cantp) + " where id_producto = " + id_producto + " ");
                sts.executeUpdate("Update almacen set cantidad_total_ganada = " + (pacum + p * cantidad_producto) + " where id_producto = " + id_producto + " ");
                sts.executeUpdate("Update almacen set cantidad = " + (can-cantidad_producto) + " where id_producto = " + id_producto + " ");
                
                //Hay que ponerlo en pedidos y meterle al usuario ese ID de pedido
            //Para generar el pedido    
            try{    //CHECAR LO DEL ID DEL PEDIDO
                sts.addBatch("INSERT INTO pedidos (id_pedido, id_producto, id_cliente, cantidad, precio_total) VALUES ( " + id_universal +", " + id_producto + "," + id_usu + ", " + cantidad_producto + ", " + (p * cantidad_producto) + " )");
                sts.executeBatch();
                sts.execute("Select * from usuarios WHERE id_usuario = " + id_usu + " ");
                rs = sts.getResultSet(); rs.next();
                
                sts.executeUpdate("Update usuarios set cantidad_gastada_acumulada = " + (rs.getInt("cantidad_gastada_acumulada") + p * cantidad_producto) + " where id_cliente = " + id_usu + " ");
            }catch(SQLException e){
                System.out.println("Error" + e);
            }//Ya se genero el pedido
                
            }else{
                System.out.println("No hay suficiente producto en almacen disponible");
            }
        }catch(SQLException e){
            System.out.println("Error" + e);
        }
   }
   
   public void cancelarPedidos(int id){ //VARIOS ARTICULOS TIENEN EL MISMO ID DEL PEDIDO, COMO REGRESAMOS TODOS? 
       //CHECAR QUE OTROS USUARIOS NO PUEDAN CANCELAR OTROS PEDIDOS DE OTROS USUARIOS
       System.out.println("Ingrese el id de su pedido a cancelar: ");
       //Recibir ID de pedido 
       int id_pedido=3, reg = 0;
       int todosPedidos[] = new int[100];
       try{
           listaOfertas.clear(); //Para que no se repitan los datos anteriores
           Statement sts = con.createStatement();
           sts.execute("Select * from pedidos WHERE id_pedido = " + id_pedido + " ");
           ResultSet rs = sts.getResultSet();
           
            while(rs.next()){
                listaOfertas.add(new ArrayList<>());
                listaOfertas.get(reg).add(rs.getInt("id_pedido"));
                listaOfertas.get(reg).add(rs.getInt("id_cliente"));
                todosPedidos[reg] = rs.getInt("id_producto");
                listaOfertas.get(reg).add(todosPedidos[reg]);
                listaOfertas.get(reg).add(rs.getInt("cantidad"));
                listaOfertas.get(reg).add(rs.getFloat("precio_total"));
                reg++;
            }
            listaProductos.clear(); 
            Iterator miIterator = listaOfertas.iterator();
            reg = 0;
            
            while(miIterator.hasNext()){
                sts.execute("Select * from almacen WHERE id_producto = " + todosPedidos[reg] + " ");
                ResultSet rs2 = sts.getResultSet();
                while(rs2.next()){
                        listaProductos.add(new ArrayList<>());
                        listaProductos.get(reg).add(rs2.getInt("id_producto"));
                        listaProductos.get(reg).add(rs2.getString("nombre_producto"));
                        listaProductos.get(reg).add(rs2.getString("descripcion"));
                        listaProductos.get(reg).add(rs2.getFloat("precio"));
                        listaProductos.get(reg).add(rs2.getInt("cantidad"));
                        listaProductos.get(reg).add(rs2.getInt("cantidad_vendida_total"));
                        listaProductos.get(reg).add(rs2.getFloat("cantidad_total_ganada"));
                        reg++;
                }
                reg++;
                miIterator.next();
            }
            listaProductos.get(1).get(2);
            reg = 0;
            while(miIterator.hasNext()){
                
                int x1 = ((Integer) listaProductos.get(reg).get(5)).intValue();
                int x2 = ((Integer) listaOfertas.get(reg).get(3)).intValue();
                float y1 = ((Float) listaProductos.get(reg).get(6)).floatValue();
                float y2 = ((Float) listaOfertas.get(reg).get(4)).floatValue();
                
                sts.executeUpdate("Update almacen set cantidad_vendida_total = '" + (x1 - x2) + "' where id_producto = " + todosPedidos[reg] + " ");
                sts.executeUpdate("Update almacen set cantidad_total_ganada = '" + (y1 - y2) + "' where id_producto = " + todosPedidos[reg] + " ");
                
                x1 = ((Integer) listaProductos.get(reg).get(4)).intValue();
                x2 = ((Integer) listaOfertas.get(reg).get(3)).intValue();
                
                sts.executeUpdate("Update almacen set cantidad = '" + (x1 + x2) + "' where id_producto = " + todosPedidos[reg] + " ");
                miIterator.next();
                reg++;
            }
            String eliminar = "DELETE FROM pedidos WHERE id_pedido = " + id_pedido + " ";
            sts.executeUpdate(eliminar);
       }catch(SQLException e){
             JOptionPane.showMessageDialog(null, e.getNextException(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
   }
   
   
   
       
    public void obtenerPedidos(){
        try{
            int cont=0;
            Statement sts = con.createStatement();
            sts.execute("Select * from pedidos");
            ResultSet rs = sts.getResultSet();
            while(rs.next()){
                PEDIDO.add(rs.getInt("id_pedido"));
                cont++;
            }
            pedir = new String [cont];
            for(int mat = 0; mat < cont ; mat++){
                pedir[mat] = PEDIDO.get(mat).toString();
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.getNextException(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
   
   public String[] getPedido() {
        return pedir;
    }
   
   
   
   
   public void mostrarPedidos(int x){ //Para que admin o usuario puedan ver sus pedidos
       String a;
       if(x!= 0){  //usuario
           a = "Select * from pedidos WHERE id_usuario = " + x + " ";
       }else{ //admin
           a = "Select * from pedidos";
       }
       int reg = 0;
        try{
            listaUsuario.clear(); //Para que no se repitan los datos anteriores
            Statement sts = con.createStatement();
            sts.execute(a);
            ResultSet rs = sts.getResultSet();
            while(rs.next()){
                listaUsuario.add(new ArrayList<>());
                listaUsuario.get(reg).add(rs.getInt("id_pedido"));
                listaUsuario.get(reg).add(rs.getInt("id_cliente"));
                listaUsuario.get(reg).add(rs.getInt("id_producto"));
                listaUsuario.get(reg).add(rs.getInt("cantidad"));
                listaUsuario.get(reg).add(rs.getFloat("precio_total"));
                reg++;
            }
            Iterator miIterator = listaUsuario.iterator();
            while(miIterator.hasNext()){
                System.out.println(miIterator.next() + "\t");
            }
        }catch(SQLException ex){
             JOptionPane.showMessageDialog(null, ex.getNextException(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
   }
   
  public void obtenerId_productos  (){
        try{
            int cont=0;
            Statement sts = con.createStatement();
            sts.execute("Select * from almacen");
            ResultSet rs = sts.getResultSet();
            while(rs.next()){
                ID_P.add(rs.getInt("id_producto"));
                cont++;
            }
            idp = new String [cont];
            for(int mat = 0; mat < cont ; mat++){
                idp[mat] = ID_P.get(mat).toString();
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.getNextException(), "ERROR", JOptionPane.ERROR_MESSAGE);
           
        }
    }
   
   public void mostrarApartados(int x){
       int reg = 0;
        try{
            listaUsuario.clear(); //Para que no se repitan los datos anteriores
            Statement sts = con.createStatement();
            if(x==1){
                sts.execute("Select * from apartados");
            }else{
                sts.execute("Select * from apartados WHERE id_usuario = " + claveusuario + " ");
            }
            
            ResultSet rs = sts.getResultSet();
            while(rs.next()){
                listaUsuario.add(new ArrayList<>());
                listaUsuario.get(reg).add(rs.getInt("id_apartados"));
                listaUsuario.get(reg).add(rs.getInt("id_usuario"));
                listaUsuario.get(reg).add(rs.getInt("id_producto"));
                listaUsuario.get(reg).add(rs.getInt("cantidad"));
                reg++;
            }
            Iterator miIterator = listaUsuario.iterator();
            while(miIterator.hasNext()){
                System.out.println(miIterator.next() + "\t");
            }
        }catch(SQLException ex){
            System.out.println(ex.getNextException());
        }
   }
   
   public void apartados(int op, int id, int cant){
       try{
        Statement sts = con.createStatement();
        if(op == 1){ //Para crear apartado
            int id_producto = id; //PEDIR AL USUARIO
            int cantidad_requerida = cant; //PEDIR AL USUARIO //CHECAR QUE SEA MAYOR A 0
            //Verificar que haya en existencia
            sts.execute("Select * from almacen WHERE id_producto = " + id_producto + " "); 
            ResultSet rs = sts.getResultSet(); rs.next();
            int cantidad = rs.getInt("cantidad");
            if(cantidad_requerida <= cantidad){
                sts.addBatch("INSERT INTO apartados (id_usuario, id_producto, cantidad) VALUES ( " + claveusuario +", " + id_producto + ", " + cantidad + ")");
                sts.executeBatch();
                sts.executeUpdate("Update almacen set cantidad = " + (cantidad - cantidad_requerida) + " where id_producto = " + id_producto + " ");
            }else{
                System.out.println("No se puede apartar ya que no hay inventario en el almacen");
            }
        }
        if(op ==2){ //Para modificar apartado
            
        }if(op == 3){ //Para eliminar apartado
            int id_apartado = 1; //PEDIR AL USUARIO
            //Verificar que haya en existencia
            sts.execute("Select * from apartado WHERE id_apartado = " + id_apartado + " "); 
            ResultSet rs = sts.getResultSet(); rs.next();
            int cantidad = rs.getInt("cantidad");
            int id_producto = rs.getInt("id_producto");
            
            sts.execute("Select * from almacen WHERE id_producto = " + id_producto + " "); 
            ResultSet rs2 = sts.getResultSet(); rs.next();
            sts.executeUpdate("Update almacen set cantidad = " + (cantidad + rs2.getInt("cantidad")) + " where id_producto = " + id_producto + " ");
            String eliminar = "DELETE FROM apartados WHERE id_apartado = " + id_apartado + " ";
            sts.executeUpdate(eliminar);
            JOptionPane.showInternalMessageDialog(null, "Apartado eliminada exitosamente!",
             "Apartado", JOptionPane.INFORMATION_MESSAGE);
        }
       }catch(SQLException e){
           System.out.println("Error" + e);
       }
   }
   
   public int login(int idus, String pas){
       try{
            Statement sts = con.createStatement();
            sts.execute("Select * from usuarios WHERE id_usuario = " + idus + " ");
            ResultSet rs = sts.getResultSet(); rs.next();
            if(rs.getInt("id_usuario") == idus && pas.equals(rs.getString("password"))){
                System.out.println("Bienvenido al sistema!");
                claveusuario = idus;
                if(rs.getInt("tipo") == 1){
                    return 1;
                }else{
                    return 2;
                }
            }else{
                System.out.println("Error, usuario y/o contra no valido ");
                return 0;
            }
            
        }catch(SQLException e){
            System.out.println("Error" + e);
            return 0;
        }
   }
   
   public void ingresarProducto(String n, String d, float p, int c){ //HACER EXCEPCIONES
      
       
       String nom = n;
       String desc = d;
       float precio = p;
       int cant = c;
  
       try{
            Statement sts = con.createStatement();
            sts.addBatch("INSERT INTO almacen (nombre_producto, descripcion, precio, cantidad, cantidad_vendida_total, cantidad_total_ganada) VALUES ( '" + nom +"', '" + desc + "', " + precio + ", " + cant + ", " + 0 + ", " + 0 + " )");
            sts.executeBatch();
            JOptionPane.showInternalMessageDialog(null, "Articulo recibido exitosamente!",
             "Cuenta", JOptionPane.INFORMATION_MESSAGE);
            new Menu_admin(con).setVisible(true);
        }catch(SQLException e){
            System.out.println("Error" + e);
        }
   }
   public final DefaultTableModel actualizaTablaoferta(){
        DefaultTableModel modelo = new DefaultTableModel();
        Object datos[] = new Object[5];
        modelo.addColumn("id_producto");
        modelo.addColumn("precio");
        modelo.addColumn("descuento");
        modelo.addColumn("precio_final");

        mostrarOfertas();
        for(int j =0; j<getOferta ().size(); j++){
            datos[0] = getOferta().get(j).get(0);
            datos[1] = getOferta().get(j).get(1);
            datos[2] = getOferta().get(j).get(2);
            datos[3] = getOferta().get(j).get(3);

           // datos[2] = getAlmacen().get(j).get(5);
            modelo.addRow(datos);
        }
        return modelo;
   }
   public ArrayList<ArrayList<Object>> getOferta() {
        return listaOfertas;
    }
   
   public final DefaultTableModel actualizaTabla(){
        DefaultTableModel modelo = new DefaultTableModel();
        Object datos[] = new Object[5];
        modelo.addColumn("id_producto");
        modelo.addColumn("nombre_producto");
        modelo.addColumn("descripcion");
        modelo.addColumn("precio");
        modelo.addColumn("cantidad");
        consultaAlmacen();
        for(int j =0; j<getAlmacen().size(); j++){
            datos[0] = getAlmacen().get(j).get(0);
            datos[1] = getAlmacen().get(j).get(1);
            datos[2] = getAlmacen().get(j).get(2);
            datos[3] = getAlmacen().get(j).get(3);
            datos[4] = getAlmacen().get(j).get(4);
           // datos[2] = getAlmacen().get(j).get(5);
            modelo.addRow(datos);
        }
        return modelo;

    }

    
//tabla pedido
      public final DefaultTableModel actualizaTablaPedido(int id_us){
        DefaultTableModel modelo = new DefaultTableModel();
        Object datos[] = new Object[4];
        modelo.addColumn("id_pedido");
        modelo.addColumn("id_producto");
        modelo.addColumn("cantidad");
        modelo.addColumn("precio_total");
       
        mostrarPedidos(id_us);
        for(int j =0; j<getAlmacen().size(); j++){
            datos[0] = getAlmacen().get(j).get(0);
            datos[1] = getAlmacen().get(j).get(1);
            datos[2] = getAlmacen().get(j).get(2);
            datos[3] = getAlmacen().get(j).get(3);
          
           // datos[2] = getAlmacen().get(j).get(5);
            modelo.addRow(datos);
        }
        return modelo;

    }
        
   

//tabla usuarios
  public final DefaultTableModel actualizaTablausuario(){
        DefaultTableModel modelo = new DefaultTableModel();
        Object datos[] = new Object[3];
        modelo.addColumn("id_usuario");
        modelo.addColumn("nombre");
        modelo.addColumn("password");

        consultaUsuario();
        for(int j =0; j<getUsuario().size(); j++){
            datos[0] = getUsuario().get(j).get(0);
            datos[1] = getUsuario().get(j).get(1);
            datos[2] = getUsuario().get(j).get(2);
            
           // datos[2] = getAlmacen().get(j).get(5);
            modelo.addRow(datos);
        }
        return modelo;

    }
  
  
  //tabla apartados
  public final DefaultTableModel actualizaTabla_Ap(int x){
      DefaultTableModel modelo = new DefaultTableModel();
        Object datos[] = new Object[4];
        modelo.addColumn("id_apartados");
        modelo.addColumn("id_usuario");
        modelo.addColumn("id_producto");
         modelo.addColumn("cantidad");

        mostrarApartados(x);
        for(int j =0; j<getApartado().size(); j++){
            datos[0] = getApartado().get(j).get(0);
            datos[1] = getApartado().get(j).get(1);
            datos[2] = getApartado().get(j).get(2);
            datos[3] = getApartado().get(j).get(3);
           // datos[2] = getAlmacen().get(j).get(5);
            modelo.addRow(datos);
        }
        return modelo;
  }
   public ArrayList<ArrayList<Object>> getApartado () {
        return listaUsuario;
    }
public ArrayList<ArrayList<Object>> getUsuario() {
        return listaUsuario;
    }
   
public void imprimirPDFAdmin(){
    int reg = 0;
        try{
            listaProductos.clear(); //Para que no se repitan los datos anteriores
            Statement sts = con.createStatement();
            sts.execute("Select * from almacen");
            ResultSet rs = sts.getResultSet();
            while(rs.next()){
                listaProductos.add(new ArrayList<>());
                listaProductos.get(reg).add(rs.getInt("id_producto"));
                listaProductos.get(reg).add(rs.getString("nombre_producto"));
                listaProductos.get(reg).add(rs.getFloat("precio"));
                listaProductos.get(reg).add(rs.getInt("cantidad"));
                listaProductos.get(reg).add(rs.getInt("cantidad_vendida_total"));
                listaProductos.get(reg).add(rs.getInt("cantidad_total_ganada"));
                reg++;
            }

        }catch(SQLException ex){
            System.out.println(ex.getNextException());
        }
    ImprimirPDF p = new ImprimirPDF(1);
    p.imprimir(listaProductos);
}
    public int IDUniversal(){
        int id = 0, reg= 0;
        try{
            listaProductos.clear(); //Para que no se repitan los datos anteriores
            Statement sts = con.createStatement();
            sts.execute("Select * from auxiliar");
            ResultSet rs = sts.getResultSet();
            while(rs.next()){
                listaProductos.add(new ArrayList<>());
                listaProductos.get(reg).add(rs.getInt("id_pedido_genere"));
                reg++;
            }
        }catch(SQLException ex){
            System.out.println(ex.getNextException());
        }        
        id = (Integer)listaProductos.get(listaProductos.size()-1).get(0);
        id++;
        return id; 
    }
    
    public void AumentarIDU(){
        int id = 0, reg= 0;
        try{
            listaProductos.clear(); //Para que no se repitan los datos anteriores
            Statement sts = con.createStatement();
            sts.addBatch("INSERT INTO auxiliar (aux) VALUES ( " + 0 + " )");
            sts.executeBatch();
        }catch(SQLException ex){
            System.out.println(ex.getNextException());
        }        
    }
}