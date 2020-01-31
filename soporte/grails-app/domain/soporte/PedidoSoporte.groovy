package soporte

class PedidoSoporte {

    String emailAutor
    List<String> etiquetas
    Map<String, Integer> ocurrenciasDeTemas

    static hasOne = [miembro: MiembroEquipo, autor: AutorPedidoSoporte]
    static hasMany = [mensajes: MensajePedidoSoporte]

    static belongsTo = [aplicacion: AplicacionCliente]

    static constraints = {
        miembro nullable: true
    }

    static mapping = {
        id generator: 'assigned', name: 'emailAutor'
    }

    def PedidoSoporte(autor) {
        setAutor(autor)
        emailAutor = autor.email
        mensajes = []
        etiquetas = []
        ocurrenciasDeTemas = [:]
    }

    def agregarMensaje(pedidoSoporteEntrante) {
        // evaluar y luego se puede taggear
        def nuevoMensaje = new MensajePedidoSoporte(pedidoSoporteEntrante.nombreAutor, pedidoSoporteEntrante.contenido)
        procesarMensaje(nuevoMensaje)
        addToMensajes(nuevoMensaje)
    }

    // evalua el mensaje en base a los temas de la aplicacion cliente y carga una lista de ponderaciones
    def procesarMensaje(mensaje) {
        // if nuevos temas! procesar todos!
        def ocurrenciasTemas = mensaje.obtenerOcurrenciasDeTemas(aplicacion.temas)
        ocurrenciasTemas.each { tema, ocurrencias ->
            if (!ocurrenciasDeTemas[tema])
                ocurrenciasDeTemas[tema] = 0
            ocurrenciasDeTemas[tema] += ocurrencias
        }
    }

    def etiquetar() {
        // etiqueta / ocurrencias
        // 1) identificar si el mensaje es de un agente o de un usuario (filtro solo mensajes de usuario)
        // 2) se toman los temas definidos en la aplicacion
        // 3) tomar los nuevos mensajes, si cambiaron los temas, evaluar todos los mensajes
        // 4) organizacion.temas.each { if mensaje.perteceAlTema(it) --> sumar ocurrencia al tag
        ocurrenciasDeTemas.each {
            if (!(it.key in etiquetas)) {
                addToEtiquetas(it.key)
            }
        }
    }

    def resolver() {
        // usar los temas q tiene asignados y devuelve un resultado o indica que no puede hacerlo
        true
    }

    def asignar(miembro) {
        // esta operacion deberia hacerse acá y no en el miembro equipo
        if (this.miembro)
            this.miembro.removerPedidoSoporte(this)
        this.miembro = miembro;
        this.miembro.agregarPedidoSoporte(this);
    }
}
