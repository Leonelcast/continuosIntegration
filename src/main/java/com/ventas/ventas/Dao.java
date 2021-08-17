package com.ventas.ventas;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

/**
 * <p>Clase utilizada para crear los servicios que interactuan con la base de datos de oracle </p>
 */
@Repository
public class Dao {
    /**
        *<p>Constructor de la clase dao </p> 
    */
    public Dao(){

    }

    /**
        *<p>Inicializar el jdbcTemplate para generar las consultas </p> 
    */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
        *<p>Constructor para inicializar los servicios de jdbc </p> 
    */
    public Dao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    //TODO: Método para el manejo de catalogo de terminales 

    /**
     * <p>Método para listar todas las terminales en la pagina de inicio </p>
          * @return listaDis la lista con todos las terminales disponibles  
    */
    public List<Telefono> list() {
        String sql = "select *from VENTAS.bodega inner join VENTAS.fabrica on origen = id_fabrica  where id_estados = 3 or id_estados = 4";
        List<Telefono> listaDis = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Telefono.class));
        return listaDis;

    }

     /**
     * <p>Método para listar las caracterizticas de solo una terminal</p>
     * @param id es la llave primaria de cada terminal
     * @return listaIn es una lista con las caracterizticas del telefono con el id que se manda como parametro 
    */
    public List<Telefono> list2(int id) {
        String sql = "select *from VENTAS.bodega inner join VENTAS.fabrica on origen = id_fabrica where id_bodega  = ?";
        Object[] args = { id };
        List<Telefono> listaIn = jdbcTemplate.query(sql, args, BeanPropertyRowMapper.newInstance(Telefono.class));
        return listaIn;

    }

    /**
     * <p>Método para obtener una terminal a manera de objeto </p>
     * @param id es la llave primaria de cada terminal
     * @return nuevo objeto de tipo telefono 
    */    
    public Telefono get(int id) {
        String sql = "SELECT * FROM VENTAS.Bodega where id_bodega = ?";
        Object[] args = { id };
        Telefono nuevo = jdbcTemplate.queryForObject(sql, args, BeanPropertyRowMapper.newInstance(Telefono.class));
        return nuevo;
    }

     /**
     * <p>Método para actualizar las fotos de las terminales </p>
     * @deprecated
     * Este Método quedo desactualizado, se paso a manejar las imagenes con url 
     * @param foto son los bytes de la foto
     * @param consulta el string para generar la consulta para actualizar los dispositivos
     * @param id la llave primaria para ejectuar la consulta sobre un registro en particular
    */
    public void updateF(byte[] foto, String consulta, int id) {

        if (foto.length > 0) {
            jdbcTemplate.update(consulta, foto, id);

        }

    }

    /**
     * <p>Método para actualizar los parametros de las terminales </p>
     * @param nuevo objeto de tipo de telefono 
     * <p>Se utiliza el objeto completo para actualizar todos los campos</p>
     * <b>Es importante usar  '=:' para actualizar los campos y no dejar espacios</b>
    */  
    public void updateT(Telefono nuevo) {
        String sql = "UPDATE VENTAS.BODEGA SET existencia=:existencia , precio_lista=:precio_lista, codigo_modelo=:codigo_modelo, ram=:ram, almacenamiento=:almacenamiento, procesador=:procesador, numero_cores=:numero_cores,color=:color,descripcion=:descripcion,nombret=:nombret,origen=origen,foto1=:foto1,foto2=:foto2,foto3=:foto3 WHERE id_bodega=:id_bodega";
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(nuevo);

        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate);
        template.update(sql, param);        

    }

     /**
     * <p>Método para crear nuevas terminales</p>
     * @param nuevo objeto de tipo de telefono, que es la terminal a guardar  
     * @throws IOException
     * <p> Los insert a la base de datos son manejados por el jdbctemplate que ejecuta la consulta</p>
      */ 
    public void save(Telefono nuevo) throws IOException { 

        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);
        insertActor.withSchemaName("VENTAS").withTableName("BODEGA").usingColumns( "existencia",
                "precio_lista", "codigo_modelo", "ram", "almacenamiento", "procesador", "numero_cores", "color",
                "descripcion", "nombret","origen","id_estados","foto1","foto2","foto3");
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(nuevo);

        insertActor.execute(param);    
    }

    /**
     * <p>Método ara salvar los numeros de serie</p>
     * @param consulta  objeto de tipo de telefono, que es la terminal a guardar  
     * <p> Los insert a la base de datos son manejados por el jdbctemplate que ejecuta la consulta</p>
      */ 
      public void saveNum(String num,String modelo,int id) {
        String sql = "insert into ventas.numeros_serie(num_series,modelo,id_bodega) values(?,?,?) ";
        jdbcTemplate.update(sql, num,modelo,id);

 
       
   

        

    }

        /**
     * <p>Obtner el id de la bodega</p> 
     * <p> Los insert a la base de datos son manejados por el jdbctemplate que ejecuta la consulta</p>
      */
      public Telefono getlastID() {
        String sql = "select *from ventas.bodega order by id_bodega desc FETCH FIRST 1 ROWS ONLY ";
        Telefono registro = jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Telefono.class));
        return registro;
    } 
      







     /**
     * <p>Método para eliminar terminales</p>
     * @param id el identificador para eliminar especificamente una terminal   
     * <p>A través del jdbcTemplate ejecutamos un update que ejecuta el delete</p>     
    */ 
    public void delete(int id) {
        String sql = "DELETE FROM VENTAS.BODEGA WHERE id_bodega = ?";
        jdbcTemplate.update(sql, id);
    }

    /**
     * <p>Método para retornar una lista con opciones del tipo de telefono, usado principalemente en el combobox de marcas</p>
     * @param query es la consulta que generamos para traer las opciones  
     * @return listaOpciones una lista con las marcas de telefonos 
     * <p>Este método puede devolver una lista de cualquier cosa que este definida dentro de la clase telefono</p>
     */ 
    public List<Telefono> listOpciones(String query) {
        String sql = query;

        List<Telefono> listaOpciones = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Telefono.class));
        return listaOpciones;

    }


  


    /**
     * <p>Método para realizar una busqueda de terminales</p>
     * @param buscar la cadena de texto que recibe para hacer la busqueda   
     * @return listaBusqueda es una lista de tipo telefono que retorna todos las terminales que cumplan con el criterio de busqueda
     
    */ 
    public List<Telefono> listBus(String buscar) {
        String sql = "select *from VENTAS.bodega where nombret like ?   ";
        Object[] args = { buscar };
        List<Telefono> listaBusqueda = jdbcTemplate.query(sql, args, BeanPropertyRowMapper.newInstance(Telefono.class));
        return listaBusqueda;

    }

     /**
     * <p>Método para borrar terminal de la bodega</p>
     * @param id identificador de la terminal a eliminar
     
     
    */ 
    public void deleteT(int id) {
        String sql = "DELETE FROM VENTAS.bodega WHERE id_bodega = ?";
        jdbcTemplate.update(sql, id);
    }

    /**
     * <p>Método para mostrar dispositivos de bodega</p>
     * @return terminalList la lista de tipo telefono con todas las terminales de bodega
     
     
    */ 
    public List<Telefono> bodega() {
        String sql = "select *from ventas.bodega";
        List<Telefono>    terminalList = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Telefono.class));
        return   terminalList;

    }



    //TODO: Métodos para el manejo del cátalgo de clientes 

  /**
     * <p>Método para listar todos los clientes dentro de la base de datos, quitando el registro de cliente individual</p>
     * @return listCli lista del tipo Cliente. 
     
    */ 
    public List<Cliente> listaCliente() {
        String sql = "select * from ventas.clientes where id_cliente != '1' and id_cliente  != 2";
        List<Cliente> listCli = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Cliente.class));
        return listCli;
    }

     /**
     * <p>Método para retornar una lista con opciones del tipo de cliente, usado principalemente para traer los tipos de clientes</p>
     * @param query es la consulta que generamos para traer las opciones  
     * @return una lista de opcioens para el tipo de cliente
     * <p>Este método puede devolver una lista de cualquier cosa que este definida dentro de la clase cliente</p>
     
    */ 
    public List<Cliente> listOpcionesC(String query) {
        String sql = query;

        List<Cliente> listaOpcionesC = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Cliente.class));
        return listaOpcionesC;

    }

     /**
     * <p>Método salvar info de un cliente dentro de la bd</p>
     * @param ci objeto de tipo cliente, es el que se guarda dentro de la bd   
     
     
    */     
    public void saveCliente(Cliente ci) {
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);
        insertActor.withSchemaName("VENTAS").withTableName("CLIENTES").usingColumns("nombre", "apellido","nit", "correo",  "inicio_suscripcion", "vencimiento_suscripcion","id_tipo_cliente","patente_comercio");
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(ci);
        insertActor.execute(param);
    }


    /**
     * <p>Método para eliminar el cliente con el id</p>
     * @param id identificador del cliente a borrar 

     
    */    
    public void deletecliente(int id) {
        String sql = "DELETE FROM VENTAS.CLIENTES WHERE id_cliente = ?";
        jdbcTemplate.update(sql, id);
    }

    /**
     * <p>Método para actualizar la info del cliente dentro de la bd</p>
     * @param ci identificador del cliente a actualizar    
     
    */ 
    public void updateC(Cliente ci) {
        String sql = "UPDATE VENTAS.CLIENTES SET  nombre=:nombre, apellido=:apellido,nit=:nit,correo=:correo, inicio_suscripcion=:inicio_suscripcion, vencimiento_suscripcion=:vencimiento_suscripcion,patente_comercio=:patente_comercio ,id_tipo_cliente=:id_tipo_cliente WHERE id_cliente=:id_cliente";
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(ci);

        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate);
        template.update(sql, param);
    }

    /**
     * <p>Método para obtener el objeto de un cliente en particular</p>
     * @param id identificador del cliente a consultar 
     * @return ci el objeto cliente del respectivo id
     
    */ 
    public Cliente getC(int id) {
        String sql = "SELECT * FROM VENTAS.CLIENTES WHERE id_cliente = ?";
        Object[] args = { id };
        Cliente ci = jdbcTemplate.queryForObject(sql, args, BeanPropertyRowMapper.newInstance(Cliente.class));
        return ci;

    }

    /**
     * <p>Método para obtener una lista con la info del cliente individual/<p>
     * @param id identificador del cliente a consultar 
     *  @return una lista de tipo cliente con la info del cliente especifico     
     
    */ 
    public List<Cliente> listaCliente2(int id) {
        String sql = "select * from ventas.clientes where id_cliente = ?";
        Object[] args = { id };
        List<Cliente> listasC = jdbcTemplate.query(sql, args, BeanPropertyRowMapper.newInstance(Cliente.class));
        return listasC;

    }
    
    
    
    //TODO:Manejo de las marcas
    /**
     * @deprecated
     * <p>Las marcas pasamos a manejarlas con las fabricas</p>
     * @return una lista con las marcas
     
    */ 
    public List<Marca> listMarca() {
        String sql = "select * from VENTAS.marca_dispositivo";
        List<Marca> listMar = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Marca.class));
        return listMar;
    }

    /**
     * @deprecated
     * @param id unid especifoc de la marca
     * @return lista con las marcas
     * <p>Las marcas pasamos a manejarlas con las fabricas</p>
     
    */ 
    public List<Marca> listMar2(int id) {
        String sql = "select * from VENTAS.marca_dispositivo where id_marca = ?";
        Object[] args = { id };
        List<Marca> listMar = jdbcTemplate.query(sql, args, BeanPropertyRowMapper.newInstance(Marca.class));
        return listMar;

    }
    /**
     * @deprecated
     * <p>Las marcas pasamos a manejarlas con las fabricas</p>
    */ 
    public void saveMarca(Marca ma) {
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);
        insertActor.withSchemaName("VENTAS").withTableName("MARCA_DISPOSITIVO").usingColumns("nombre_marca");
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(ma);

        insertActor.execute(param);
    }
    /**
     * @deprecated
     * <p>Las marcas pasamos a manejarlas con las fabricas</p>
     
    */ 
    public void deleteMarca(int id) {
        String sql = "DELETE FROM VENTAS.marca_dispositivo WHERE id_marca = ?";
        jdbcTemplate.update(sql, id);
    }
    /**
     * @deprecated
     * <p>Las marcas pasamos a manejarlas con las fabricas</p>
     
    */ 
    public void updateMarca(Marca ma) {
        String sql = "UPDATE VENTAS.marca_dispositivo SET  nombre_marca=:nombre_marca WHERE id_marca=:id_marca";
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(ma);

        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate);

        template.update(sql, param);

    }


    //TODO: Métodos para el manejo de pedidos 

    /**
         * <p>Método para insertar un pedido realizado por algun cliente </p>
         * @param nuevo es el objeto de tipo Pedido con la info a guardar 
         * @throws IOException

         
    */ 
    public void insertarPedido(Pedido nuevo) throws IOException { 
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);
        insertActor.withSchemaName("VENTAS").withTableName("PEDIDO").usingColumns( "ID_CLIENTE","CANTIDADP", "CANTIDADP_A","ID_BODEGA","TOTAL_C");
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(nuevo);
        insertActor.execute(param);    
    }

    /**
         * @deprecated
         * ya no se uso para obtener el id de un cliente en especifico 
         * <p>Obtener info de un cliente en particular </p>
         *@param nit se pasa para obtener la info de un cliente en especifico 
         
    */ 
    public List<Cliente> idCliente(int nit) {
        String sql = "select id_cliente from ventas.clientes where nit = ?";
        Object[] args = { nit };
        List<Cliente> idC = jdbcTemplate.query(sql, args, BeanPropertyRowMapper.newInstance(Cliente.class));
        return idC;
    }


    /**
         * <p>Obtener info de un cliente  </p>
         *@param nit para obtener info de un cliente en particular 
         
    */ 
    public List<Cliente> clienteNit(int nit) {
        String sql = "select * from ventas.clientes where nit = ?";
        Object[] args = { nit };
        List<Cliente> idCn = jdbcTemplate.query(sql, args, BeanPropertyRowMapper.newInstance(Cliente.class));
        return idCn;

    }

     /**
         * <p>Confirmar las terminales pedidas por un cliente  </p>
         *@param id el identificador de la persona que realiza el pedido 
         @param cred si el pedido se realizara al credito 
         
    */    
    public void facturar(int id,int cred) {
        String sql = "UPDATE VENTAS.pedido SET credito = ? where id_cliente = ? and compra != 1  ";
        jdbcTemplate.update(sql,cred,id);

    }

    /**
         * <p>Mostrar las terminales añadidas a un pedido  </p>
         *@param id el identificador del cliente al que pertence el carro 
         
    */ 
    public List<Pedido> mostrarCarro(int id) {
        

        String sql = "select *from ventas.bodega inner join ventas.pedido using(id_bodega) where id_cliente = ? and compra != 1";
        Object[] args = { id };
        List<Pedido> listaC = jdbcTemplate.query(sql,args, BeanPropertyRowMapper.newInstance(Pedido.class));
        return listaC;

    }


    /**
         * <p>Mostrar la factura del pedido que se esta realizando   </p>
         *@param id identificador del cliente a mostrar factura 
         
    */ 
    public List<Pedido> mostrarFactura(int id) {
        

        String sql = "select (ventas.precio(id_tipo_cliente,id_bodega)*(cantidadp+cantidadp_a)) as precio_total, b.* from(  select  *from ventas.clientes inner join(    select *from ventas.bodega inner join ventas.pedido using(id_bodega) )a  using(id_cliente))b where id_cliente = ? and compra =0 ";
        Object[] args = { id };
        List<Pedido> listaF = jdbcTemplate.query(sql,args,  BeanPropertyRowMapper.newInstance(Pedido.class));
        return listaF;

    }
   
    /**
         * <p>Mostrar el total de la compra   </p>
         *@param id total de la compra de un cliente en especifico
         
    */ 
    public List<Pedido> total(int id) {
        

        String sql = "select sum(total) as totales  from(select (ventas.precio(id_tipo_cliente,id_bodega)*(cantidadp+cantidadp_a)) as total, b.* from(select  *from ventas.clientes inner join( select *from ventas.bodega inner join ventas.pedido using(id_bodega) )a  using(id_cliente))b)c where id_cliente = ? and compra = 0 ";
        Object[] args = { id };
        List<Pedido> listaT = jdbcTemplate.query(sql, args, BeanPropertyRowMapper.newInstance(Pedido.class));
        return listaT;

    }


    /**
         * <p>Mostrar el total de la compra una vez confirmada  </p>
         *@param id total de la compra de un cliente en especifico
         
    */ 
    public List<Pedido> total2(int id) {
        

        String sql = "select sum(total) as totales  from(select (ventas.precio(id_tipo_cliente,id_bodega)*(cantidadp+cantidadp_a)) as total, b.* from(select  *from ventas.clientes inner join( select *from ventas.bodega inner join ventas.pedido using(id_bodega) )a  using(id_cliente))b)c where id_cliente = ? and compra = 1 ";
        Object[] args = { id };
        List<Pedido> listaT = jdbcTemplate.query(sql, args, BeanPropertyRowMapper.newInstance(Pedido.class));
        return listaT;

    }
    
    /**
         * <p>Eliminar un pedido añadido al carro  </p>
         *@param id identificador del pedido a eliminar 
         
    */
    public void deletePedido(int id) {
        String sql = "DELETE FROM VENTAS.pedido where id_pedido = ?";
        jdbcTemplate.update(sql, id);
    }

    /**
         * <p>Confirmar las compras </p>
         *@param id identificador del cliente que realiza la compra 
         *Este metodo es importante porque es el que le indica al trigger que entre en funcionamiento 
         
    */
    public void comprarT(int id) {
        String sql = "update ventas.pedido set id_estado = 2 , compra = 1 where id_cliente = ? and compra = 0";
        jdbcTemplate.update(sql, id);

        String sql2 = "update ventas.pedido set descontado = 1 where id_cliente = ?";
        jdbcTemplate.update(sql2, id);

    }








   
    //TODO: Manejo de usuarios 
  
    /**
         * <p>Método para obtener info de un usuario </p>
         * @param usuario el usuario que ingresa 
         * @param contraseña la contraseña que ingresa 
         
    */ 
    public List<Usuarios> listUsuarios( String usuario,String contraseña) {
        

        String sql = "select *from VENTAS.USUARIO where usuario = ? and contraseña = ?";
        Object[] args = { usuario,contraseña };
        List<Usuarios> listaU = jdbcTemplate.query(sql, args, BeanPropertyRowMapper.newInstance(Usuarios.class));
        return listaU;

    }

    /**
         * <p>Método mostrar a todos los usuarios dentro de la base de datos </p>
         *@return listaU una lista de tipo Usuarios con todos los usuarios y su info
         
    */ 
    public List<Usuarios> usuariosMostrar() {
        

        String sql = "select *from VENTAS.usuario inner join VENTAS.tipo_usuario using(id_tipo_usuario)";
      
        List<Usuarios> listaU = jdbcTemplate.query(sql,  BeanPropertyRowMapper.newInstance(Usuarios.class));
        return listaU;

    }

     /**
        * @deprecated
         * <p>Método utilizado para listar usuarios</p>
         *@return una lista de tipo usuario 
         
    */ 
    public List<Usuarios> getUsuarioporNombre() {
        

        String sql = "select *from VENTAS.usuario inner join VENTAS.tipo_usuario using(id_tipo_usuario) where nombre = ?";      
        List<Usuarios> listaU = jdbcTemplate.query(sql,  BeanPropertyRowMapper.newInstance(Usuarios.class));
        return listaU;

    }

   

    /**
         * <p>Método para actualizar un usuario</p>
         * @param nuevo objeto tipo usuario que manda las actualizaciones
         
    */ 
    public void updateU(Usuarios nuevo) {
        String sql = "UPDATE VENTAS.usuario SET usuario=:usuario , id_tipo_usuario=:id_tipo_usuario WHERE id_usuario=:id_usuario";
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(nuevo);

        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate);
        template.update(sql, param);

        

    }

    /**
         * <p>Método para eliminar un usuario especifico</p>
         * @param id es el identificador para eliminar el usuario en especifico 
         
    */ 
    public void deleteUsuario(int id) {
        String sql = "DELETE FROM VENTAS.usuario WHERE id_usuario = ?";
        jdbcTemplate.update(sql, id);
    }

     /**
         * <p>Método para crear usuarios dentro del sistema de ventas</p>
         * @param  nuevo objeto de tipo usuario 
         *  @throws IOException
         
    */ 
    public void crearU(Usuarios nuevo) throws IOException {

  

        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);
        insertActor.withSchemaName("VENTAS").withTableName("USUARIO").usingColumns( "usuario","contraseña","id_tipo_usuario");
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(nuevo);
        insertActor.execute(param);
     }

 

  

 //TODO: Manejo del historial 

    /**
         * <p>Método para mostrar los cambios realizados</p>
         * @return una lista con los cambios de tipo Historial 
         
         * 
    */ 
    public List<Historial> cambios() {
        String sql = "select *from ventas.historial order by fecha_cambio desc";
        List<Historial> listaDis = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Historial.class));
        return listaDis;

    }

     /**
         * <p>Método para eliminiar el historial, solo queda guardado cuando fue la ultima vez que se elimino</p>
         * @throws IOException        
    */ 
    public void historialBorrar() throws IOException {
        String sql = "delete from ventas.historial where cambio != 'Elimino historial'";
        jdbcTemplate.update(sql);
    }

    /**
         * <p>Método insertar cambios al historial</p>
         * @param  usuario quien realizo el cambio
         *  @param cambio que cambio realizo 
         * @param tabla en que se realizo el cambio
         * @throws IOException
    */ 
    public void historialC(String usuario,String cambio, String tabla) throws IOException {
        String sql = "insert into ventas.historial(usuario,cambio,tabla,fecha_cambio) values(?,?,?, cast(current_date as timestamp) at time zone 'UTC')";
        jdbcTemplate.update(sql, usuario,cambio,tabla);  

      }

    /**
         * <p>Método para mostrar dispositivos de una vista</p>
         * @return una lista de tipo de telefono resultado de consultar una vista       
    */ 
    public List<Telefono> listTerminales() {
        String sql = "select *from VENTAS.VIEW2";
        List<Telefono> listaDis = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Telefono.class));
        return listaDis;

    }  

    /**
     * <p>Método para mostrar las compras individuales de cada cliente</p>
     * @param id identificador del cliente a consultar    
    */ 
    public List<Pedido> mostrarC(int id) {
        

        String sql = "select *from ventas.view1 where id_cliente = ? and compra =1 ";
        Object[] args = { id };
        List<Pedido> listaF = jdbcTemplate.query(sql,args,  BeanPropertyRowMapper.newInstance(Pedido.class));
        return listaF;

    }

    /**
     * <p>Retornar una lista con información de los pedidos</p>
     * @return listaF retorna una lista del tipo Pedido para más adelante gráficarlos
    
    */ 
    public List<Pedido> board() {        

        String sql = "select nombref, sum(cantidad) as cantidad from ventas.view3  where compra =1  group by nombref  ";        
        List<Pedido> listaF = jdbcTemplate.query(sql,  BeanPropertyRowMapper.newInstance(Pedido.class));
        return listaF;

    }

    //TODO: Métodos para el manejo de las fabricas 
    /**
     * <p>Método para mostrar la info de todas las fabircas dentro de la bd</p>
     * @return listaDis que es una lista con los datos de todas las fabricas
      
    */ 
    public List<Fabrica> listFabrica() {
        String sql = "select *from VENTAS.fabrica";
        List<Fabrica> listaDis = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Fabrica.class));
        return listaDis;

    }

    /**
     * <p>Método para guardar los datos de una fabrica</p>
     * @param nuevo objeto de tipo fabrica para guardar los datos de la fab en la bd
     * @throws IOException 
      
    */ 
    public void guardarFabrica(Fabrica nuevo) throws IOException {

  

        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);
        insertActor.withSchemaName("VENTAS").withTableName("FABRICA").usingColumns("IP","PUERTO","NOMBREF");
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(nuevo);

        insertActor.execute(param);



    }

    /**
     * <p>Método para obtener información de una  fabrica</p>
     * @param id el identificador de la fabrica para obtener sus datos 
     * @return listaIn que es una lista del tipo fabrica, tiene los datos de la fabrica en particular
     
    */ 
    public List<Fabrica> getFab(int id) {
        String sql = "select *from VENTAS.fabrica where id_fabrica = ?";
        Object[] args = { id };
        List<Fabrica> listaIn = jdbcTemplate.query(sql, args, BeanPropertyRowMapper.newInstance(Fabrica.class));
        return listaIn;

    }
     /**
     * <p>Método para obtener información de una  fabrica</p>
     * @param id el identificador de la fabrica para obtener sus datos 
     * @return listaIn que es una lista del tipo fabrica, tiene los datos de la fabrica en particular
     
    */ 
    public List<Fabrica> getFabdaots(int id) {
        String sql = "select *from ventas.fabrica inner join ventas.bodega on id_fabrica =origen where id_fabrica =?";
        Object[] args = { id };
        List<Fabrica> listaIn = jdbcTemplate.query(sql, args, BeanPropertyRowMapper.newInstance(Fabrica.class));
        return listaIn;

    }


    /**
     * <p>Método para eliminar el acceso a una fabrica</p>
     * @param id el identificador de la fabrica para eliminarlo
     
    */ 
    public void deletefab(int id) {
        String sql = "DELETE FROM VENTAS.fabrica WHERE id_fabrica = ?";
        jdbcTemplate.update(sql, id);
    }

    /**
     * <p>Método secundario para salvar telefonos que se reciben del rest</p>
     * @param nuevo que es el objeto de telefono a guardar en la db
     * @throws IOException
     
    */ 
    public void save2(Telefono nuevo) throws IOException {

  

        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);
        insertActor.withSchemaName("VENTAS").withTableName("BODEGA").usingColumns( "existencia",
                "precio_lista", "codigo_modelo", "ram", "almacenamiento", "procesador", "numero_cores", "color",
                "descripcion", "nombret","origen","id_estados");
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(nuevo);

        insertActor.execute(param);
        }


     /**
      * @deprecated
     * <p>Método para mostrar los pedidos realizados </p>
     
    */ 
    public List<Telefono> listPedidoF() {
        String sql = "select *from ventas.estadosf inner join(select *from VENTAS.bodega inner join VENTAS.fabrica on origen = id_fabrica where id_estados = 1)a on ID_ESTADOF = ID_ESTADOS";
        List<Telefono> listaDis = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Telefono.class));
        return listaDis;

    }

    /**
     * <p>Método para confirmar pedido, cambia el estado del pedido dentro de la bd para confirmarlo</p>
     * @param n que es un objeto del tipo telefono que es la terminal que se confirma para el pedido 
     
    */ 
    public void confirmarP(Telefono n) {
        String sql = "UPDATE VENTAS.bodega set id_estados=:id_estados where id_bodega=:id_bodega";
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(n);

        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate);

        template.update(sql, param);

    }

    /**
     * <p>Método para generar un listado con las ventas realizadas</p>
     * @return listaDis que es una lista con los telefonos vendidos
     
    */ 
    public List<EnviarCorreo> listaReporte(int origen) {
        String sql = "select nombret, cantidadp, cantidadp_a, total_c,origen  from ventas.fabrica inner join(select *from ventas.bodega inner join ventas.pedido using(id_bodega))a on origen = id_fabrica where id_estado = 2 and origen = ?";
        Object[] args = { origen };
        List<EnviarCorreo> listaDis = jdbcTemplate.query(sql,args, BeanPropertyRowMapper.newInstance(EnviarCorreo.class));
        return listaDis;

    }

    public void asignarNum(int dueño,int times) {
        String sql = "UPDATE VENTAS.numeros_serie set estadov=1,dueño=? where estadov= 0 and rownum <=  " + times+ "";
        jdbcTemplate.update(sql, dueño);
    }

    public void asignarNumPermanente(int dueño,int times) {
        String sql = "UPDATE VENTAS.numeros_serie set estadov=1,dueño=?  rownum <=  " + times+ "";
        jdbcTemplate.update(sql, dueño);
    }
    
    
    
   


  


















}