package com.tecabix.bz.cuenta;

import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.tecabix.db.entity.Cuenta;
import com.tecabix.db.entity.Sesion;
import com.tecabix.db.repository.CuentaRepository;
import com.tecabix.res.b.RSB009;
import com.tecabix.sv.rq.RQSV015;

/**
 *
 * @author Ramirez Urrutia Angel Abinadi
 */
public class Cuenta001BZ {

    /**
     * Repositorio para acceder a los datos de cuentas.
     */
    private final CuentaRepository cuentaRepository;

    /**
     * Cuenta de origen no encontrada.
     */
    private static final String NO_SE_ENCONTRO_LA_CUENTA;

    static {
        NO_SE_ENCONTRO_LA_CUENTA = "No se encontro la cuenta.";
    }

    /**
     * Constructor de la clase {@code Cuenta001BZ}.
     *
     * @param repository el repositorio de cuentas que se utilizará
     *                         para acceder a los datos de cuenta.
     */
    public Cuenta001BZ(final CuentaRepository repository) {
        this.cuentaRepository = repository;
    }

    /**
     * Obtiene el saldo de la cuenta según los datos de la solicitud.
     *
     * @param rqsv015 Petición con parámetros para identificar la cuenta.
     * @return Respuesta HTTP con {@link RSB009} que contiene el saldo.
     */
    public ResponseEntity<RSB009> obtenerSaldo(final RQSV015 rqsv015) {
        RSB009 rsb009 = rqsv015.getRsb009();
        Sesion sesion = rqsv015.getSesion();

        Optional<Cuenta> optional;
        optional = cuentaRepository.findByUsuario(sesion.getUsuario().getId());
        if (optional.isEmpty()) {
            return rsb009.notFound(NO_SE_ENCONTRO_LA_CUENTA);
        }
        return rsb009.ok(optional.get());
    }
}
