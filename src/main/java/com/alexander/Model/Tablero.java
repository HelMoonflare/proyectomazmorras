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
        ArrayList<Personaje> enemigosCopia = new ArrayList<>((ArrayList<Personaje>) gp.getListaPersonaje());
        // Verificar si el protagonista está inicializado antes de usarlo
        if (gp.getProta() == null) {
            throw new IllegalStateException("El protagonista no está inicializado en GestorPersonajes.");
        }
        // Asegurar que hay al menos un Cobarde en la lista
        /*
         * boolean hayCobarde = false;
         * for (Personaje p : enemigosCopia) {
         * if (p instanceof Cobarde) {
         * hayCobarde = true;
         * }
         * }
         * if (!hayCobarde) {
         * Cobarde cobardeExtra = new Cobarde(5, 2, 3, 3, "Cobarde");
         * gp.insertarPersonaje(cobardeExtra);
         * enemigosCopia.add(cobardeExtra);
         * System.out.println("Cobarde añadido automáticamente a la lista de personajes"
         * );
         * }
         */

        boolean haySanador = false;
        for (Personaje p : enemigosCopia) {
            if (p instanceof Sanador) {
                haySanador = true;
            }
        }
        if (!haySanador) {
            Sanador sanadorExtra = new Sanador(5, 2, 3, 3, "Sanador");
            gp.insertarPersonaje(sanadorExtra);
            enemigosCopia.add(sanadorExtra);
            System.out.println("Sanador añadido automáticamente a la lista de personajes");
        }

        /*
         * boolean hayAliado = false;
         * for (Personaje p : enemigosCopia) {
         * if (p instanceof Aliado) {
         * hayAliado = true;
         * }
         * }
         * if (!hayAliado) {
         * Aliado aliadoExtra = new Aliado(5, 2, 3, 3, "Aliado");
         * gp.insertarPersonaje(aliadoExtra);
         * enemigosCopia.add(aliadoExtra);
         * System.out.println("Aliado añadido automáticamente a la lista de personajes"
         * );
         * }
         */

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
                            if (gp.getProta() == null) {
                                throw new IllegalStateException(
                                        "El protagonista no está inicializado en GestorPersonajes.");
                            }
                            tablero[filas][i] = new Casilla(TipoCasilla.Suelo, gp.getProta());
                            gp.getProta().setCordX(filas);
                            gp.getProta().setCordY(i);
                            break;
                        case 3:
                            Enemigo enemigo = null;
                            for (int idx = 0; idx < enemigosCopia.size(); idx++) {
                                if (enemigosCopia.get(idx) instanceof Enemigo
                                        && !(enemigosCopia.get(idx) instanceof Cobarde)) {
                                    enemigo = (Enemigo) enemigosCopia.remove(idx);
                                    break;
                                }
                            }
                            if (enemigo != null) {
                                tablero[filas][i] = new Casilla(TipoCasilla.Suelo, enemigo);
                                enemigo.setCordX(filas);
                                enemigo.setCordY(i);
                            } else {
                                tablero[filas][i] = new Casilla(TipoCasilla.Suelo, null);
                            }
                            break;
                        case 4:
                            tablero[filas][i] = new Casilla(TipoCasilla.Curacion, null);
                            break;
                        case 5:
                            Cobarde cobarde = null;
                            for (int idx = 0; idx < enemigosCopia.size(); idx++) {
                                if (enemigosCopia.get(idx) instanceof Cobarde) {
                                    cobarde = (Cobarde) enemigosCopia.remove(idx);
                                    break;
                                }
                            }
                            if (cobarde != null) {
                                tablero[filas][i] = new Casilla(TipoCasilla.Suelo, cobarde);
                                cobarde.setCordX(filas);
                                cobarde.setCordY(i);
                                System.out.println("Cobarde colocado en (" + filas + "," + i + ")");
                            } else {
                                tablero[filas][i] = new Casilla(TipoCasilla.Suelo, null);
                            }
                            break;

                        case 6:
                            Sanador sanador = null;
                            for (int idx = 0; idx < enemigosCopia.size(); idx++) {
                                if (enemigosCopia.get(idx) instanceof Sanador) {
                                    sanador = (Sanador) enemigosCopia.remove(idx);
                                    break;
                                }
                            }
                            if (sanador != null) {
                                tablero[filas][i] = new Casilla(TipoCasilla.Suelo, sanador);
                                sanador.setCordX(filas);
                                sanador.setCordY(i);
                                System.out.println("Sanador colocado en (" + filas + "," + i + ")");
                            } else {
                                tablero[filas][i] = new Casilla(TipoCasilla.Suelo, null);
                            }
                            break;

                        case 7:
                            Aliado aliado = null;
                            for (int idx = 0; idx < enemigosCopia.size(); idx++) {
                                if (enemigosCopia.get(idx) instanceof Aliado) {
                                    aliado = (Aliado) enemigosCopia.remove(idx);
                                    break;
                                }
                                if (aliado != null) {
                                    tablero[filas][i] = new Casilla(TipoCasilla.Suelo, aliado);
                                    aliado.setCordX(filas);
                                    aliado.setCordY(i);
                                    System.out.println("Aliado colocado en (" + filas + "," + i + ")");
                                } else {
                                    tablero[filas][i] = new Casilla(TipoCasilla.Suelo, null);
                                }
                            }
                            break;
                        default:
                            tablero[filas][i] = new Casilla(TipoCasilla.Suelo, null);
                            break;
                    }
                }
                filas++;
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return tablero;
    }

    public void actualizarCasilla(Personaje pj, int x, int y) {
        if (x < 0 || x >= tablero.length || y < 0 || y >= tablero[0].length) {
            System.err.println("Error: Coordenadas fuera de los límites del tablero.");
            return;
        }
        if (pj == null) {
            tablero[x][y].setPersonaje(null);
            return;
        }
        tablero[pj.getCordX()][pj.getCordY()].setPersonaje(null);
        tablero[x][y].setPersonaje(pj);
        pj.setCordX(x);
        pj.setCordY(y);

        // Método para casillas especiales
        if (tablero[x][y].getTipo() == TipoCasilla.Curacion) {
            System.out.println("¡Curación activada!");
            ArrayList<Personaje> todos = new ArrayList<>();
            if (Proveedor.getInstance().getP().getVitalidad() > 0) {
                todos.add(Proveedor.getInstance().getP());
            }
            for (Personaje personaje : Proveedor.getInstance().getGp().getListaPersonaje()) {
                if (personaje.getVitalidad() > 0 && !todos.contains(personaje)) {
                    todos.add(personaje);
                }
            }
            if (!todos.isEmpty()) {
                Random r = new Random();
                Personaje curado = todos.get(r.nextInt(todos.size()));
                int vidaActual = curado.getVitalidad();
                curado.setVitalidad((int) (vidaActual + vidaActual * 0.25));
                System.out.println("¡" + curado + " ha sido curado! Vida actual: " + curado.getVitalidad());
            }
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

    public boolean movimientoValido(int x, int y) {
        return (x >= 0 && y >= 0) && (x < getNFilas() && y < getNColumnas())
                && (getTipoCasilla(x, y) != TipoCasilla.Pared);
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
