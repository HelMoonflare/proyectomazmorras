package com.alexander.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GestorPersonajes {
    public static Object getEnemigo;
    private Protagonista prota;
    ArrayList<Personaje> personajes;

    
    /**
     * Constructor de GestorPersonajes.
     * @param prota Protagonista principal.
     */
    public GestorPersonajes(Protagonista prota) {
        this.personajes = LecturaEnemigos.leerEnemigos();
        this.prota = prota;
    }
    /**
     * Método para insertar un personaje en el gestor.
     * 
     * @param e Personaje a insertar.
     */
    public void insertarPersonaje(Personaje e) {
        this.personajes.add(e);
    }
    /**
     * Método para eliminar un personaje del gestor.
     * 
     * @param e Personaje a eliminar
     */
    public void eliminarEnemigo(Personaje e) {
        this.personajes.remove(e);
    }
    /**
     * Método para obtener el personaje por su nombre.
     * 
     * @param nombre Nombre del personaje a buscar.
     * @return Personaje encontrado o null si no se encuentra.
     */
    public List<Personaje> getListaPersonaje() {
        return this.personajes;
    }
    /**
     * Método para obtener el protagonista.
     * 
     * @return Protagonista.
     */
    public Protagonista getProta() {
        return prota;
    }

    // Método para inicializar el protagonista
    /**
     * Método para establecer el protagonista.
     * 
     * @param prota Protagonista a establecer.
     */
    public void setProta(Protagonista prota) {
        this.prota = prota;
    }
    /**
     * Método para obtener un enemigo aleatorio.
     * 
     * @return Enemigo aleatorio.
     */
    public Enemigo getEnemigo() {
        Random random = new Random();
        return (Enemigo) personajes.get(random.nextInt(personajes.size()));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Personaje personaje : personajes) {
            sb.append(personaje + "\n");
        }
        return sb.toString();
    }
    /**
     * Método para obtener la lista de personajes.
     * 
     * @return Lista de personajes.
     */
    public void ordenarPersonajes() {
        // ordenamos el arraylist de personajes por velocidad
        // de mayor a menor
        personajes.sort(null);
    }

}
