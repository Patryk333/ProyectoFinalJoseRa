package edu.alumno.patryk.proyecto_futbol.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

public class PaginationHelper {

    private PaginationHelper() {

    }

    /**
     * Crea un objeto Pageable a partir de los parámetros dados.
     *
     * @param page Número de página (empieza en 0)
     * @param size Tamaño de la página
     * @param sort Array de criterios de ordenación (ej. "campo,asc")
     * @return Objeto Pageable con la paginación y ordenación configurada
     */

    public static Pageable createPageable(int page, int size, String[] sort) {

        List<Order> criteriosOrdenacion = new ArrayList<Order>();

        if (sort[0].contains(",")) {
            for (String criterioOrdenacion : sort) {
                String[] orden = criterioOrdenacion.split(",");
                if (orden.length > 1) {
                    criteriosOrdenacion.add(new Order(Direction.fromString(orden[1]), orden[0]));
                } else {
                    criteriosOrdenacion.add(new Order(Direction.fromString("asc"), orden[0]));
                }
            }
        } else {
            criteriosOrdenacion.add(new Order(Direction.fromString(sort[1]), sort[0]));
        }

        Sort sorts = Sort.by(criteriosOrdenacion);

        return PageRequest.of(page, size, sorts);
    }
}