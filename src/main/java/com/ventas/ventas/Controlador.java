
package com.ventas.ventas;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.annotations.Any;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

//Rest
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * <p>El controlador, sirve para mostrar, redireccionar las paginas web, asi como mostrar los resultados de las paginas web</p>
 */
@Controller
public class Controlador {
    String session2; 
    int id_session2; 
    @Autowired 
    private Correo correo; 

    @Autowired(required = true)
    private Dao dao;
    /**
     * <p>
     * Método para ver los dispositivos del sistema de ventas
     * </p>
     * 
     * @param model   mostrar el html de los dispositivos
     * @param session sesion logeada
     * @return Se muestra la pagina html para mostrar todos los dispositivos que
     *         existen el sistema de ventas y mostrar las operaciones que puede
     *         realizar dependiento el tipo de usuario que ingrese.
     */
    @RequestMapping("/")
    public String viewHomePage(final Model model, HttpSession session) {
        final List<Telefono> listaDis = dao.list();
        model.addAttribute("listaDis", listaDis);
        //model.addAttribute("imgUtil", new ImageUtil());

        if (session.getAttribute("name") == null) {
            return "accesoProhibido.html";
        } else {
            return "index.html";

        }

    }

    /**
     * <p>
     * Método para editar el dispositivo individual
     * </p>
     * 
     * @param id      para llamar el id del dispositivo
     * @param session sesion logeada
     * @return Se muestra el model con los datos de cada dispositivo individual,
     *         para que se pueda realizar una modificación y se guarde los cambios
     *         que se realizaron.
     */
    @RequestMapping("/individual/{id}")
    public ModelAndView showEditForm(@PathVariable(name = "id") int id, HttpSession session) {
        ModelAndView mav = new ModelAndView("individual");
        Telefono nuevo = dao.get(id);
        mav.addObject("nuevo", nuevo);

        return mav;
    }

    /**
     * <p>
     * Método para mostrar el dispositivo individual
     * </p>
     * 
     * @param model para mostrar la pagina del html del dispositivo individual
     * @param id    para que mande a llamar el id que tiene el dispositivo
     * @return Se muestra el html del dispositivo individual que fue seleccionado
     *         para que muestre todas sus caracteristicas y también las imagenes que
     *         tiene ese dispositivo.
     */
    @RequestMapping(value = "/individual/{id}", method = RequestMethod.GET)
    public String view(final Model model, @PathVariable("id") int id) {
        final List<Telefono> listaDis = dao.list2(id);
        final List<Telefono> opciones = dao.listOpciones("Select *from DEV.fabrica");
        model.addAttribute("opciones", opciones);
        model.addAttribute("listaDis", listaDis);
        model.addAttribute("imgUtil", new ImageUtil());
        final List<Cliente> listCli = dao.listaCliente();
        model.addAttribute("listCli", listCli);

        return "individual.html";
    }

    /**
     * <p>
     * Método para actualizar la imagen de los dispositivos individuales
     * </p>
     * 
     * @param foto1   para mostrar la foto 1 del dispositivo individual
     * @param foto2   para mostrar la foto 2 del dispositivo individual
     * @param foto3   para mostrar la foto 3 del dispositivo individual
     * @param id      para que manda a llamar el id del dispositivo individual
     *                seleccionado
     * @param session para la sesion logeada
     * @return Se mostrara un model donde tendra los campos para que se pueda
     *         ingresar las imagenes de los dispositivos individuales
     */
    // Actualizar foto
    @RequestMapping(value = "/updateImg", method = RequestMethod.POST)
    public String update(@RequestParam(name = "foto1") MultipartFile foto1,
            @RequestParam(name = "foto2") MultipartFile foto2, @RequestParam(name = "foto3") MultipartFile foto3,
            @RequestParam(name = "id_bodega") int id, HttpSession session) throws IOException {

        byte[] fotoa = foto1.getBytes();
        byte[] fotob = foto2.getBytes();
        byte[] fotoc = foto3.getBytes();
        dao.updateF(fotoa, "UPDATE DEV.BODEGA SET foto1=? WHERE id_bodega=?", id);
        dao.updateF(fotob, "UPDATE DEV.BODEGA SET foto2=? WHERE id_bodega=?", id);
        dao.updateF(fotoc, "UPDATE DEV.BODEGA SET foto3=? WHERE id_bodega=?", id);

        String tabla = "Bodega";
        dao.historialC(session2, "Update", tabla);

        return "redirect:/";
    }

    /**
     * <p>
     * Método para para insertar un dispositivo
     * </p>
     * 
     * @param model para mostrar la pagina html de las caracteristicas del
     *              dispositivo
     * @return Se mostrara un html con los campos que se deberan ingresar para que
     *         se agrege un nuevo dispositivo en el sistema de ventas.
     */
    // insertar telefono
    @RequestMapping("/new")
    public String showNewForm(Model model) {
        Telefono nuevo = new Telefono();
        model.addAttribute("nuevo", nuevo);
        final List<Telefono> opciones = dao.listOpciones("Select *from DEV.fabrica");
        model.addAttribute("opciones", opciones);
        return "new";
    }

    /**
     * <p>
     * Método para guardar los datos de un dispositivo
     * </p>
     * 
     * @param nuevo para guardar los datos del dispositivo
     * @return Se mostrara en el la pagia html de dispositivos el nuevo dispositivo
     *         agregado.
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("nuevo") Telefono nuevo) throws IOException {
        dao.save(nuevo);

        String tabla = "Bodega";
        dao.historialC(session2, "Insertar", tabla);

        return "redirect:/";
    }

    /**
     * <p>
     * Método para eliminar un dispositivo
     * </p>
     * 
     * @param id para llamar el id del dispositivo seleccionado
     * @return Se mostrara en el la pagia html de dispositivos sin el dispositivo
     *         que fue eliminado.
     */
    // delete telefono
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") int id) throws IOException {
        dao.delete(id);
        String tabla = "Bodega";
        dao.historialC(session2, "Delete", tabla);
        return "redirect:/";
    }

    /**
     * <p>
     * Método para actualizar un dispositivo
     * </p>
     * 
     * @param nuevo para guardar los datos del dispositivo actualizados
     * @return Se mostrara en el la pagia html de dispositivos con los cambios del
     *         dispositivo.
     */
    // actualizar telefono
    @RequestMapping(value = "/updatePh", method = RequestMethod.POST)
    public String updatePh(@ModelAttribute("nuevo") Telefono nuevo) throws IOException {
        dao.updateT(nuevo);

        String tabla = "Bodega";
        dao.historialC(session2, "Update", tabla);
        return "redirect:/";

    }

    /**
     * <p>
     * Método para buscar un dispositivo
     * </p>
     * 
     * @param model  para mostrar la pagina html del dispositivo buscado
     * @param buscar para guardar los datos del dispositivo actualizados
     * @return Se mostrara en el la pagia html donde se podra buscar el dispositivo
     *         que se necesite ingresando el parametro a buscar para que se muestre
     *         en la pagina.
     * 
     */
    // Busqueda telefono

    @RequestMapping(value = "/busqueda", method = RequestMethod.GET)
    public String viewBusqueda(final Model model, @RequestParam(value = "buscar", required = false) String buscar) {
        buscar = "%" + buscar + "%";
        final List<Telefono> listaDis = dao.listBus(buscar);
        model.addAttribute("listaDis", listaDis);
        model.addAttribute("imgUtil", new ImageUtil());

        return "busqueda.html";
    }

    // clientes
    /**
     * <p>
     * Método para mostrar clientes
     * </p>
     * 
     * @param model para mostrar la pagina html de los clientes
     * @return Se mostrara en el la pagia html todos los clientes que existen en
     *         nuestro sistema de ventas.
     * 
     */
    // read
    @RequestMapping(value = "/clientes")
    public String viewcli(final Model model) {
        final List<Cliente> listCli = dao.listaCliente();
        model.addAttribute("listCli", listCli);
        return "clientes.html";
    }

    /**
     * <p>
     * Método para insertar un cliente
     * </p>
     * 
     * @param model para mostrar la pagina html ingresar un cliente
     * @return Se mostrara en el la pagia html con los campos que se deben de llenar
     *         para ingresar un cliente en el sitema de ventas.
     * 
     */
    // insert
    @RequestMapping("/cliente")
    public String viewCliente(Model model) throws IOException {
        Cliente ci = new Cliente();
        model.addAttribute("ci", ci);
        final List<Cliente> opciones = dao.listOpcionesC("Select *from DEV.tipo_clientes");
        model.addAttribute("opciones", opciones);

        return "cliente.html";
    }

    /**
     * <p>
     * Método para guardar un cliente
     * </p>
     * 
     * @param ci para guardar los datos de un cliente
     * @return Se mostrara en el la pagia html con el cliente que fue ingresado en
     *         el sistema.
     * 
     */
    @RequestMapping(value = "/saveCliente", method = RequestMethod.POST)
    public String saveCliente(@ModelAttribute("ci") Cliente ci) throws IOException {
        dao.saveCliente(ci);

        String tabla = "Clientes";
        dao.historialC(session2, "Insertar", tabla);

        return "redirect:/clientes";
    }

    /**
     * <p>
     * Método para eliminar un cliente
     * </p>
     * 
     * @param id para llamar el id del cliente que fue seleccionado
     * @return Se mostrara en el la pagia html sin el cliente que fue seleccionado
     *         para eliminar del sistema.
     * 
     */
    // delete
    @RequestMapping("/deletecliente/{id}")
    public String deletecliente(@PathVariable(name = "id") int id) throws IOException {
        dao.deletecliente(id);

        String tabla = "Clientes";
        dao.historialC(session2, "Delete", tabla);
        return "redirect:/clientes";
    }

    /**
     * <p>
     * Método para actualizar un cliente
     * </p>
     * 
     * @param id para llamar el id del cliente que fue seleccionado
     * @return Se redireccionar a la pagina html con la informacion del cliente que
     *         fue seleccionado.
     * 
     */
    // update
    @RequestMapping("/updateC/{id}")
    public ModelAndView updateC(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("updateC");
        Cliente ci = dao.getC(id);
        mav.addObject("ci", ci);

        return mav;
    }

    /**
     * <p>
     * Método para actualizar datos de un cliente
     * </p>
     * 
     * @param model para mostrar la pagina html de actualización de un cliente
     * @param id    para llamar el id del cliente que fue seleccionado
     * @return Se mostrara en el la pagia html con los datos del cliente que se
     *         selecciono para que sea modificado.
     * 
     */
    @RequestMapping(value = "/updateC/{id}", method = RequestMethod.GET)
    public String viewC2(final Model model, @PathVariable("id") int id) {
        final List<Cliente> listCli = dao.listaCliente2(id);
        model.addAttribute("listCli", listCli);
        return "updateC.html";
    }

    /**
     * <p>
     * Método para guardar los datos actualizados de un cliente
     * </p>
     * 
     * @param ci para guardar los datos del cliente
     * @return Se guardaran los cambios del cliente y se mostrara por último los
     *         datos cambiados en la pagina html.
     */
    @RequestMapping(value = "/updateC", method = RequestMethod.POST)
    public String updateC(@ModelAttribute("clienteN") Cliente ci) throws IOException {
        dao.updateC(ci);
        String tabla = "Clientes";
        dao.historialC(session2, "Update", tabla);

        return "redirect:/clientes";
    }

    // Marca
    // Marca
    /**
     * <p>
     * Método para mostrar las marcas de los dispositivos
     * </p>
     * 
     * @param model para mostrar la pagina html de marcas de dispositivos
     * @return Se mostrara en el la pagia html todas las marcas de los dispositivos
     *         que tiene el sistema de ventas.
     */
    // read
    @RequestMapping(value = "/marcas")
    public String viewMar(final Model model) {
        final List<Marca> listMar = dao.listMarca();
        model.addAttribute("listMar", listMar);
        return "marcas.html";
    }

    /**
     * <p>
     * Método para insertar una marca de dispositivo
     * </p>
     * 
     * @param model para mostrar la pagina html de marca
     * @return Se mostrara en el la pagia html con todos los campos para insertar
     *         una nueva marca de dispositivos.
     */
    // insert
    @RequestMapping("/addMarca")
    public String viewMarca(Model model) {
        Marca ma = new Marca();
        model.addAttribute("ma", ma);
        return "addMarca";
    }

    /**
     * <p>
     * Método para guardar la marca del dispositivo
     * </p>
     * 
     * @param ma para guardar los datos de la marca del dispositivo
     * @return Se mostrara en el la pagia html la marca insertada del dispositivo
     *         con todos sus datos.
     */
    @RequestMapping(value = "/saveMarca", method = RequestMethod.POST)
    public String saveMarca(@ModelAttribute("ma") Marca ma) throws IOException {
        dao.saveMarca(ma);
        String tabla = "Marcas";
        dao.historialC(session2, "Insert", tabla);

        return "redirect:/marcas";
    }

    /**
     * <p>
     * Método para eliminar la marca del dispositivo
     * </p>
     * 
     * @param id para llamar el id de la marca del dispositivo seleccionada
     * @return Se mostrara en el la pagia html sin la marca de dispositivo que fue
     *         seleccionado para eliminar del sistema.
     */
    // delete
    @RequestMapping("/deleteMarca/{id}")
    public String deleteMarca(@PathVariable(name = "id") int id) throws IOException {
        dao.deleteMarca(id);
        String tabla = "Marcas";
        dao.historialC(session2, "Delete", tabla);
        return "redirect:/marcas";
    }

    /**
     * <p>
     * Método para actualizar una marca de dispositivo
     * </p>
     * 
     * @param modelo para mostrar la pagina html deactualizar de la marca del
     *               dispositivo
     * @param id     para llamar el id de la marca del dispositivo que fue
     *               seleccionado
     * @return Se llama a la pagina html con la informacion de la marca que fue
     *         seleccionada para modificar los campos.
     * 
     */
    // update
    @RequestMapping(value = "/updateMarca/{id}", method = RequestMethod.GET)
    public String viewM(final Model model, @PathVariable("id") int id) {
        final List<Marca> listMar = dao.listMar2(id);
        model.addAttribute("listMar", listMar);
        return "updateMarca.html";
    }

    /**
     * <p>
     * Método para guardar actualizacion de una marca de dispositivo
     * </p>
     * 
     * @param ma para guardar los datos modificados de la marca de dispositivos
     * @return Se llama a la pagina html de marca de dispostivo con los datos ya
     *         actualizados.
     * 
     */
    @RequestMapping(value = "/updateMarca", method = RequestMethod.POST)
    public String updateMarca(@ModelAttribute("ma") Marca ma) throws IOException {
        dao.updateMarca(ma);
        String tabla = "Marcas";
        dao.historialC(session2, "Update", tabla);

        return "redirect:/marcas";
    }

    /**
     * <p>
     * Método para atender a un cliente
     * </p>
     * 
     * @param nit     para traer el cliente a atender
     * @param session para llamar al usuario logeado
     * @return Se llama a la pagina html para traer el nombre del cliente que sera
     *         atendido y para ver que tipo de cliente es.
     */
    // Pedido
    @RequestMapping(value = "/atenderCliente", method = RequestMethod.POST)
    public String log(@RequestParam(name = "nit") int nit, HttpSession session) throws IOException {
        final List<Cliente> listaDis = dao.clienteNit(nit);

        String usuario = "";
        int tipo_u = 0;
        int id_cliente = 0;
        for (Cliente cliente : listaDis) {

            usuario += cliente.getNombre();
            tipo_u += cliente.getId_tipo_cliente();
            id_cliente += cliente.getId_cliente();

        }

        if (listaDis.size() > 0) {
            session.setAttribute("nit", nit);
            session.setAttribute("cliente", usuario);
            session.setAttribute("idT", tipo_u);
            id_session2 = id_cliente;
            session.setAttribute("idc", id_cliente);

            return "redirect:/";

        } else {
            return "redirect:/";
        }

    }

    /**
     * <p>
     * Método para hacer un pedido
     * </p>
     * 
     * @param ma para insertar los pedidos
     * @return Se llama a la pagina html para insertar un pedido de los dispositivos
     *         que desea el cliente.
     */
    @RequestMapping(value = "/pedido", method = RequestMethod.POST)
    public String añadirPediod(@ModelAttribute("pedidon") Pedido ma, @RequestParam("id_cliente") int id, @RequestParam("cantidadp") int times) throws IOException {

        dao.insertarPedido(ma);
        String tabla = "Pedido";
        dao.asignarNum(id,times);
        dao.historialC(session2, "Insert", tabla);

        return "redirect:/";
    }

    /**
     * <p>
     * Método para mostrar los pedidos
     * </p>
     * 
     * @param model para mostrar los pedidos del cliente
     * @return Se llama a la pagina html para que muestre todos los pedidos de los
     *         dispositivos que hizo un cliente.
     */
    @RequestMapping("/carro")
    public String carro(final Model model) {
        final List<Pedido> listC = dao.mostrarCarro(id_session2);
        model.addAttribute("listC", listC);

        return "carro.html";
    }

    /**
     * <p>
     * Método para efectuar pago de pedidos al credito
     * </p>
     * 
     * @param model para mostrar si es pedido al credito
     * @param cerd  para seleccionar pedido al cedito
     * @return Se llama a la pagina html para poder seleccionar la facturación del
     *         pedido es al credito.
     */
    @RequestMapping(value = "/facturar", method = RequestMethod.POST)
    public String factura(final Model model, @RequestParam("credito") int cred) {

        dao.facturar(id_session2, cred);

        return "redirect:/factura";
    }

    /**
     * <p>
     * Método para facturacion de pedido
     * </p>
     * 
     * @param model para mostrar la factura de los dispositivos comprados
     * @return Se llama a la pagina html para mostrar los datos para la facturacion
     *         de la compra de los pedidos de un cliente.
     */
    @RequestMapping("/factura")
    public String factura(final Model model) {

        final List<Pedido> listaF = dao.mostrarFactura(id_session2);
        model.addAttribute("listaF", listaF);
        final List<Pedido> listaT = dao.total(id_session2);
        model.addAttribute("listaT", listaT);

        return "factura.html";
    }

    /**
     * <p>
     * Método para eliminar un pedido del cliente
     * </p>
     * 
     * @param id para llamar el id del pedido del cliente
     * @return Se llama el id del pedido del cliente para que se elimine del carrito
     *         de compras.
     */
    @RequestMapping(value = "/eliminarPedido", method = RequestMethod.POST)
    public String deleteP(@RequestParam(name = "id_pedido") int id) {

        dao.deletePedido(id);
        ;
        return "redirect:/carro";
    }

    /**
     * <p>
     * Método para verificar tipo de cliente y descuento
     * </p>
     * 
     * @param id para llamar el id del cliente que esta comprando
     * @return Se llama el id del cliente que esta comprando un dispositivo para
     *         poder generar un descuento dependiendo que tipo de cliente es el que
     *         esta comprando.
     */
    @RequestMapping(value = "/comprarT", method = RequestMethod.POST)
    public String comprarT(@RequestParam(name = "id_cliente") int id) {

        dao.comprarT(id);
  
        return "redirect:/carro";
    }

    // MANEJO DE USUARIOS
    // Login de usuarios
    /**
     * <p>
     * Método para logearse en el sistema de ventas
     * </p>
     * 
     * @param model   para mostrar la pagina del html
     * @param session sesion para logearse
     * @return Se muestra el html de iniciar sesion para que el usuario se logee por
     *         medio de nombre y contraseña, se revisa si el usuario existe dejará
     *         ingresar y mostrara los dispositivos y en caso de que el usuario no
     *         existe no se podra logear y aparecera que el acceso es denegado.
     */
    @RequestMapping("/login")
    public String verLogin(final Model model, HttpSession session) {
        session.invalidate();
        return "login.html";
    }

    /**
     * <p>
     * Método para verificación de usuarios
     * </p>
     * 
     * @param usuarion   usuario que se ingresa
     * @param contraseña contraseña que se ingresa
     * @param session    sesion para logearse
     * @return Se manda el parametro de usuario y contraseña para que el sistema
     *         peuda validar si existe el usuario y que tipo de usuario es el que
     *         ingresara al sistema. Si no se encuentra el usuario dara una alerta
     *         de accesos denegado.
     */
    @RequestMapping(value = "/log", method = RequestMethod.POST)
    public String log(@RequestParam(name = "usuario") String usuarion,
            @RequestParam(name = "contraseña") String contraseña, HttpSession session) throws IOException {
        final List<Usuarios> listaDis = dao.listUsuarios(usuarion, contraseña);

        int tipo = 0;
        for (Usuarios usuarios : listaDis) {

            tipo += usuarios.getId_tipo_usuario();
        }

        if (listaDis.size() > 0) {
            session.setAttribute("name", usuarion);
            session2 = usuarion;
            session.setAttribute("tipo", tipo);

            return "redirect:/";

        } else {
            return "redirect:/login?error=true";
        }

    }

    /**
     * <p>
     * Método para verificación de usuarios
     * </p>
     * 
     * @param usuarion   usuario que se ingresa
     * @param contraseña contraseña que se ingresa
     * @param session    sesion para logearse
     * @return Se manda el parametro de usuario y contraseña para que el sistema
     *         peuda validar si existe el usuario y que tipo de usuario es el que
     *         ingresara al sistema. Si no se encuentra el usuario dara una alerta
     *         de accesos denegado.
     */
    // Error
    /**
     * <p>
     * Método para mostrar error en el login
     * </p>
     * 
     * @param model para mostrar el html
     * @param error para mostrar error en el html
     * @return Se retornara en la pagina html del login si existe un error a la hora
     *         de ingresar al sistema de ventas.
     */
    @RequestMapping("/login?{error}")
    public String verLoginE(final Model model, @PathVariable("error") String error) {

        return "login.html";
    }

    /**
     * <p>
     * Método para restingir el acceso a las paginas html
     * 
     * @param model para mostrar el html
     * @return Se retornara en la pagina html diciendo que tiene acceso prohibido a
     *         esa pagina ya que no es un usuario permitido.
     */
    @RequestMapping("/accesoProhibido")
    public String accesoP(final Model model) {

        return "accesoProhibido.html";
    }

    // Pagina usuarios
    /**
     * <p>
     * Método para mostrar los usuarios
     * 
     * @param model para mostrar el html de usuarios
     * @return Se retornara en la pagina html todos los usuarios que existen en el
     *         sistema de ventas.
     */
    @RequestMapping(value = "/usuarios")
    public String usuariosPag(final Model model) {
        final List<Usuarios> listU = dao.usuariosMostrar();
        model.addAttribute("listU", listU);
        return "usuarios.html";
    }

    /**
     * <p>
     * Método para actualizar un usuario
     * 
     * @param nuevo para mandar los usuarios actualizados
     * @return Se mandaran los datos del usuario que se estan actualizando en el
     *         sistema.
     */
    @RequestMapping(value = "/updateUsuario", method = RequestMethod.POST)
    public String actualizarU(@ModelAttribute("user") Usuarios nuevo) throws IOException {

        dao.updateU(nuevo);
        String tabla = "Usuarios";
        dao.historialC(session2, "Update", tabla);
        return "redirect:/usuarios";
    }

    /**
     * <p>
     * Método para eliminar un usuario
     * 
     * @param id para llamar el id del usuario que se selecciono
     * @return Se mandaran la operacion de eliminacion del usuario que fue
     *         seleccionado y se redireccionara a la pagina de usuarios ya con el
     *         usuario eliminado.
     */
    @RequestMapping(value = "/eliminarUsuario", method = RequestMethod.POST)
    public String deleteU(@RequestParam(name = "id_usuario") int id) throws IOException {

        dao.deleteUsuario(id);
        String tabla = "Usuarios";
        dao.historialC(session2, "Delete", tabla);
        return "redirect:/usuarios";
    }

    /**
     * <p>
     * Método para salir del sistema
     * 
     * @param model   para mostrar el html
     * @param session para llamar a la sesion logeada
     * @return Se mandaran la pagina html del login cuando el usuario se deslogee
     *         del sistema de ventas.
     */
    @RequestMapping("/logoff")
    public String logoff(final Model model, HttpSession session) {
        session.invalidate();
        return "login.html";
    }

    // Crear usuario
    /**
     * <p>
     * Método para insertar un usuario
     * 
     * @param nuevo para insertar un usuario
     * @return Se mandaran la pagina html donde se podra ingresar un nuevo ussuario
     *         al sistema de ventas para que tenga sus respectivos accesos.
     */
    @RequestMapping(value = "/crearUsuario", method = RequestMethod.POST)
    public String createU(@ModelAttribute("userN") Usuarios nuevo) throws IOException {

        Base64.getEncoder().encodeToString(nuevo.getContraseña().getBytes("utf-8"));
        dao.crearU(nuevo);
        String tabla = "Usuarios";
        dao.historialC(session2, "Insert", tabla);
        return "redirect:/usuarios";
    }

    // Mostrar historial
    /**
     * <p>
     * Método para ver historial de log
     * 
     * @param model para mostrar el html
     * @return Se mandaran la pagina html del historial de todas las operaciones que
     *         se han realizado en el sistema de ventas.
     */
    @RequestMapping(value = "/cambios")
    public String cambiosP(final Model model) {
        final List<Historial> listC = dao.cambios();
        model.addAttribute("listC", listC);
        return "cambios.html";
    }

    // Borrar historial
    /**
     * <p>
     * Método para eliminar historial de log
     * </p>
     * 
     * @return Se eliminara todo el historial de las operaciones que se realizaron
     *         en el sistema de ventas.
     */
    @RequestMapping("/eliminarH")
    public String eliminarhistorial() throws IOException {
        dao.historialBorrar();
        dao.historialC(session2, "Elimino historial", "Historial");
        return "redirect:/cambios";
    }

    // Dispositivos individuales
    /**
     * <p>
     * Método para mostrar los dispositvos individuales
     * </p>
     * 
     * 
     * @param model para mostrar el html
     * @return Se mostrara la pagina html con los dato de los dispositivos
     *         individuales con su numero de serie.
     */
    @RequestMapping(value = "/terminales")
    public String terminales(final Model model) {
        final List<Telefono> listT = dao.listTerminales();
        model.addAttribute("listT", listT);
        return "terminales.html";
    }

    // Bodega
    /**
     * <p>
     * Método para mostrar los dispositvos en bodega
     * </p>
     * 
     * 
     * @param model para mostrar el html
     * @return Se mostrara la pagina html con los dispositivos en existencia en
     *         bodegas en el sistema de ventas.
     */
    @RequestMapping(value = "/bodega")
    public String bodega(final Model model) {
        final List<Telefono> listT = dao.bodega();
        model.addAttribute("listT", listT);
        return "bodega.html";
    }

    /**
     * <p>
     * Método para eliminar los dispositvos en bodega
     * </p>
     * 
     * @param id para llamar el id de los dispositivos que se selecciono
     * @return Se eliminara de bodegas los dispositivo que ya no estara en el
     *         sistema de ventas.
     */
    @RequestMapping("/eliminarT")
    public String eliminarB(@RequestParam(name = "id_bodega") int id) throws IOException {
        dao.deleteT(id);
        dao.historialC(session2, "Delete", "Bodega");
        return "redirect:/bodega";
    }

    // Mostrar compras individuales
    /**
     * <p>
     * Método para compras individuales
     * </p>
     * 
     * @param model para mostrar el html
     * @param id    para llamar el id de los clientes individuales
     * @return Se tendra en la pagina html de compras los dispositivos de un cliente
     *         individual que fueron comprados.
     */
    @RequestMapping(value = "/compras/{id}", method = RequestMethod.GET)
    public String viewCompra(final Model model, @PathVariable("id") int id) {
        final List<Pedido> listC = dao.mostrarC(id);
        model.addAttribute("listC", listC);
        final List<Pedido> listaT = dao.total2(id);
        model.addAttribute("listaT", listaT);
        return "compras.html";
    }

    /**
     * <p>
     * Método para enviar correo de compras al credito
     * </p>
     * 
     * @param model   para mostrar el html
     * @param id      para llamar de clientes
     * @param correoE para mandar los datos de compra al credito
     * @return Se tendra una pagina web donde se enviara un correo a los clientes
     *         que compraron dispositivos al credito para recordarles de su pago.
     */
    @RequestMapping(value = "/reporte/{id}/{correo}", method = RequestMethod.GET)
    public String reporte(final Model model, @PathVariable("id") int id, @PathVariable("correo") String correoE) {
        final List<Pedido> listC = dao.mostrarC(id);
        model.addAttribute("listC", listC);
        final List<Pedido> listaT = dao.total2(id);
        model.addAttribute("listaT", listaT);

        String mensaje = "Los dispositvos comprados este mes fueron: ";
        String mensajeT = " '\n'El total de compras al credito es de: ";

        for (Pedido pedido : listaT) {
            mensajeT += pedido.getTotales();
        }
        for (Pedido pedido : listC) {
            mensaje += pedido.getNombret() + ',' + pedido.getCantidadp() + pedido.getCantidadp_a() + ',';
        }

        correo.sendMail("marcellinos777@gmail.com", correoE, "Detalle", mensaje + mensajeT);
        return "compras.html";
    }

    // Mostrar el board
    /**
     * <p>
     * Método para mostrar las marcas vendidas
     * </p>
     * 
     * @param model para mostrar el html
     * @return Se tendra una pagina web donde se mostraran unas graficas con las
     *         marcas que fueron vendidas y cuantos dispositivos fueron vendidos en
     *         el sistema de ventas.
     */
    @RequestMapping(value = "/board")
    public String board(final Model model) {
        final List<Pedido> listT = dao.board();
        model.addAttribute("listT", listT);

        List<String> marcas = new ArrayList<String>();
        List<Integer> cantidad = new ArrayList<Integer>();

        for (Pedido cuenta : listT) {
            String nmarca = cuenta.getNombref();
            marcas.add(nmarca);
            cantidad.add(cuenta.getCantidad());
        }
        cantidad.add(0);
        model.addAttribute("marcas", marcas);
        model.addAttribute("cantidad", cantidad);

        return "board.html";
    }

    // REST
    /**
     * <p>
     * Método para mostrar los dispositvos de fabrica
     * </p>
     * 
     * @param model para mostrar el html
     * @return Se tendra una pagina web donde se mostraran los dispositivos que
     *         venden la fabrica de alguna marca en especifico.
     */
    @RequestMapping(value = "/getdispo", method = RequestMethod.GET)
    public String getDispo(final Model model) {
        String uri = "http://localhost:5000/api/dispositivos";
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Dispositivo[]> response = restTemplate.getForEntity(uri, Dispositivo[].class);
        Dispositivo[] employees = response.getBody();
        model.addAttribute("listaFD", employees);
        Telefono nuevo = new Telefono();
        model.addAttribute("nuevo", nuevo);

        return "AAFabricaDispo.html";
    }

    // Ir a fabrica
    /**
     * <p>
     * Método para insertar fabrica
     * </p>
     * 
     * @param model para mostrar el html
     * @return Se tendra una pagina web para ingresar una nueva fabrica donde el
     *         sistema de ventas podra realizar compras.
     */
    @RequestMapping(value = "/fabricas")
    public String fabricas(final Model model) {
        Fabrica nuevo = new Fabrica();
        model.addAttribute("nuevo", nuevo);
        final List<Fabrica> listaDis = dao.listFabrica();
        model.addAttribute("listaF", listaDis);

        return "Fabrica.html";
    }

    // Guardar fabrica
    /**
     * <p>
     * Método para guardar fabrica
     * </p>
     * 
     * @param nuevo para guardar los datos de la fabrica
     * @param model para mostrar el html
     * @return Se tendra una pagina web donde se guardaran las fabricas con las
     *         cuales el sistema de ventas puede interactuar.
     */
    @RequestMapping(value = "/guardarFabrica", method = RequestMethod.POST)
    public String guardarFabri(final Model model, @ModelAttribute("nuevo") Fabrica nuevo) throws IOException {
        dao.guardarFabrica(nuevo);
        Marca ma = new Marca();

        ma.setNombre_marca(nuevo.getNombref());
        dao.saveMarca(ma);

        return "redirect:/fabricas";
    }

    String ip = "";
    String puerto = "";
    String url = "";

    // Conectar con fabrica
    /**
     * <p>
     * Método para conectar con una fabrica
     * </p>
     * 
     * @param model para mostrar el html
     * @param id    para llamar el id de la fabrica
     * @return Se tendra una pagina web donde se conectara las fabricas para poder
     *         realizar un pedido.
     */
    @RequestMapping(value = "/irFabrica/{id}", method = RequestMethod.GET)
    public String irFabrica(final Model model, @PathVariable("id") int id) {

        final List<Fabrica> listaF = dao.getFab(id);

        for (Fabrica fabrica : listaF) {
            ip = fabrica.getIp();
            puerto = fabrica.getPuerto();

        }

        final List<Fabrica> listaF2 = dao.getFab(id);
        int idmarca = 0;
        for (Fabrica fabrica : listaF2) {
            idmarca += fabrica.getId_fabrica();
        }
        model.addAttribute("id_marca", idmarca);

        // Para mostrar los dispositivos
        String uri = "http://" + ip + ":" + puerto + "/api/dispositivos";
        url = uri;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Telefono[]> response = restTemplate.getForEntity(uri, Telefono[].class);
        Telefono[] employees = response.getBody();
        model.addAttribute("listaFD", employees);
        // Para mostrar los clientes
        String uri2 = "http://" + ip + ":" + puerto + "/api/cliente";
        RestTemplate restTemplate2 = new RestTemplate();
        ResponseEntity<Cliente[]> response2 = restTemplate2.getForEntity(uri2, Cliente[].class);
        Cliente[] clientes = response2.getBody();
        model.addAttribute("listaC", clientes);

        Telefono nuevo = new Telefono();
        model.addAttribute("nuevo", nuevo);

        return "AAFabricaDispo.html";
    }


    /**
     * <p>
     * Método para eliminar una fabrica
     * </p>
     * 
     * @param id para llamar el id de la fabrica
     * @return se enviara el parametro del id de la fabrica la cual fue seleccionada
     *         para eliminarla y redireccionara a la pagina de las fabricas sin la
     *         que fue eliminada.
     */
    @RequestMapping(value = "/deletefab", method = RequestMethod.POST)
    public String deletefab(@RequestParam(name = "id_fabrica") int id) throws IOException {
        dao.deletefab(id);

        String tabla = "Fabrica";
        dao.historialC(session2, "Delete", tabla);
        return "redirect:/fabricas";
    }

    

    
      
    //TODO:Cambio   


       /**
         * <p>Este controlador es para hacer un pedidos al sistema de fabrica</p>
         * @param nuevo el telefono a pedir
         * @param idu   el id del usuario que lo pidio
         * @param idd   el id de la terminal a pedir
         * @return la pagina a redireccionar 
      
        */    
        @RequestMapping(value = "/savePD", method = RequestMethod.POST)
        public String savep(@ModelAttribute("nuevo") Telefono nuevo,@RequestParam(name="idu") String idu,@RequestParam(name="_idd") String idd)
                throws IOException {
            
            //dao.save(nuevo);

            String uri = "http://" + ip +":"+ puerto+ "/api/pedidos/gios";
		    RestTemplate restTemplate = new RestTemplate();

		    Map<String, String> map = new HashMap<>();
            Date fecha = new Date(); 
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String f = sdf.format(new Date());

            map.put("cantidad", String.valueOf(nuevo.getExistencia()));          
            map.put("fecha_p", f); 
            map.put("dispositivo",idd);
            map.put("cliente",idu);              
           
			  
		    ResponseEntity<Void> response  = restTemplate.postForEntity(uri, map, Void.class);
		
		if (response.getStatusCode() == HttpStatus.OK) {
			System.out.println("Request Successful");
		} else {
			System.out.println("Request Failed");
		}
		return "redirect:/";
            
    
    
        }

     



        /**
         * <p>Este controlador es para confirmar el pediod a fabrica</p>
         * @param nuevo el telefono a confirmar 
         * @param idu   el id del usuario que lo confirmo 
         * @param idd   el id de la terminal a confirmar 
         * @param idp   el id del pedido a confirmar 
         * @return la pagina a redireccionar 
      
        */ 
        @RequestMapping(value = "/savePD2")
        public String save2(@ModelAttribute("nuevo") Telefono nuevo,@RequestParam(name="idu") String idu,@RequestParam(name="_idd") String idd,@RequestParam(name="_idp") String idp,@RequestParam(name="listanum") List<String> num)
                throws IOException {
            
           
                dao.save(nuevo);
                    for (String string : num) {
                        String[] parts = string.split("/");
                        if(!string.equals("1")){
                            
                            dao.saveNum(string,parts[0],dao.getlastID().getId_bodega());
                            
                        }
                        
                    }
  

        

            String uri = "http://" + ip +":"+ puerto+ "/api/pedidos/" + idp;
		    RestTemplate restTemplate = new RestTemplate();
		    Map<String, String> map = new HashMap<>();       
            map.put("estado","Entregado");        
  
           	
		  
           Telefono wry = new Telefono();
           wry.setEstado("Entregado");
            
		  

		   restTemplate.put(uri, map);  

           
		
		return "redirect:/";
            
    
    
        }

    

         /**
         * <p>Este controlador es para visualizar los pedidos realizados a cada fabrica </p>
         * @param model la representacion html 
         * @param id el id de la fabrica a consultar los pedidos       
         * @return la pagina a redireccionar 
      
        */        
        @RequestMapping(value = "/pedidosF/{id}",method = RequestMethod.GET)
        public String   getPedidoF(final Model model,@PathVariable("id") int id)

        {

            final List<Fabrica> listaF = dao.getFab(id); 
        
                for (Fabrica fabrica : listaF) {
                ip = fabrica.getIp();
                puerto = fabrica.getPuerto(); 
            
                }
            final List<Fabrica> listaF2 = dao.getFab(id); 
            int idmarca = 0; 
            for (Fabrica fabrica : listaF2) {
                idmarca += fabrica.getId_fabrica();
            }
            model.addAttribute("id_marca", idmarca);
            String con = ip +":" + puerto; 
            model.addAttribute("conexion", con);


                String uri = "http://" + ip +":"+ puerto+ "/api/pedidos";
                url = uri; 
                RestTemplate restTemplate = new RestTemplate();   
                ResponseEntity<Telefono[]> response = restTemplate.getForEntity(uri,Telefono[].class);
                Telefono[] employees = response.getBody();	  
                model.addAttribute("listaFD", employees); 

                /*
                final List<Telefono> listaDis = dao.listPedidoF();
            
                model.addAttribute("listaFD", listaDis);  

        
                */


        
            
                return "FabricaControl.html";
        }
    



    
         /**
         * <p>Este controlador es para confirmar a fabrica  </p>
         * @deprecated
         * usado solo para hacer pruebas con la api
         * @param model la representacion html 
         * @param n el telefono que se guardo   
         * @return la pagina a redireccionar 
      
        */ 
        @RequestMapping(value = "/updateEstadp")
        public String updateP(final Model model,@ModelAttribute("nuevo") Telefono n)

        {       
        
            String uri = "http://" + ip +":"+ puerto+ "/api/pedidos/6074b74053f92e0fb4017181";
            RestTemplate restTemplate = new RestTemplate();

		    Map<String, String> map = new HashMap<>();
           
            map.put("Estado", "Entregado");         
        

            
		
		    return "a "; 
		
		  

        }




        @RequestMapping(value = "/devolucion")
        public String devolucion(final Model model,@RequestParam(name="ip") String ips,@RequestParam(name="puerto") String puertos,@RequestParam(name="series") String series)

        {       
            String conexion =  "" + ips +":" + puertos; 
            
            model.addAttribute("conexion",conexion);
            model.addAttribute("series",series);

            return "terminalIndividual.html";
           
        }




        @RequestMapping(value = "/devolver")
        public String devolver(final Model model,@RequestParam(name="conexion") String conexion,@RequestParam(name="serie") String num_series)

        {       
        
            String uri = "http://" + conexion+ "/api/serie/"+num_series;
            RestTemplate restTemplate = new RestTemplate();
		    Map<String, String> map = new HashMap<>();           
            map.put("estado", "Devuelto");         

            
		
            restTemplate.put(uri, map);  
		
		  

		  
        
            
            return "redirect:/terminales";
        }

     




   









    

   








}

