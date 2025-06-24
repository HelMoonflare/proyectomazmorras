package com.alexander.Model;

import java.util.ArrayList;

import com.alexander.Interfaces.Observer;

public class Proveedor {
    private static Proveedor instance;
    private GestorPersonajes gp;
    private Tablero tab;
    private Protagonista p;
    private Enemigo e;
    ArrayList<Observer> observers;
    private boolean finJuego = false;

    private Proveedor() {
        // Crear y asignar el protagonista con valores predeterminados
        this.p = new Protagonista(10, 100, 15, "Héroe");
        this.gp = new GestorPersonajes(p);
        this.tab = new Tablero();
        gp.setProta(p);
        tab.LecturaInicioTablero(gp);
        observers = new ArrayList<>();
    }

    /**
     * Método para suscribir un observador.
     * 
     * @param observer Observador a suscribir.
     */
    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    /**
     * Método para eliminar un observador.
     * 
     * @param observer Observador a eliminar.
     */
    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Método para notificar a los observadores.
     */
    public void notifyObservers() {
        observers.forEach(item -> item.onChange());
    }

    public static Proveedor getInstance() {
        if (instance == null) {
            instance = new Proveedor();
        }
        return instance;
    }

    public GestorPersonajes getGp() {
        return gp;
    }

    public void setGp(GestorPersonajes gp) {
        this.gp = gp;
    }

    public Tablero getTab() {
        return tab;
    }

    public void setTab(Tablero tab) {
        this.tab = tab;
    }

    public Protagonista getP() {
        return p;
    }

    public void setP(Protagonista p) {
        this.p = p;
    }

    public Enemigo getE() {
        return e;
    }

    public void setE(Enemigo e) {
        this.e = e;
    }
    
    public boolean getFinJuego() {
        return this.finJuego;
    }

    /**
     * Mueve los personajes en el tablero.
     */
    public void MoverPersonajes() {
        gp.ordenarPersonajes();
        for (int i = 0; i < gp.getListaPersonaje().size(); i++) {
            Personaje p = gp.getListaPersonaje().get(i);
            p.moverse();
        }
        for (int i = 0; i < gp.getListaPersonaje().size(); i++) {
            if (gp.getListaPersonaje().get(i).getVitalidad() <= 0) {
                tab.personajeMuerto(gp.getListaPersonaje().get(i).getCordX(), gp.getListaPersonaje().get(i).getCordY());
                gp.eliminarEnemigo(gp.getListaPersonaje().get(i));
                i--;
            }
        }
        if (p.getVitalidad() <= 0 || gp.getListaPersonaje().size() == 1) {
            finJuego = true;
        }
        notifyObservers();
    }

}
