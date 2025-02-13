package com.minseg.spring.utilities;

public class Constantes {

    // ---ERRORES---
    public static String ERROR_ROL_NOT_FOUND = "El rol especificado no existe.";
    public static String ERROR_ID_ENTIDAD_NOT_FOUND = "Entidad no válida o ID de entidad no proporcionado";
    public static String ERROR_ID_BANDA_NOT_FOUND = "Banda no válida o ID de banda no proporcionado";
    public static String ERROR_CAMPOS_OBLIGATORIOS = "Todos los campos de contraseña son obligatorios";
    public static String ERROR_PASS_NO_COINCIDE = "Las nuevas contraseñas no coinciden";
    public static String ERROR_OLD_PASS_NO_COINCIDE = "Contraseña antigua incorrecta";

    // ---VARIABLES---
    public static String NUEVA_CONTRASENIA = "nuevaContrasenia";
    public static String REPETIR_NUEVA_CONTRASENIA = "repetirNuevaContrasenia";
    public static String ANTIGUA_CONTRASENIA = "antiguaContrasenia";

    // ---MENSAJES---
    public static String EXITO_CAMBIO_CONTRASENIA = "Contraseña actualizada correctamente!";

    // ---ENDPOINTS DE SECURITYCONFIG---
    public static String AUTH = "/auth";
    public static String AUTH_LOGIN = "/auth/login";
    public static String LOGOUT = "/logout";

    // ---ROLES---
    public static String ROL_ADMIN = "ADMINISTRADOR";
    public static String ROL_INVES = "INVESTIGADOR";
    public static String ROL_VIGI = "VIGILANTE";

    // ---ARCHIVOS ESTÁTICOS---
    public static String INDEX = "/index.html";
    public static String LOGIN = "/login.html";
    public static String LOGIN_ERROR = "/login.html?error=true";
    public static String FORBIDDEN_403 = "/error403.html";
    public static String PERFIL_USUARIO = "/perfilUsuario.html";
    public static String CARPETA_CSS = "/css/**";
    public static String CARPETA_JS = "/js/**";
    public static String CARPETA_IMG = "/img/**";

    public static String USUARIOS = "/usuarios.html";
    public static String REGISTRAR_USUARIO = "/registrarUsuario.html";
    public static String EDITAR_USUARIO = "/editarusuario.html";
    public static String EDITAR_CONTRASENIA_USUARIO = "/editarContrasenia.html";

    public static String ENTIDADES = "/entidades.html";
    public static String REGISTRAR_ENTIDAD = "/registrarEntidad.html";
    public static String EDITAR_ENTIDAD = "/editarEntidad.html";

    public static String SUCURSALES = "/sucursales.html";
    public static String REGISTRAR_SUCURSAL = "/registrarSucursal.html";
    public static String EDITAR_SUCURSAL = "/editarSucursal.html";

    public static String VIGILANTES = "/vigilantes.html";
    public static String REGISTRAR_VIGILANTE = "/registrarVigilante.html";
    public static String EDITAR_VIGILANTE = "/editarVigilante.html";
    public static String CONTRATOS_VIGILANTE = "/contratosVigilante.html";
    public static String REGISTRAR_CONTRATO = "/registrarContrato.html";

    public static String JUECES = "/jueces.html";
    public static String REGISTRAR_JUEZ = "/registrarJuez.html";
    public static String EDITAR_JUEZ = "/editarJuez.html";

    public static String DELITOS = "/delitos.html";
    public static String REGISTRAR_DELITO = "/registrarDelito.html";
    public static String EDITAR_DELITO = "/editarDelito.html";
    public static String DELITOS_DELINCUENTE = "/delitosDelincuente.html";

    public static String DELINCUENTES = "/delincuentes.html";
    public static String REGISTRAR_DELINCUENTE = "/registrarDelincuente.html";
    public static String EDITAR_DELINCUENTE = "/editarDelincuente.html";

    public static String BANDAS = "/bandas.html";
    public static String REGISTRAR_BANDA = "/registrarBanda.html";
    public static String EDITAR_BANDA = "/editarBanda.html";

    public static String SENTENCIAS = "/sentencias.html";
    public static String REGISTRAR_SENTENCIA = "/registrarSentencia.html";
    public static String EDITAR_SENTENCIA = "/editarSentencia.html";
    public static String SENTENCIAS_JUEZ = "/sentenciasJuez.html";
}
