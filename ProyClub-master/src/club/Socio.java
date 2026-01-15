package club;
import java.util.ArrayList;

/**
 * Clase que modela un socio.
 */
public class Socio
{
    // -----------------------------------------------------------------
    // Enumeraciones
    // -----------------------------------------------------------------

    /**
     * Enumeraciones para los tipos de suscripci�n.
     */
    public enum Tipo
    {
        /**
         * Representa el socio VIP.
         */
        VIP,
        /**
         * Representa el socio regular.
         */
        REGULAR
    }
    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * Dinero base con el que empiezan todos los socios regulares.
     */
    public final static double FONDOS_INICIALES_REGULARES = 50;

    /**
     * Dinero base con el que empiezan todos los socios VIP.
     */
    public final static double FONDOS_INICIALES_VIP = 100;

    /**
     * Dinero m�ximo que puede tener un socio regular en sus fondos.
     */
    public final static double MONTO_MAXIMO_REGULARES = 1000;

    /**
     * Dinero m�ximo que puede tener un socio VIP en sus fondos.
     */
    public final static double MONTO_MAXIMO_VIP = 5000;

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * C�dula del socio.
     */
    private String cedula;

    /**
     * Nombre del socio.
     */
    private String nombre;

    /**
     * Dinero que el socio tiene disponible.
     */
    private double fondos;

    /**
     * Tipo de subscripci�n del socio.
     */
    private Tipo tipoSubscripcion;

    /**
     * Facturas que tiene por pagar el socio.
     */
    private ArrayList<Factura> facturas;

    /**
     * Nombres de las personas autorizadas para este socio.
     */
    private ArrayList<String> autorizados;

    // -----------------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------------
    /**
     * Crea un socio del club. <br>
     * <b>post: </b> Se cre� un objeto socio con los valores pasados por par�metro.<br>
     * El vector de facturas y el vector de autorizados fueron inicializados. <br>
     * Se inicializaron los fondos disponibles en FONDOS_INICIALES.
     * @param pCedula Corresponde a la c�dula del socio nuevo. pCedula != null && pCedula != "".
     * @param pNombre Corresponde al nombre del socio nuevo. pNombre != null && pNombre != "".
     * @param pTipo Corresponde al tipo de subscripci�n del socio. pTipo pertenece {Tipo.VIP, Tipo.REGULAR}.
     */
    public Socio( String pCedula, String pNombre, Tipo pTipo )
    {
        cedula = pCedula;
        nombre = pNombre;
        tipoSubscripcion = pTipo;

        switch( tipoSubscripcion )
        {
            case VIP:
                fondos = FONDOS_INICIALES_VIP;
                break;
            default:
                fondos = FONDOS_INICIALES_REGULARES;
        }

        facturas = new ArrayList<Factura>( );
        autorizados = new ArrayList<String>( );
    }

    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------

    /**
     * Retorna el nombre del socio. <br>
     * @return El nombre del socio.
     */
    public String darNombre( )
    {
        return nombre;
    }

    /**
     * Retorna la c�dula del socio. <br>
     * @return La c�dula del socio.
     */
    public String darCedula( )
    {
        return cedula;
    }

    /**
     * Retorna los fondos disponibles del socio. <br>
     * @return Los fondos del socio.
     */
    public double darFondos( )
    {
        return fondos;
    }

    /**
     * Retorna el tipo de subscripci�n del socio. <br>
     * @return El tipo de subscripci�n del socio.
     */
    public Tipo darTipo( )
    {
        return tipoSubscripcion;
    }

    /**
     * Retorna la lista de facturas. <br>
     * @return Retorna una lista con todas las facturas pendientes de pago del socio.
     */
    public ArrayList<Factura> darFacturas( )
    {
        return facturas;
    }

    /**
     * Retorna la lista de autorizados por el socio. <br>
     * @return La lista con los nombres de los autorizados por este socio.
     */
    public ArrayList<String> darAutorizados( )
    {
        return autorizados;
    }

    /**
     * Indica si un autorizado pertenece o no a lista del socio. <br>
     * <b>pre: </b> La lista de autorizados ha sido inicializada. <br>
     * @param pNombreAutorizado Nombre del autorizado a buscar. pNombreAutorizado != null && pNombreAutorizado != "".
     * @return True si en la lista existe un autorizado con el nombre dado, false de lo contrario.
     */
    private boolean existeAutorizado( String pNombreAutorizado )
    {
        boolean encontro = false;

        for( int i = 0; i < autorizados.size( ) && !encontro; i++ )
        {
            String a = autorizados.get( i );
            if( a.equals( pNombreAutorizado ) )
            {
                encontro = true;
            }
        }
        return encontro;
    }
    
    /**
     * Indica si un autorizado tiene una factura asociada.<br>
     * <b>pre: </b> La lista de facturas ha sido inicializada. <br>
     * @param pNombreAutorizado Nombre del autorizado a verificar. pNombreAutorizado != null && pNombreAutorizado != "".
     * @return True si el autorizado tiene factura asociada, false de lo contrario.
     */
    private boolean tieneFacturaAsociada( String pNombreAutorizado)
    {
        boolean tiene = false;
        for( int i = 0; i < facturas.size( ) && !tiene; i++ )
        {
            Factura factura = facturas.get( i );
            if( factura.darNombre( ).equals( pNombreAutorizado ) )
            {
                tiene = true;
            }
        }
        
        return tiene;
    }

    /**
     * Aumenta los fondos disponibles del socio.
     * @param pFondos Valor por adicionar a los fondos. pFondos > 0.
     *
     */
    public void aumentarFondos( double pFondos )
    {
        if( tipoSubscripcion == Tipo.VIP && pFondos + fondos > MONTO_MAXIMO_VIP )
        {
            System.out.println("Con este monto se exceder�an los fondos m�ximos de un socio VIP, ingrese una cantidad menor" );


        }
        else if( tipoSubscripcion == Tipo.REGULAR && pFondos + fondos > MONTO_MAXIMO_REGULARES )
        {
            System.out.println( "Con este monto se exceder�an los fondos m�ximos de un socio regular, ingrese una cantidad menor" );
        }
        else
        {
            fondos = fondos + pFondos;
        }
    }

    /**
     * Registra un nuevo consumo para el socio, realizado por �l mismo o por una de sus personas autorizadas. <br>
     * <b>pre: </b> La lista de facturas ha sido inicializada. <br>
     * El nombre pertenece a la lista de autorizados.<br>
     * <b>post: </b> Se agreg� una nueva factura .
     * @param pNombre El nombre de la persona que realiz� el consumo. pNombre != null && pNombre != "".
     * @param pConcepto Es la descripci�n del consumo. pConcepto != null && pConcepto != "".
     * @param pValor Es el valor del consumo. pValor >= 0.
     *
     */
    public void registrarConsumo( String pNombre, String pConcepto, double pValor )
    {

        if( pValor > fondos )
        {
            System.out.println( "El socio no posee fondos suficientes para este consumo" );
        }
        else
        {
            Factura nuevaFactura = new Factura( pNombre, pConcepto, pValor );
            facturas.add( nuevaFactura );
        }
    }

    /**
     * Agrega una nueva persona autorizada al socio. <br>
     * <b>pre: </b> La lista de autorizados ha sido inicializada. <br>
     * <b>post: </b> Se agreg� un nuevo autorizado.
     * @param pNombreAutorizado Es el nombre de la nueva persona autorizada para el socio. pNombreAutorizado != null.
     *
     */
    public void agregarAutorizado( String pNombreAutorizado )
    {
        // Verificar que el nombre del socio no es el mismo del que se quiere autorizar
        if( pNombreAutorizado.equals( darNombre( ) ) )
        {
            System.out.println( "No puede agregar el socio como autorizado." );
        }

        // Verificar que el socio posee fondos para financiar un nuevo autorizado
        if( fondos == 0 )
        {
            System.out.println( "El socio no tiene fondos para financiar un nuevo autorizado." );
        }
        // Si el nombre no exist�a entonces lo agregamos
        if( !existeAutorizado( pNombreAutorizado ) )
        {
            autorizados.add( pNombreAutorizado );
        }
        else
        {
            System.out.println("El autorizado ya existe." );
        }
    }

    /**
     * Elimina el autorizado del socio con el nombre dado. <br>
     * <b>pre: </b> La lista de autorizados ha sido inicializada. <br>
     * <b>post: </b> Se elimin� un socio, con nombre igual a alguno de los vinculados.
     * @param pNombreAutorizado Nombre del autorizado. pNombreAutorizado != null.
     *
     */
    public void eliminarAutorizado( String pNombreAutorizado )
    {
        boolean encontro = false;
        int numAutorizados = autorizados.size( );
        if(tieneFacturaAsociada( pNombreAutorizado )){
            System.out.println( pNombreAutorizado + " tiene una factura sin pagar.");
        }
        for( int i = 0; i < numAutorizados && !encontro; i++ )
        {
            String a = autorizados.get( i );
            if( a.equals( pNombreAutorizado ) )
            {
                encontro = true;
                autorizados.remove( i );
            }
        }
    }

    /**
     * Paga la factura con el �ndice dado. <br>
     * <b>pre: </b> La lista de facturas ha sido inicializada. <br>
     * <b>post: </b> Se borr� la factura de la lista de facturas.
     * @param pIndiceFactura Posici�n de la factura a eliminar. facturaIndice >= 0.
     *
     */
    public void pagarFactura( int pIndiceFactura )
    {
        Factura factura = facturas.get( pIndiceFactura );
        if( factura.darValor( ) > fondos )
        {
            System.out.println( "El socio no posee fondos suficientes para pagar esta factura" );
        }
        else
        {
            fondos = fondos - factura.darValor( );
            facturas.remove( pIndiceFactura );
        }
    }

    /**
     * Retorna la cadena que representa al socio.
     * @return Cadena de caracteres con la informaci�n del socio con el siguiente formato: <c�dula> - <nombre>.
     */
    public String toString( )
    {
        String socio = cedula + " - " + nombre;
        return socio;
    }

}