package com.alexander.Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import com.alexander.App;

public class Tablero {
    private Casilla[][] tablero;


    public Casilla[][] LecturaInicioTablero(GestorPersonajes gp) {
        tablero = new Casilla[13][13];
        String[] columnas;
        int filas = 0;
        // Verificar si el protagonista está inicializado antes de usarlo
        if (gp.getProta() == null) {
            throw new IllegalStateException("El protagonista no está inicializado en GestorPersonajes.");
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(new File(App.class.getResource("data/tablero.DARKEST").toURI())),
                StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                columnas = linea.split(" ");
                for (int i = 0; i < columnas.length; i++) {
                    switch (Integer.parseInt(columnas[i])) {
                        case 0:
                            tablero[filas][i] = new Casilla(TipoCasilla.Suelo, null);
                            break;
                        case 1:
                            tablero[filas][i] = new Casilla(TipoCasilla.Pared, null);
                            break;
                        case 2:
                            // Validar que el protagonista esté inicializado antes de usarlo
                            if (gp.getProta() == null) {
                                throw new IllegalStateException("El protagonista no está inicializado en GestorPersonajes.");
                            }
                            tablero[filas][i] = new Casilla(TipoCasilla.Suelo, gp.getProta());
                            gp.getProta().setCordX(i);
                            gp.getProta().setCordY(filas);
                            break;
                        case 3:
                            tablero[filas][i] = new Casilla(TipoCasilla.Suelo, gp.getEnemigo());
                            gp.getEnemigo().setCordX(i);
                            gp.getEnemigo().setCordY(filas);
                            break;
                        case 4:
                            tablero[filas][i] = new Casilla(TipoCasilla.Trampa, null);
                            break;
                        default:

                            break;
                    }
                }
                filas++;

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e1) {
          
            e1.printStackTrace();
        }
        return tablero;
    }

    public void actualizarCasilla(Personaje pj, int x, int y) {
        // Validar que las coordenadas estén dentro de los límites del tablero
        if (x < 0 || x >= tablero.length || y < 0 || y >= tablero[0].length) {
            System.err.println("Error: Coordenadas fuera de los límites del tablero.");
            return;
        }

        // Si pj es null, limpia la casilla
        if (pj == null) {
            tablero[x][y].setPersonaje(null);
            return;
        }

        // Actualiza la casilla del personaje en el tablero
        if (tablero[x][y].getTipo() == TipoCasilla.Suelo && tablero[x][y].getPersonaje() == null) {
            tablero[pj.getCordX()][pj.getCordY()].setPersonaje(null);
            tablero[x][y].setPersonaje(pj);
            pj.setCordX(x);
            pj.setCordY(y);
        }
    }

    public TipoCasilla getTipoCasilla(int x, int y) {
        return tablero[x][y].getTipo();
    }

    public Personaje getPersonaje(int x, int y) {
        return tablero[x][y].getPersonaje();
    }

    public boolean EstaCasillaEstaVacia(int x, int y) {
        return tablero[x][y].getPersonaje() == null;

    }

    public int getNFilas(){
        return tablero.length;
    }

    public int getNColumnas() {
        return tablero[0].length;
    }

    public Casilla[][] getTablero() {
        return tablero;
    }

    public int getAncho() {
        return tablero.length > 0 ? tablero[0].length : 0;
    }

    public int getAlto() {
        return tablero.length;
    }
}
