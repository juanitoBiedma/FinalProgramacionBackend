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
    public static String AUTH_ALTA = "/auth";
    public static String AUTH_LOGIN = "/auth/login";
    public static String LOGOUT = "/logout";
    public static String INFORMACION_USUARIO = "/auth/usuario-logueado";

    // ---ROLES---
    public static String ROL_ADMIN = "ADMINISTRADOR";
    public static String ROL_INVES = "INVESTIGADOR";
    public static String ROL_VIGI = "VIGILANTE";

    // ---ARCHIVOS ESTÁTICOS---
    public static String INDEX = "/public/index.html";
    public static String LOGIN = "/public/login.html";
    public static String LOGIN_ERROR = "/public/login.html?error=true";
    public static String FORBIDDEN_403 = "/public/error403.html";
    public static String PERFIL_USUARIO = "/public/perfilUsuario.html";
    public static String CARPETA_CSS = "/public/css/**";
    public static String CARPETA_JS = "/public/js/**";
    public static String CARPETA_IMG = "/public/img/**";

    public static String USUARIOS = "/public/usuarios.html";
    public static String REGISTRAR_USUARIO = "/public/registrarUsuario.html";
    public static String EDITAR_USUARIO = "/public/editarusuario.html";
    public static String EDITAR_CONTRASENIA_USUARIO = "/public/editarContrasenia.html";

    public static String ENTIDADES = "/public/entidades.html";
    public static String REGISTRAR_ENTIDAD = "/public/registrarEntidad.html";
    public static String EDITAR_ENTIDAD = "/public/editarEntidad.html";

    public static String SUCURSALES = "/public/sucursales.html";
    public static String REGISTRAR_SUCURSAL = "/public/registrarSucursal.html";
    public static String EDITAR_SUCURSAL = "/public/editarSucursal.html";

    public static String VIGILANTES = "/public/vigilantes.html";
    public static String REGISTRAR_VIGILANTE = "/public/registrarVigilante.html";
    public static String EDITAR_VIGILANTE = "/public/editarVigilante.html";
    public static String CONTRATOS_VIGILANTE = "/public/contratosVigilante.html";
    public static String REGISTRAR_CONTRATO = "/public/registrarContrato.html";

    public static String JUECES = "/public/jueces.html";
    public static String REGISTRAR_JUEZ = "/public/registrarJuez.html";
    public static String EDITAR_JUEZ = "/public/editarJuez.html";

    public static String DELITOS = "/public/delitos.html";
    public static String REGISTRAR_DELITO = "/public/registrarDelito.html";
    public static String EDITAR_DELITO = "/public/editarDelito.html";
    public static String DELITOS_DELINCUENTE = "/public/delitosDelincuente.html";

    public static String DELINCUENTES = "/public/delincuentes.html";
    public static String REGISTRAR_DELINCUENTE = "/public/registrarDelincuente.html";
    public static String EDITAR_DELINCUENTE = "/public/editarDelincuente.html";

    public static String BANDAS = "/public/bandas.html";
    public static String REGISTRAR_BANDA = "/public/registrarBanda.html";
    public static String EDITAR_BANDA = "/public/editarBanda.html";

    public static String SENTENCIAS = "/public/sentencias.html";
    public static String REGISTRAR_SENTENCIA = "/public/registrarSentencia.html";
    public static String EDITAR_SENTENCIA = "/public/editarSentencia.html";
    public static String SENTENCIAS_JUEZ = "/public/sentenciasJuez.html";
}
