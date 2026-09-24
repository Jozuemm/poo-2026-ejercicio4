package model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Inventario {

    private final Map<String, Maquina> maquinas;//esto basicamente es como un diccionario ordenado donde con cada codigo podemos accerder a su info

    public Inventario() {
        maquinas = new LinkedHashMap<>();// esto de la mano con lo de arriba
        }

    public boolean agregar(Maquina maquina) {
        if (maquina == null) {
            throw new IllegalArgumentException("Ya debe haber maquina para continuar");
        }

        String codigo = maquina.getCodigo();
        if (maquinas.containsKey(codigo)){//verificamos que ya haya codigo 
            return false;
        }
        maquinas.put(codigo, maquina);
        return true;
    }

    public Maquina buscar(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return null;
        }

        String codigoBuscado = codigo.trim().toUpperCase();//Elimanamos espacios y ponemos las letras en mayuscuals
        return maquinas.get(codigoBuscado);
    }

    public List<Maquina> obtenerTodas() {
        return new ArrayList<>(maquinas.values());
    }

    public int contarTotal() {
        return maquinas.size();
    }

    public int contarDisponibles(String categoria){
        return contarPorCategoria(categoria, true);
    }

    public int contarAlquiladas(String categoria){
        return contarPorCategoria(categoria, false);
    }

    private int contarPorCategoria(String categoria, boolean disponibilidadBuscada) {
        int contador = 0;

        for (Maquina maquina : maquinas.values()) {
            boolean mismaCategoria = maquina.getCategoria().equalsIgnoreCase(categoria);

            boolean mismaDisponibilidad = maquina.estaDisponible() == disponibilidadBuscada;

            if (mismaCategoria && mismaDisponibilidad) {
                contador++;
            }
        }
        return contador;
    }
    
}
