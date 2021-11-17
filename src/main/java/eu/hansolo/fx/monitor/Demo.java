/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Copyright 2020-2021 Gerrit Grunwald.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 package eu.hansolo.fx.monitor;

 import eu.hansolo.fx.monitor.tools.EcgData;
 import eu.hansolo.fx.monitor.tools.Theme;
 import eu.hansolo.fx.monitor.tools.Timespan;
 import javafx.application.Application;
 import javafx.application.Platform;
 import javafx.beans.value.ChangeListener;
 import javafx.beans.value.ObservableValue;
 import javafx.collections.FXCollections;
 import javafx.collections.ObservableList;
 import javafx.geometry.Insets;
 import javafx.scene.control.CheckBox;
 import javafx.scene.control.ComboBox;
 import javafx.scene.control.Label;
 import javafx.scene.control.Slider;
 import javafx.scene.layout.GridPane;
 import javafx.scene.layout.VBox;
 import javafx.scene.paint.Color;
 import javafx.stage.Stage;
 import javafx.scene.layout.StackPane;
 import javafx.scene.Scene;

 import java.util.List;
 import java.util.Random;
 import java.util.concurrent.Executors;
 import java.util.concurrent.ScheduledExecutorService;
 import java.util.concurrent.TimeUnit;


 /**
  * User: hansolo
  * Date: 18.12.20
  * Time: 09:54
  */
 public class Demo extends Application {
     private static final Random                   RND = new Random();
     private              ScheduledExecutorService executorService;
     private              Runnable                 sinTask;
     private              Monitor                  monitor;
     private              GridPane                 grid;
     private              double                   t;
     private              double                   step;

     @Override public void init() {
         monitor = MonitorBuilder.create()
                                 //.lineColor(Color.BLUE)
                                 //.backgroundColor(Color.rgb(51, 51, 51))
                                 //.rasterColor(Color.RED)
                                 //.textColor(Color.YELLOW)
                                 .lineWidth(2)
                                 .dotSize(4)
                                 .rasterVisible(true)
                                 .textVisible(true)
                                 .glowVisible(true)
                                 .crystalOverlayVisible(true)
                                 .lineFading(true)
                                 .timespan(Timespan.FIVE_SECONDS)
                                 .colorTheme(Theme.GREEN)
                                 .speedFactor(1)
                                 .noOfSegments(150)
                                 //.scaleFactorY(0.4)
                                 .data(EcgData.ECG_DATA)
                                 .build();

         //monitor.setRasterColorToLineColor();
         //monitor.setTextColorToLineColor();

         monitor.setOnMouseClicked(e -> {
            if (monitor.isRunning()) {
                monitor.stop();
            } else {
                monitor.start();
            }
        });

         t               = 0;
         step            = (Math.PI * 2.0) / 100;
         sinTask         = () -> {
             Platform.runLater(() -> {
                 monitor.addDataPoint(Math.sin(t) * 100);
             });
             t += step;
             if (t >= 2 * Math.PI) { t = 0; }
         };
         executorService = Executors.newScheduledThreadPool(1);

         Label                 themeLabel       = new Label("Theme");
         ObservableList<Theme> themes           = FXCollections.observableArrayList(List.of(Theme.values()));
         ComboBox<Theme>       themesComboBox   = new ComboBox<>(themes);

         Label                    timespanLabel = new Label("Timespan");
         ObservableList<Timespan> timespans     = FXCollections.observableArrayList(List.of(Timespan.values()));
         ComboBox<Timespan> timespanComboBox    = new ComboBox<>(timespans);

         CheckBox glowVisibleCheckBox           = new CheckBox("Glow");
         CheckBox rasterVisibleCheckBox         = new CheckBox("Raster");
         CheckBox textVisibleCheckBox           = new CheckBox("Text");
         CheckBox crystalOverlayVisibleCheckBox = new CheckBox("Crystal overlay");
         CheckBox fadingLineCheckBox            = new CheckBox("Fading line");
         Label    noOfSegmentsLabel             = new Label("Length");
         Slider   noOfSegmentsSlider            = new Slider(0, 250, Monitor.DEFAULT_NO_OF_SEGMENTS);
         Label    dotSizeLabel                  = new Label("Dot size");
         Slider   dotSizeSlider                 = new Slider(1, 5, Monitor.DEFAULT_DOT_SIZE);
         Label    lineWidthLabel                = new Label("Line width");
         Slider   lineWidthSlider               = new Slider(1, 5, Monitor.DEFAULT_LINE_WIDTH);
         lineWidthSlider.setBlockIncrement(0.5);
         Label    scaleFactorYLabel             = new Label("Scale factor y");
         Slider   scaleFactorYSlider            = new Slider(0.05, 10, Monitor.DEFAULT_SCALE_FACTOR_Y);
         scaleFactorYSlider.setBlockIncrement(0.05);
         Label    speedFactorLabel              = new Label("Speed factor");
         Slider   speedFactorSlider             = new Slider(0.1, 10, Monitor.DEFAULT_SPEED_FACTOR);
         speedFactorSlider.setBlockIncrement(0.1);

         timespanComboBox.getSelectionModel().select(Timespan.FIVE_SECONDS);
         themesComboBox.getSelectionModel().select(Theme.GREEN);
         glowVisibleCheckBox.setSelected(true);
         rasterVisibleCheckBox.setSelected(true);
         textVisibleCheckBox.setSelected(true);
         crystalOverlayVisibleCheckBox.setSelected(true);
         fadingLineCheckBox.setSelected(true);

         grid = new GridPane();
         grid.setHgap(20);
         grid.setVgap(10);

         grid.add(new VBox(themeLabel, themesComboBox), 0, 0);

         grid.add(new VBox(timespanLabel, timespanComboBox), 0, 1);

         grid.add(glowVisibleCheckBox, 0, 2);
         grid.add(rasterVisibleCheckBox, 0, 3);
         grid.add(textVisibleCheckBox, 0, 4);
         grid.add(crystalOverlayVisibleCheckBox, 0, 5);
         grid.add(fadingLineCheckBox, 0, 6);

         grid.add(new VBox(noOfSegmentsLabel, noOfSegmentsSlider), 1, 0);

         grid.add(new VBox(dotSizeLabel, dotSizeSlider), 1, 1);

         grid.add(new VBox(lineWidthLabel, lineWidthSlider), 1, 2);

         grid.add(new VBox(scaleFactorYLabel, scaleFactorYSlider), 1, 3);

         grid.add(new VBox(speedFactorLabel, speedFactorSlider), 1, 4);

         // Register listeners
         themesComboBox.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> monitor.setColorTheme(nv));
         timespanComboBox.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> monitor.setTimespan(nv));
         glowVisibleCheckBox.selectedProperty().addListener((o, ov, nv) -> monitor.setGlowVisible(nv));
         rasterVisibleCheckBox.selectedProperty().addListener((o, ov, nv) -> monitor.setRasterVisible(nv));
         textVisibleCheckBox.selectedProperty().addListener((o, ov, nv) -> monitor.setTextVisible(nv));
         crystalOverlayVisibleCheckBox.selectedProperty().addListener((o, ov, nv) -> monitor.setCrystalOverlayVisible(nv));
         fadingLineCheckBox.selectedProperty().addListener((o, ov, nv) -> monitor.setLineFading(nv));
         noOfSegmentsSlider.valueProperty().addListener((o, ov, nv) -> monitor.setNoOfSegments(nv.intValue()));
         dotSizeSlider.valueProperty().addListener((o, ov, nv) -> monitor.setDotSize(nv.doubleValue()));
         lineWidthSlider.valueProperty().addListener((o, ov, nv) -> monitor.setLineWidth(nv.doubleValue()));
         scaleFactorYSlider.valueProperty().addListener((o, ov, nv) -> monitor.setScaleFactorY(nv.doubleValue()));
         speedFactorSlider.valueProperty().addListener((o, ov, nv) -> monitor.setSpeedFactor(nv.doubleValue()));
     }

     @Override public void start(Stage stage) {
         StackPane monitorPane = new StackPane(monitor);
         VBox pane = new VBox(10, monitorPane, grid);
         pane.setPadding(new Insets(10));

         Scene scene = new Scene(pane);

         scene.setOnKeyPressed(e -> monitor.addDataPoint(RND.nextInt(200) - 100));
         scene.setOnKeyReleased(e -> monitor.addDataPoint(0));

         stage.setTitle("JavaFX Monitor");
         stage.setScene(scene);
         stage.show();

         // Start the sinus curve generator
         executorService.scheduleAtFixedRate(sinTask, 0, 5, TimeUnit.MILLISECONDS);
     }

     @Override public void stop() {
         System.exit(0);
     }

     public static void main(String[] args) {
         launch(args);
     }
 }
