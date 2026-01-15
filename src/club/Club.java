package club;

import java.util.ArrayList;
import club.Socio.Tipo;
import club.Excepciones.*;

/**
 * Clase que modela un club social.
 * @author Tu Nombre
 */
public class Club {

    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * Cantidad máxima de socios VIP que acepta el club.
     */
    public final static int MAXIMO_VIP = 3;

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Lista de socios del club.
     */
    private ArrayList<Socio> socios;

    // -----------------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------------

    /**
     * Constructor de la clase.
     * post: Se inicializó la lista de socios.
     */
    public Club() {
        socios = new ArrayList<Socio>();
    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Retorna los socios afiliados al club.
     * @return Lista de socios.
     */
    public ArrayList<Socio> darSocios() {
        return socios;
    }

    /**
     * Afilia un nuevo socio al club.
     * pre: La lista de socios está inicializada.
     * post: Se ha afiliado un nuevo socio en el club con los datos dados.
     * @param pCedula Cédula del socio a afiliar. pCedula != null && pCedula != "".
     * @param pNombre Nombre del socio a afiliar. pNombre != null && pNombre != "".
     * @param pTipo Es el tipo de subscripción del socio. pTipo != null.
     * @throws SocioYaExisteException Si ya existe un socio con esa cédula.
     * @throws LimiteVIPException Si se excede el límite de socios VIP.
     */
    public void afiliarSocio(String pCedula, String pNombre, Tipo pTipo)
            throws SocioYaExisteException, LimiteVIPException {

        // Revisar que no haya ya un socio con la misma cédula
        Socio s = buscarSocio(pCedula);

        if(s != null) {
            throw new SocioYaExisteException(
                    "Ya existe un socio con la cédula: " + pCedula
            );
        }

        // Revisar que no se haya alcanzado el límite de subscripciones VIP
        if(pTipo == Tipo.VIP && contarSociosVIP() >= MAXIMO_VIP) {
            throw new LimiteVIPException(
                    "El club no acepta más socios VIP. Límite máximo: " + MAXIMO_VIP
            );
        }

        // Se crea y agrega el nuevo socio al club
        Socio nuevoSocio = new Socio(pCedula, pNombre, pTipo);
        socios.add(nuevoSocio);
    }

    /**
     * Retorna el socio con la cédula dada.
     * pre: La lista de socios está inicializada.
     * @param pCedulaSocio Cédula del socio buscado. pCedulaSocio != null && pCedulaSocio != "".
     * @return El socio buscado, null si el socio buscado no existe.
     */
    public Socio buscarSocio(String pCedulaSocio) {
        Socio elSocio = null;
        boolean encontre = false;
        int numSocios = socios.size();

        for(int i = 0; i < numSocios && !encontre; i++) {
            Socio s = socios.get(i);
            if(s.darCedula().equals(pCedulaSocio)) {
                elSocio = s;
                encontre = true;
            }
        }

        return elSocio;
    }

    /**
     * Retorna la cantidad de socios VIP que tiene el club.
     * pre: La lista de socios está inicializada.
     * @return Número de socios VIP.
     */
    public int contarSociosVIP() {
        int conteo = 0;
        for(Socio socio : socios) {
            if(socio.darTipo() == Tipo.VIP) {
                conteo++;
            }
        }
        return conteo;
    }

    /**
     * Retorna la lista de autorizados del socio con la cédula dada.
     * pre: La lista de socios está inicializada.
     * @param pCedulaSocio La cédula del socio.
     * @return La lista de autorizados del socio.
     * @throws SocioNoExisteException Si no existe el socio.
     */
    public ArrayList<String> darAutorizadosSocio(String pCedulaSocio)
            throws SocioNoExisteException {

        Socio s = buscarSocio(pCedulaSocio);

        if(s == null) {
            throw new SocioNoExisteException(
                    "No existe un socio con la cédula: " + pCedulaSocio
            );
        }

        ArrayList<String> autorizados = new ArrayList<String>();
        autorizados.add(s.darNombre());
        autorizados.addAll(s.darAutorizados());

        return autorizados;
    }

    /**
     * Agrega una nueva persona autorizada por el socio con la cédula dada.
     * pre: El socio con la cédula dada existe.
     * post: Se agregó el nuevo autorizado.
     * @param pCedulaSocio La cédula del socio al cual se va a agregar el autorizado.
     * @param pNombreAutorizado El nombre de la persona a autorizar.
     * @throws SocioNoExisteException Si no existe el socio.
     * @throws AutorizadoInvalidoException Si intenta agregar al mismo socio.
     * @throws FondosInsuficientesException Si no tiene fondos.
     * @throws AutorizadoYaExisteException Si el autorizado ya existe.
     */
    public void agregarAutorizadoSocio(String pCedulaSocio, String pNombreAutorizado)
            throws SocioNoExisteException, AutorizadoInvalidoException,
            FondosInsuficientesException, AutorizadoYaExisteException {

        Socio s = buscarSocio(pCedulaSocio);

        if(s == null) {
            throw new SocioNoExisteException(
                    "No existe un socio con la cédula: " + pCedulaSocio
            );
        }

        s.agregarAutorizado(pNombreAutorizado);
    }

    /**
     * Elimina la persona autorizada por el socio con la cédula dada.
     * @param pCedulaSocio La cédula del socio que autorizó a la persona a eliminar.
     * @param pNombreAutorizado El nombre del autorizado a eliminar.
     * @throws SocioNoExisteException Si no existe el socio.
     * @throws AutorizadoConFacturasException Si el autorizado tiene facturas.
     */
    public void eliminarAutorizadoSocio(String pCedulaSocio, String pNombreAutorizado)
            throws SocioNoExisteException, AutorizadoConFacturasException {

        Socio s = buscarSocio(pCedulaSocio);

        if(s == null) {
            throw new SocioNoExisteException(
                    "No existe un socio con la cédula: " + pCedulaSocio
            );
        }

        s.eliminarAutorizado(pNombreAutorizado);
    }

    /**
     * Registra un consumo a un socio o a su autorizado.
     * post: Se agregó una nueva factura al vector del socio.
     * @param pCedulaSocio La cédula del socio.
     * @param pNombreCliente El nombre la persona que realizó en consumo.
     * @param pConcepto El concepto del consumo.
     * @param pValor El valor del consumo.
     * @throws SocioNoExisteException Si no existe el socio.
     * @throws FondosInsuficientesException Si no hay fondos suficientes.
     */
    public void registrarConsumo(String pCedulaSocio, String pNombreCliente,
                                 String pConcepto, double pValor)
            throws SocioNoExisteException, FondosInsuficientesException {

        Socio s = buscarSocio(pCedulaSocio);

        if(s == null) {
            throw new SocioNoExisteException(
                    "No existe un socio con la cédula: " + pCedulaSocio
            );
        }

        s.registrarConsumo(pNombreCliente, pConcepto, pValor);
    }

    /**
     * Retorna la lista de facturas de un socio.
     * pre: Existe el socio con la cédula dada.
     * @param pCedulaSocio La cédula del socio.
     * @return La lista de facturas del socio.
     * @throws SocioNoExisteException Si no existe el socio.
     */
    public ArrayList<Factura> darFacturasSocio(String pCedulaSocio)
            throws SocioNoExisteException {

        Socio s = buscarSocio(pCedulaSocio);

        if(s == null) {
            throw new SocioNoExisteException(
                    "No existe un socio con la cédula: " + pCedulaSocio
            );
        }

        return s.darFacturas();
    }

    /**
     * Realiza el pago de la factura de un socio.
     * post: Se borró la factura del vector del socio.
     * @param pCedulaSocio La cédula del socio.
     * @param pFacturaIndice El índice de la factura a pagar.
     * @throws SocioNoExisteException Si no existe el socio.
     * @throws FondosInsuficientesException Si no hay fondos suficientes.
     */
    public void pagarFacturaSocio(String pCedulaSocio, int pFacturaIndice)
            throws SocioNoExisteException, FondosInsuficientesException {

        Socio s = buscarSocio(pCedulaSocio);

        if(s == null) {
            throw new SocioNoExisteException(
                    "No existe un socio con la cédula: " + pCedulaSocio
            );
        }

        s.pagarFactura(pFacturaIndice);
    }

    /**
     * Aumenta los fondos de un socio en la cantidad dada.
     * post: Los fondos del socio aumentaron en el valor especificado.
     * @param pCedulaSocio La cédula del socio.
     * @param pValor Valor por el cual se desean aumentar los fondos.
     * @throws SocioNoExisteException Si no existe el socio.
     * @throws LimiteFondosException Si se excede el límite de fondos.
     */
    public void aumentarFondosSocio(String pCedulaSocio, double pValor)
            throws SocioNoExisteException, LimiteFondosException {

        Socio s = buscarSocio(pCedulaSocio);

        if(s == null) {
            throw new SocioNoExisteException(
                    "No existe un socio con la cédula: " + pCedulaSocio
            );
        }

        s.aumentarFondos(pValor);
    }

    // -----------------------------------------------------------------
    // Métodos de Extensión - REQUERIDOS POR LA CONSIGNA
    // -----------------------------------------------------------------

    /**
     * MÉTODO Calcula el total de consumos de un socio dado su número de cédula.
     *
     * @param pCedula Número de cédula del socio.
     * @return El valor total de consumos. Retorna 0 si no hay consumos.
     * @throws SocioNoExisteException Si no existe un socio con esa cédula.
     */
    public double calcularTotalConsumos(String pCedula) throws SocioNoExisteException {
        double totalConsumos = 0.0;

        // Buscar el socio
        Socio socio = buscarSocio(pCedula);

        // Si no existe el socio, lanzar excepción
        if(socio == null) {
            throw new SocioNoExisteException(
                    "No existe un socio con la cédula: " + pCedula
            );
        }

        // Obtener las facturas del socio
        ArrayList<Factura> facturas = socio.darFacturas();

        // Si no hay facturas, retornar 0
        if(facturas == null || facturas.isEmpty()) {
            return 0.0;
        }

        // Calcular el total sumando todas las facturas
        for(Factura factura : facturas) {
            totalConsumos += factura.darValor();
        }

        return totalConsumos;
    }

    /**
     * MÉTODO Verifica si un socio puede ser eliminado del club según las reglas establecidas.
     * @param pCedula La cédula del socio a verificar.
     * @return String con el mensaje indicando si se puede eliminar o no, y la razón.
     */
    public String sePuedeEliminarSocio(String pCedula) {
        String resultado = "";

        try {
            // Buscar el socio
            Socio socio = buscarSocio(pCedula);

            // CASO 1: No existe un socio con la cédula
            if(socio == null) {
                throw new SocioNoExisteException(
                        "No existe un socio con la cédula: " + pCedula
                );
            }

            // CASO 2: El socio es de tipo VIP
            if(socio.darTipo() == Tipo.VIP) {
                throw new SocioVIPNoEliminableException(
                        "No se pueden eliminar socios de tipo VIP."
                );
            }

            // CASO 3: El socio tiene facturas pendientes de pago
            ArrayList<Factura> facturas = socio.darFacturas();
            if(facturas != null && !facturas.isEmpty()) {
                throw new FacturasPendientesException(
                        "No se puede eliminar un socio con facturas pendientes. " +
                                "Facturas pendientes: " + facturas.size()
                );
            }

            // CASO 4: El socio tiene más de un autorizado
            ArrayList<String> autorizados = socio.darAutorizados();
            if(autorizados != null && autorizados.size() > 1) {
                throw new MuchosAutorizadosException(
                        "No se puede eliminar un socio con más de un autorizado. " +
                                "Autorizados: " + autorizados.size()
                );
            }

            // Si pasa todas las validaciones, se puede eliminar
            resultado = "✓ El socio con cédula " + pCedula + " SÍ puede ser eliminado." +
                    "\n  Cumple todas las condiciones para ser eliminado.";

        } catch(SocioNoExisteException e) {
            resultado = "✗ CASO 1: " + e.getMessage();
        } catch(SocioVIPNoEliminableException e) {
            resultado = "✗ CASO 2: " + e.getMessage();
        } catch(FacturasPendientesException e) {
            resultado = "✗ CASO 3: " + e.getMessage();
        } catch(MuchosAutorizadosException e) {
            resultado = "✗ CASO 4: " + e.getMessage();
        }

        return resultado;
    }
}