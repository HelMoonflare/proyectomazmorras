package com.alexander.Model;



public class Personaje implements Comparable<Personaje> {
    protected int velocidad;
    protected int vitalidad;
    protected int fuerza;
    private int cordX;
    private int cordY;

    /**
     * Constructor de Personaje.
     * 
     * @param velocidad Velocidad del personaje.
     * @param vitalidad Vitalidad del personaje.
     * @param fuerza    Fuerza del personaje.
     */
    public Personaje(int velocidad, int vitalidad, int fuerza) {
        this.velocidad = velocidad;
        this.vitalidad = vitalidad;
        this.fuerza = fuerza;
    }

    /**
     * Obtiene la velocidad del personaje.
     * @return Velocidad.
     */
    public int getVelocidad() {
        return this.velocidad;
    }
    /**
     * Establece la velocidad del personaje.
     * @param velocidad Velocidad a establecer.
     */
    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }
    /**
     * Obtiene la vitalidad del personaje.
     * @return Vitalidad.
     */
    public int getVitalidad() {
        return this.vitalidad;
    }
    /**
     * Establece la vitalidad del personaje.
     * @param vitalidad Vitalidad a establecer.
     */
    public void setVitalidad(int vitalidad) {
        this.vitalidad = vitalidad;
    }
    /**
     * Obtiene la fuerza del personaje.
     * @return Fuerza.
     */
    public int getFuerza() {
        return this.fuerza;
    }
    /**
     * Establece la fuerza del personaje.
     * @param fuerza Fuerza a establecer.
     */
    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
    }
    /**
     * Obtiene la coordenada X del personaje.
     * @return Coordenada X.
     */
    public int getCordX() {
        return this.cordX;
    }
    /**
     * Establece la coordenada X del personaje.
     * @param cordX Coordenada X a establecer.
     */
    public void setCordX(int cordX) {
        this.cordX = cordX;
    }
    /**
     * Obtiene la coordenada Y del personaje.
     * @return Coordenada Y.
     */
    public int getCordY() {
        return this.cordY;
    }
    /**
     * Establece la coordenada Y del personaje.
     * @param cordY Coordenada Y a establecer.
     */
    public void setCordY(int cordY) {
        this.cordY = cordY;
    }
    public void pegar(Personaje contrincante){
        contrincante.setVitalidad(contrincante.getVitalidad()-(this.getFuerza()/10));
    }
    /**
     * Método para recibir un golpe.
     */
    public void recibirGolpe() {
        
    }
    
    @Override
    public String toString() {
        return "{" +
            " velocidad='" + getVelocidad() + "'" +
            ", vitalidad='" + getVitalidad() + "'" +
            ", fuerza='" + getFuerza() + "'" +
            "}";
    }
    /**
     * Método para mover el personaje.
     */
    public void moverse(){
      
    }

     @Override
    /**
     * Método para comparar dos gestores de personajes por la velocidad del
     * protagonista.
     * 
     * @param o GestorPersonajes a comparar.
     * @return -1 si el protagonista es más rápido, 1 si es más lento, 0 si son
     *         iguales.
     */
    public int compareTo(Personaje o) {
        // comparamos velocidad de los personajes para ordenar el arraylist de mayor a
        // menor
        if (this.getVelocidad() > o.getVelocidad()) {
            return -1;
        } else if (this.getVelocidad() < o.getVelocidad()) {
            return 1;
        } else {
            return 0;
        }
    }

}

