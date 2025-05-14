package com.alexander.Model;

import java.util.ArrayList;
import java.util.Random;

import com.alexander.Interfaces.Observer;

public class Enemigo extends Personaje implements Observer {
    private String nombreEnemigo;
    private int percepcion;
    ArrayList<Observer> observers;

    public Enemigo(int velocidad, int vitalidad, int fuerza, int percepcion, String nombreEnemigo) {
        super(velocidad, vitalidad, fuerza);
        this.nombreEnemigo = nombreEnemigo;
        this.percepcion = percepcion;
        this.observers = new ArrayList<>(); // Inicialización de la lista de observers
    }

    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        observers.forEach(item -> item.onChange());
    }

    public String getNombreEnemigo() {
        return this.nombreEnemigo;
    }

    public void setNombreEnemigo(String nombreEnemigo) {
        this.nombreEnemigo = nombreEnemigo;
    }

    public int getPercepcion() {
        return this.percepcion;
    }

    public void setPercepcion(int percepcion) {
        this.percepcion = percepcion;
    }

    @Override
    public String toString() {
        return "{" +
                " nombreEnemigo='" + getNombreEnemigo() + "'" +
                super.toString() +
                ", percepcion='" + getPercepcion() + "'";
    }

    @Override
    public void moverse() {
        Random r = new Random();
        Proveedor p = Proveedor.getInstance();
        Tablero tab = p.getTab();
        Protagonista prota = p.getP();

        boolean seguir = true;
        float Calculo1 = this.CalculoAlgoritmo(this.getCordX(), this.getCordY(), prota.getCordX(),
                prota.getCordY());
        int nuevaX = this.getCordX();
        int nuevaY = this.getCordY();

        if (Calculo1 <= this.getPercepcion()) {
            if (this.getCordX() - 1 >= 0 && this.getCordX() - 1 < tab.getAncho() && this.getCordY() >= 0
                    && this.getCordY() < tab.getAlto()) {
                if (tab.getTipoCasilla(this.getCordX() - 1, this.getCordY()) != TipoCasilla.Pared) {
                    if (Calculo1 < this.CalculoAlgoritmo(this.getCordX() - 1, this.getCordY(), prota.getCordX(),
                            prota.getCordY())) {
                        Calculo1 = this.CalculoAlgoritmo(this.getCordX() - 1, this.getCordY(), prota.getCordX(),
                                prota.getCordY());
                        nuevaX = this.getCordX() - 1;
                        nuevaY = this.getCordY();
                    }
                }
            }
            // Validar otras direcciones
            if (this.getCordX() + 1 >= 0 && this.getCordX() + 1 < tab.getAncho() && this.getCordY() >= 0
                    && this.getCordY() < tab.getAlto()) {
                if (tab.getTipoCasilla(this.getCordX() + 1, this.getCordY()) != TipoCasilla.Pared) {
                    if (Calculo1 < this.CalculoAlgoritmo(this.getCordX() + 1, this.getCordY(), prota.getCordX(),
                            prota.getCordY())) {
                        Calculo1 = this.CalculoAlgoritmo(this.getCordX() + 1, this.getCordY(), prota.getCordX(),
                                prota.getCordY());
                        nuevaX = this.getCordX() + 1;
                        nuevaY = this.getCordY();
                    }
                }
            }
            if (this.getCordX() >= 0 && this.getCordX() < tab.getAncho() && this.getCordY() - 1 >= 0
                    && this.getCordY() - 1 < tab.getAlto()) {
                if (tab.getTipoCasilla(this.getCordX(), this.getCordY() - 1) != TipoCasilla.Pared) {
                    if (Calculo1 < this.CalculoAlgoritmo(this.getCordX(), this.getCordY() - 1, prota.getCordX(),
                            prota.getCordY())) {
                        Calculo1 = this.CalculoAlgoritmo(this.getCordX(), this.getCordY() - 1, prota.getCordX(),
                                prota.getCordY());
                        nuevaX = this.getCordX();
                        nuevaY = this.getCordY() - 1;
                    }
                }
            }
            if (this.getCordX() >= 0 && this.getCordX() < tab.getAncho() && this.getCordY() + 1 >= 0
                    && this.getCordY() + 1 < tab.getAlto()) {
                if (tab.getTipoCasilla(this.getCordX(), this.getCordY() + 1) != TipoCasilla.Pared) {
                    if (Calculo1 < this.CalculoAlgoritmo(this.getCordX(), this.getCordY() + 1, prota.getCordX(),
                            prota.getCordY())) {
                        Calculo1 = this.CalculoAlgoritmo(this.getCordX(), this.getCordY() + 1, prota.getCordX(),
                                prota.getCordY());
                        nuevaX = this.getCordX();
                        nuevaY = this.getCordY() + 1;
                    }
                }
            }

            // Actualizar posición del enemigo
            tab.actualizarCasilla(this, nuevaX, nuevaY);
        } else {
            while (seguir) {
                int opRandom = r.nextInt(4);
                switch (opRandom) {
                    case 0: // Arriba
                        nuevaX = this.getCordX() - 1;
                        nuevaY = this.getCordY();
                        break;
                    case 1: // Abajo
                        nuevaX = this.getCordX() + 1;
                        nuevaY = this.getCordY();
                        break;
                    case 2: // Izquierda
                        nuevaX = this.getCordX();
                        nuevaY = this.getCordY() - 1;
                        break;
                    case 3: // Derecha
                        nuevaX = this.getCordX();
                        nuevaY = this.getCordY() + 1;
                        break;
                }

                if (nuevaX >= 0 && nuevaX < tab.getAncho() && nuevaY >= 0 && nuevaY < tab.getAlto() &&
                        tab.EstaCasillaEstaVacia(nuevaX, nuevaY) &&
                        tab.getTipoCasilla(nuevaX, nuevaY) == TipoCasilla.Suelo
                        || tab.getTipoCasilla(nuevaX, nuevaY) == TipoCasilla.Trampa) {
                    tab.actualizarCasilla(null, this.getCordX(), this.getCordY());
                    this.setCordX(nuevaX);
                    this.setCordY(nuevaY);
                    tab.actualizarCasilla(this, nuevaX, nuevaY);
                    seguir = false;
                    this.notifyObservers();
                }
            }
        }
    }

    @Override
    public void onChange() {
        // Lógica para reaccionar a los cambios del protagonista
        this.moverse();
    }

    public float CalculoAlgoritmo(int x1, int y1, int x2, int y2) {
        // Calcular la distancia entre los dos puntos
        float distancia = (float) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        return distancia;
    }

}
