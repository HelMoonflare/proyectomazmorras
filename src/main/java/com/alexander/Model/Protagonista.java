package com.alexander.Model;

import java.util.ArrayList;

import com.alexander.Interfaces.Observer;

public class Protagonista extends Personaje {
    private String nombreProta;
    TipoMov direccion;
    ArrayList<Observer> observers;

    /**
     * Constructor de Protagonista.
     * 
     * @param velocidad   Velocidad del protagonista.
     * @param vitalidad   Vitalidad del protagonista.
     * @param fuerza      Fuerza del protagonista.
     * @param nombreProta Nombre del protagonista.
     */
    public Protagonista(int velocidad, int vitalidad, int fuerza, String nombreProta) {
        super(velocidad, vitalidad, fuerza);
        this.nombreProta = nombreProta;
        // Inicializar la lista de observadores en el constructor
        this.observers = new ArrayList<>();
    }
    
    /**
     * Método para obtener el nombre del protagonista.
     * 
     * @return Nombre del protagonista.
     */
    public String getNombreProta() {
        return this.nombreProta;
    }
    /**
     * Método para establecer el nombre del protagonista.
     * 
     * @param nombreProta Nombre a establecer.
     */
    public void setNombreProta(String nombreProta) {
        this.nombreProta = nombreProta;
    }
    /**
     * Método para obtener la dirección del protagonista.
     * 
     * @return Dirección del protagonista.
     */
    public TipoMov getDireccion() {
        return this.direccion;
    }
    /**
     * Método para establecer la dirección del protagonista.
     * 
     * @param direccion Dirección a establecer.
     */
    public void setDireccion(TipoMov direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "{" +
                " nombreProta='" + getNombreProta() + "'" +
                super.toString();
    }

    @Override
    /**
 * Lógica de movimiento del protagonista.
 */
    public void moverse() {
        Proveedor p = Proveedor.getInstance();
        int nuevaX = p.getP().getCordX();
        int nuevaY = p.getP().getCordY();
        switch (direccion) {
            case ARRIBA:
                nuevaX = p.getP().getCordX() - 1;
                break;
            case ABAJO:
                nuevaX = p.getP().getCordX() + 1;
                break;
            case IZQUIERDA:
                nuevaY = p.getP().getCordY() - 1;
                break;
            case DERECHA:
                nuevaY = p.getP().getCordY() + 1;
                break;
            default:
                break;
        }
        System.out.println("Intentando mover al protagonista a: (" + nuevaX + ", " + nuevaY + ")");
        if (p.getTab().EstaCasillaEstaVacia(nuevaX, nuevaY)
                && p.getTab().getTipoCasilla(nuevaX, nuevaY) == TipoCasilla.Suelo) {
            
            System.out.println("Movimiento válido. Actualizando posición del protagonista.");
            p.getTab().actualizarCasilla(p.getP(), nuevaX, nuevaY);
            

            // Mover a los enemigos después de mover al protagonista
            
        }else if (p.getTab().getPersonaje(nuevaX, nuevaY)instanceof Enemigo) {
            p.getP().pegar(p.getTab().getPersonaje(nuevaX, nuevaY));
            
        }
    }
}
