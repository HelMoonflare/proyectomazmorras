package com.alexander.Model;

import java.util.ArrayList;
import java.util.Random;

import com.alexander.Interfaces.Observer;

public class Sanador extends Personaje implements Observer {
    private String nombreEnemigo;
    private int percepcion;
    ArrayList<Observer> observers;

    /**
     * Constructor de Enemigo.
     * 
     * @param velocidad     Velocidad del enemigo.
     * @param vitalidad     Vitalidad del enemigo.
     * @param fuerza        Fuerza del enemigo.
     * @param percepcion    Percepción del enemigo.
     * @param nombreEnemigo Nombre del enemigo.
     */
    public Sanador(int velocidad, int vitalidad, int fuerza, int percepcion, String nombreEnemigo) {
        super(velocidad, vitalidad, fuerza);
        this.nombreEnemigo = nombreEnemigo;
        this.percepcion = percepcion;
        this.observers = new ArrayList<>(); // Inicialización de la lista de observers
    }

    /**
     * Suscribe un observer al enemigo.
     * 
     * @param observer Observer a suscribir.
     */
    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    /**
     * Elimina un observer del enemigo.
     * 
     * @param observer Observer a eliminar.
     */
    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        observers.forEach(item -> item.onChange());
    }

    /**
     * Obtiene el nombre del enemigo.
     * 
     * @return Nombre del enemigo.
     */
    public String getNombreEnemigo() {
        return this.nombreEnemigo;
    }

    /**
     * Establece el nombre del enemigo.
     * 
     * @param nombreEnemigo Nombre a establecer.
     */
    public void setNombreEnemigo(String nombreEnemigo) {
        this.nombreEnemigo = nombreEnemigo;
    }

    /**
     * Obtiene la percepción del enemigo.
     * 
     * @return Valor de percepción.
     */
    public int getPercepcion() {
        return this.percepcion;
    }

    /**
     * Establece la percepción del enemigo.
     * 
     * @param percepcion Valor de percepción.
     */
    public void setPercepcion(int percepcion) {
        this.percepcion = percepcion;
    }

    @Override
    public String toString() {
        return "{" +
                " nombreEnemigo='" + getNombreEnemigo() + "'" +
                super.toString() +
                ", percepcion='" + getPercepcion() + "'" + getCordX() + " " + getCordY() +
                "}";
    }

    @Override
    public void moverse() {
        Random r = new Random();
        Proveedor p = Proveedor.getInstance();
        Tablero tab = p.getTab();
        int mov = 0;
        Protagonista prota = p.getP();
        ArrayList<Integer[]> direcciones = new ArrayList<>();
        Integer[][] direccionesPosibles = { { 1, 0 }, { 0, -1 }, { -1, 0 }, { 0, 1 } };

        float distancia = this.CalculoAlgoritmo(this.getCordX(), this.getCordY(), prota.getCordX(), prota.getCordY());
        float menor = distancia;
        int nuevaX = this.getCordX();
        int nuevaY = this.getCordY();

        for (int i = 0; i < 4; i++) {
            if (p.getTab().movimientoValido(direccionesPosibles[i][0] + nuevaX, direccionesPosibles[i][1] + nuevaY)) {
                direcciones.add(direccionesPosibles[i]);
            }
        }
        if (distancia >= this.percepcion) {
            mov = r.nextInt(direcciones.size());
        } else {
            for (int i = 0; i < direcciones.size(); i++) {
                menor = CalculoAlgoritmo(p.getP().getCordX(), p.getP().getCordY(), getCordX() + direcciones.get(i)[0],
                        getCordY() + direcciones.get(i)[1]);
                if (menor < distancia) {
                    menor = distancia;
                    mov = i;
                }
            }
        }
        nuevaX = this.getCordX() + direcciones.get(mov)[0];
        nuevaY = this.getCordY() + direcciones.get(mov)[1];
        if (p.getTab().getPersonaje(nuevaX, nuevaY) instanceof Protagonista) {
            p.getP().curar(p.getTab().getPersonaje(nuevaX, nuevaY));
        } else {
            tab.actualizarCasilla(this, nuevaX, nuevaY);
        }

        notifyObservers();

    }

    @Override
    public void onChange() {
        // Lógica para reaccionar a los cambios del protagonista
        this.moverse();
    }

    /**
     * Calcula la distancia entre dos puntos.
     * 
     * @param x1 Coordenada X inicial.
     * @param y1 Coordenada Y inicial.
     * @param x2 Coordenada X final.
     * @param y2 Coordenada Y final.
     * @return Distancia calculada.
     */
    public float CalculoAlgoritmo(int x1, int y1, int x2, int y2) {
        // Calcular la distancia entre los dos puntos
        float distancia = (float) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        return distancia;
    }

}
