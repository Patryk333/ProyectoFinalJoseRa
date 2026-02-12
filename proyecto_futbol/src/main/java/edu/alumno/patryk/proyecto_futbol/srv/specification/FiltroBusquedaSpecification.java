package edu.alumno.patryk.proyecto_futbol.srv.specification;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;

import edu.alumno.patryk.proyecto_futbol.model.dto.FiltroBusqueda;
import edu.alumno.patryk.proyecto_futbol.srv.specification.operacion.ContieneOperacionStrategy;
import edu.alumno.patryk.proyecto_futbol.srv.specification.operacion.IgualOperacionStrategy;
import edu.alumno.patryk.proyecto_futbol.srv.specification.operacion.MayorQueOperacionStrategy;
import edu.alumno.patryk.proyecto_futbol.srv.specification.operacion.MenorQueOperacionStrategy;
import edu.alumno.patryk.proyecto_futbol.srv.specification.operacion.OperacionBusquedaStrategy;
import io.micrometer.common.lang.NonNull;
import io.micrometer.common.lang.Nullable;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class FiltroBusquedaSpecification<T> implements Specification<T> {

    private List<FiltroBusqueda> filtrosBusqueda;
    private final List<OperacionBusquedaStrategy> estrategias;

    private List<OperacionBusquedaStrategy> getDefaultStrategies() {
        return List.of(
                new IgualOperacionStrategy(),
                new ContieneOperacionStrategy(),
                new MayorQueOperacionStrategy(),
                new MenorQueOperacionStrategy()
        // Añadir nuevas estrategias aquí sin modificar otras clases
        );
    }

    public FiltroBusquedaSpecification(List<FiltroBusqueda> filtrosBusqueda) {
        this.filtrosBusqueda = filtrosBusqueda;
        // Cuando no se especifican estrategias asignamos las estrategias de las
        // operaciones por defecto
        this.estrategias = getDefaultStrategies();
    }

    public FiltroBusquedaSpecification(List<FiltroBusqueda> filtrosBusqueda,
            List<OperacionBusquedaStrategy> estrategias) {
        this.filtrosBusqueda = filtrosBusqueda;
        this.estrategias = estrategias;
    }

    // Método Builder
    public Specification<T> build(List<FiltroBusqueda> filtros) {
        if (filtros == null || filtros.isEmpty()) {
            return (root, query, cb) -> cb.conjunction();
        }

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicados = filtros.stream()
                    .map(filtro -> crearPredicado(root, criteriaBuilder, filtro))
                    .collect(Collectors.toList());

            return predicados.isEmpty()
                    ? criteriaBuilder.conjunction()
                    : criteriaBuilder.and(predicados.toArray(new Predicate[0]));
        };
    }

    private Predicate crearPredicado(Root<T> root, CriteriaBuilder criteriaBuilder, FiltroBusqueda filtro) {
        return estrategias.stream()
                .filter(estrategia -> estrategia.soportaOperacion(filtro.getOperacion()))
                .findFirst()
                .map(estrategia -> estrategia.crearPredicado(root, criteriaBuilder, filtro))
                .orElseThrow(() -> new UnsupportedOperationException(
                        "Operador de filtro no permitido: " + filtro.getOperacion()));
    }

    @Override
    public Predicate toPredicate(@NonNull Root<T> root, @Nullable CriteriaQuery<?> query,
            @NonNull CriteriaBuilder criteriaBuilder) {
        // No hay filtros que aplicar
        if (filtrosBusqueda == null || filtrosBusqueda.isEmpty()) {
            return criteriaBuilder.conjunction();
        }
        // Hay filtros que aplicar
        List<Predicate> predicados = filtrosBusqueda.stream()
                .map(filtro -> crearPredicado(root, criteriaBuilder, filtro))
                .collect(Collectors.toList());

        // Si no hay predicados, devolvemos una conjunción vacía para evitar errores
        return predicados.isEmpty()
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.and(predicados.toArray(new Predicate[0]));
    }
}
