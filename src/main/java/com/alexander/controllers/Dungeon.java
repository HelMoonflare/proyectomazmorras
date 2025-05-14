package com.alexander.controllers;

import com.alexander.App;
import com.alexander.Interfaces.Observer;
import com.alexander.Model.Personaje;
import com.alexander.Model.Protagonista;
import com.alexander.Model.Enemigo;
import com.alexander.Model.GestorPersonajes;
import com.alexander.Model.Proveedor;
import com.alexander.Model.Tablero;
import com.alexander.Model.TipoCasilla;
import com.alexander.Model.TipoMov;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;

public class Dungeon implements Observer {

    @FXML
    Label Ltab;

    @FXML
    GridPane gridTablero;

    @FXML
    GridPane gridTableroPersonajes;

    @FXML
    StackPane stackPane;

    GestorPersonajes gp;

    @FXML
    public void initialize() {
        gp = Proveedor.getInstance().getGp();
        gp.subscribe(this);

        gridTablero.setHgap(0); // Sin espaciado horizontal
        gridTablero.setVgap(0); // Sin espaciado vertical
        gridTablero.setPadding(Insets.EMPTY); // Sin relleno
        gridTablero.setGridLinesVisible(false); // Opcional: Ocultar líneas de cuadrícula

        gridTableroPersonajes.setHgap(0); // Sin espaciado horizontal
        gridTableroPersonajes.setVgap(0); // Sin espaciado vertical
        gridTableroPersonajes.setPadding(Insets.EMPTY); // Sin relleno
        gridTableroPersonajes.setGridLinesVisible(false); // Opcional: Ocultar líneas de cuadrícula

        Proveedor.getInstance().getTab().LecturaInicioTablero(gp);
        int nFilas = Proveedor.getInstance().getTab().getNFilas();
        int nColumnas = Proveedor.getInstance().getTab().getNColumnas();

        gridTablero.getRowConstraints().clear();
        gridTablero.getColumnConstraints().clear();
        gridTableroPersonajes.getRowConstraints().clear();
        gridTableroPersonajes.getColumnConstraints().clear();

        for (int i = 0; i < nFilas; i++) {
            RowConstraints row = new RowConstraints();
            row.setMinHeight(30);
            row.setPrefHeight(30);
            row.setMaxHeight(30);
            gridTablero.getRowConstraints().add(row);
            gridTableroPersonajes.getRowConstraints().add(row); // Asegurar que ambos GridPane tengan las mismas filas
        }

        for (int i = 0; i < nColumnas; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setMinWidth(30);
            col.setPrefWidth(30);
            col.setMaxWidth(30);
            gridTablero.getColumnConstraints().add(col);
            gridTableroPersonajes.getColumnConstraints().add(col); // Asegurarse de que ambos GridPane tengan las mismas
                                                                   // columnas
        }

        // limpieza del StackPane antes de agregar nuevos elementos
        stackPane.getChildren().clear();

        // Agregar ambos GridPane al StackPane
        stackPane.getChildren().addAll(gridTablero, gridTableroPersonajes);

        generarMapa();
        GenerarMapaPersonajes();

        // Configurar eventos de teclado para mover al protagonista
        stackPane.setOnKeyPressed(event -> {
            Protagonista prota = (Protagonista) Proveedor.getInstance().getP();
            switch (event.getCode()) {
                case W:
                case UP:
                    prota.setDireccion(TipoMov.ARRIBA);
                    break;
                case S:
                case DOWN:
                    prota.setDireccion(TipoMov.ABAJO);
                    break;
                case A:
                case LEFT:
                    prota.setDireccion(TipoMov.IZQUIERDA);
                    break;
                case D:
                case RIGHT:
                    prota.setDireccion(TipoMov.DERECHA);
                    break;
                default:
                    return;
            }
            prota.moverse();
            GenerarMapaPersonajes(); // Actualizar la visualización del tablero
        });

        // Asegurarse de que el StackPane tenga el foco para capturar eventos de teclado
        stackPane.setFocusTraversable(true);
        stackPane.requestFocus();
    }

    public void GenerarMapaPersonajes() {
        Tablero tableroEnemigos = Proveedor.getInstance().getTab();
        gridTableroPersonajes.getChildren().clear();

        Image enemigo = new Image(App.class.getResourceAsStream("/com/alexander/data/enemigo.png"), 50, 50, false,
                false);
        Image prota = new Image(App.class.getResourceAsStream("/com/alexander/data/SpriteProta.png"), 50, 50, false,
                false);

        for (int fila = 0; fila < tableroEnemigos.getNFilas(); fila++) {
            for (int col = 0; col < tableroEnemigos.getNColumnas(); col++) {
                if (tableroEnemigos.getTipoCasilla(fila, col) == TipoCasilla.Suelo) {
                    Personaje personaje = tableroEnemigos.getPersonaje(fila, col);
                    if (personaje instanceof Protagonista) {
                        gridTableroPersonajes.add(new ImageView(prota), col, fila);
                    } else if (personaje instanceof Enemigo) {
                        gridTableroPersonajes.add(new ImageView(enemigo), col, fila);
                    }
                }
            }
        }
    }

    public void generarMapa() {
        Tablero tablero = new Tablero();
        tablero.LecturaInicioTablero(gp);
        tablero.getTablero();

        gridTablero.getChildren().clear();

        Image camino = new Image(App.class.getResourceAsStream("data/SpriteCamino.jpg"), 50, 50, false, false);
        Image muro = new Image(App.class.getResourceAsStream("data/SpriteMuro.png"), 50, 50, false, false);
        Image lava = new Image(App.class.getResourceAsStream("data/SpriteLava.jpg"), 50, 50, false, false);

        for (int fila = 0; fila < tablero.getNFilas(); fila++) {
            for (int col = 0; col < tablero.getNColumnas(); col++) {
                if (tablero.getTipoCasilla(fila, col) == TipoCasilla.Pared) {
                    gridTablero.add(new ImageView(muro), col, fila);
                } else if (tablero.getTipoCasilla(fila, col) == TipoCasilla.Trampa) {
                    gridTablero.add(new ImageView(lava), col, fila);
                } else {
                    gridTablero.add(new ImageView(camino), col, fila);
                }
            }
        }
    }

    @Override
    public void onChange() {

    }

}