package com.alexander.Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

import com.alexander.App;

public class Tablero {
    private Casilla[][] tablero;

    /**
     * Lee el archivo de inicio y genera el tablero.
     * 
     * @param gp Gestor de personajes.
     * @return Matriz de casillas del tablero.
     */
    public Casilla[][] LecturaInicioTablero(GestorPersonajes gp) {
        tablero = new Casilla[13][13];
        String[] columnas;
        int filas = 0;
        Random random = new Random();
        ArrayList<Personaje> enemigosCopia = new ArrayList<>((ArrayList<Personaje>) gp.getListaPersonaje());
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
                                throw new IllegalStateException(
                                        "El protagonista no está inicializado en GestorPersonajes.");
                            }
                            tablero[filas][i] = new Casilla(TipoCasilla.Suelo, gp.getProta());
                            gp.getProta().setCordX(filas);
                            gp.getProta().setCordY(i);
                            break;
                        case 3:
                            int aleatorio = random.nextInt(enemigosCopia.size());
                            if (enemigosCopia.get(aleatorio) instanceof Protagonista) {
                                enemigosCopia.remove(aleatorio);
                            } else {
                                Enemigo enemigo = (Enemigo) enemigosCopia
                                        .remove(aleatorio);
                                tablero[filas][i] = new Casilla(TipoCasilla.Suelo, enemigo);
                                enemigo.setCordX(filas);
                                enemigo.setCordY(i);
                            }
                            break;
                        case 4:
                            tablero[filas][i] = new Casilla(TipoCasilla.Maldicion, null);
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

    /**
     * Actualiza la casilla con el personaje dado.
     * 
     * @param pj Personaje a colocar.
     * @param x  Coordenada X.
     * @param y  Coordenada Y.
     */
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
        tablero[pj.getCordX()][pj.getCordY()].setPersonaje(null);
        tablero[x][y].setPersonaje(pj);
        pj.setCordX(x);
        pj.setCordY(y);

    }

    /**
     * Obtiene el tipo de casilla en las coordenadas dadas.
     * 
     * @param x Coordenada X.
     * @param y Coordenada Y.
     * @return Tipo de casilla.
     */
    public TipoCasilla getTipoCasilla(int x, int y) {
        return tablero[x][y].getTipo();
    }

    /**
     * Obtiene el personaje en las coordenadas dadas.
     * 
     * @param x Coordenada X.
     * @param y Coordenada Y.
     * @return Personaje en la casilla.
     */
    public Personaje getPersonaje(int x, int y) {
        return tablero[x][y].getPersonaje();
    }

    /**
     * Verifica si la casilla está vacía.
     * 
     * @param x Coordenada X.
     * @param y Coordenada Y.
     * @return true si la casilla está vacía, false en caso contrario.
     */
    public boolean EstaCasillaEstaVacia(int x, int y) {
        return tablero[x][y].getPersonaje() == null;
    }

    public boolean momivimientoValido(int x, int y) {
        return (x >= 0 && y >= 0) && (x <= getAncho() && y <= getAlto()) && (getTipoCasilla(x, y) != TipoCasilla.Pared);
    }

    public int getNFilas() {
        return tablero.length;
    }

    public int getNColumnas() {
        return tablero[0].length;
    }

    public void personajeMuerto(int x, int y) {
        tablero[x][y].setPersonaje(null);
    }

    /**
     * Método para obtener el tablero.
     * 
     * @return Tablero.
     */
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
