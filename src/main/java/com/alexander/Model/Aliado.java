package com.alexander.Model;

import java.util.ArrayList;
import java.util.Random;

import com.alexander.Interfaces.Observer;

public class Aliado extends Personaje implements Observer {
    private String nombreAliado;
    private int percepcion;
    ArrayList<Observer> observers;

    /**
     * Constructor de aliado.
     * 
     * @param velocidad     Velocidad del aliado.
     * @param vitalidad     Vitalidad del aliado.
     * @param fuerza        Fuerza del aliado.
     * @param percepcion    Percepción del aliado.
     * @param nombreAliado  Nombre del aliado.
     */
    public Aliado(int velocidad, int vitalidad, int fuerza, int percepcion, String nombreAliado) {
        super(velocidad, vitalidad, fuerza);
        this.nombreAliado = nombreAliado;
        this.percepcion = percepcion;
        this.observers = new ArrayList<>(); // Inicialización de la lista de observers
    }

    /**
     * Suscribe un observer al aliado.
     * 
     * @param observer Observer a suscribir.
     */
    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    /**
     * Elimina un observer del aliado.
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
     * Obtiene el nombre del aliado.
     * 
     * @return Nombre del aliado.
     */
    public String getnombreAliado() {
        return this.nombreAliado;
    }

    /**
     * Establece el nombre del aliado.
     * 
     * @param nombreAliado Nombre a establecer.
     */
    public void setnombreAliado(String nombreAliado) {
        this.nombreAliado = nombreAliado;
    }

    /**
     * Obtiene la percepción del aliado.
     * 
     * @return Valor de percepción.
     */
    public int getPercepcion() {
        return this.percepcion;
    }

    /**
     * Establece la percepción del aliado.
     * 
     * @param percepcion Valor de percepción.
     */
    public void setPercepcion(int percepcion) {
        this.percepcion = percepcion;
    }

    @Override
    public String toString() {
        return "{" +
                " nombreAliado='" + getnombreAliado() + "'" +
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
        //Enemigo enemigo = p.getE();
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
        if (p.getTab().getPersonaje(nuevaX, nuevaY) instanceof Enemigo) {
            p.getP().pegar(p.getTab().getPersonaje(nuevaX, nuevaY));
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
