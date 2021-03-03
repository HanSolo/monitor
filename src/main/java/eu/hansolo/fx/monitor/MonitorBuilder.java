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

import eu.hansolo.fx.monitor.tools.ColorTheme;
import eu.hansolo.fx.monitor.tools.Timespan;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.List;


public class MonitorBuilder<B extends MonitorBuilder<B>> {
    private HashMap<String, Property> properties = new HashMap<>();


    // ******************** Constructors **************************************
    protected MonitorBuilder() {}


    // ******************** Methods *******************************************
    public static final MonitorBuilder create() {
        return new MonitorBuilder();
    }

    public final B lineWidth(final double lineWidth) {
        properties.put("lineWidth", new SimpleDoubleProperty(lineWidth));
        return (B)this;
    }

    public final B lineColor(final Color lineColor) {
        properties.put("lineColor", new SimpleObjectProperty<>(lineColor));
        return (B)this;
    }

    public final B backgroundColor(final Color backgroundColor) {
        properties.put("backgroundColor", new SimpleObjectProperty<>(backgroundColor));
        return (B)this;
    }

    public final B rasterColor(final Color rasterColor) {
        properties.put("rasterColor", new SimpleObjectProperty<>(rasterColor));
        return (B)this;
    }

    public final B textColor(final Color textColor) {
        properties.put("textColor", new SimpleObjectProperty<>(textColor));
        return (B)this;
    }

    public final B dotSize(final double dotSize) {
        properties.put("dotSize", new SimpleDoubleProperty(dotSize));
        return (B)this;
    }

    public final B data(final List<Number> data) {
        properties.put("data", new SimpleObjectProperty<>(data));
        return (B)this;
    }

    public final B rasterVisible(final boolean rasterVisible) {
        properties.put("rasterVisible", new SimpleBooleanProperty(rasterVisible));
        return (B)this;
    }

    public final B textVisible(final boolean textVisible) {
        properties.put("textVisible", new SimpleBooleanProperty(textVisible));
        return (B)this;
    }

    public final B glowVisible(final boolean glowVisible) {
        properties.put("glowVisible", new SimpleBooleanProperty(glowVisible));
        return (B)this;
    }

    public final B lineFading(final boolean lineFading) {
        properties.put("lineFading", new SimpleBooleanProperty(lineFading));
        return (B)this;
    }

    public final B crystalOverlayVisible(final boolean crystalOveralyVisible) {
        properties.put("crystalOverlayVisible", new SimpleBooleanProperty(crystalOveralyVisible));
        return (B)this;
    }

    public final B colorTheme(final ColorTheme colorTheme) {
        properties.put("colorTheme", new SimpleObjectProperty<>(colorTheme));
        return (B)this;
    }

    public final B timespan(final Timespan timespan) {
        properties.put("timespan", new SimpleObjectProperty<>(timespan));
        return (B)this;
    }

    public final B speedFactor(final double speedFactor) {
        properties.put("speedFactor", new SimpleDoubleProperty(speedFactor));
        return (B)this;
    }

    public final B noOfSegments(final int noOfSegments) {
        properties.put("noOfSegments", new SimpleIntegerProperty(noOfSegments));
        return (B)this;
    }

    public final B scaleFactorY(final double scaleFactorY) {
        properties.put("scaleFactorY", new SimpleDoubleProperty(scaleFactorY));
        return (B)this;
    }

    public final B prefSize(final double WIDTH, final double HEIGHT) {
        properties.put("prefSize", new SimpleObjectProperty<>(new Dimension2D(WIDTH, HEIGHT)));
        return (B)this;
    }
    public final B minSize(final double WIDTH, final double HEIGHT) {
        properties.put("minSize", new SimpleObjectProperty<>(new Dimension2D(WIDTH, HEIGHT)));
        return (B)this;
    }
    public final B maxSize(final double WIDTH, final double HEIGHT) {
        properties.put("maxSize", new SimpleObjectProperty<>(new Dimension2D(WIDTH, HEIGHT)));
        return (B)this;
    }

    public final B prefWidth(final double PREF_WIDTH) {
        properties.put("prefWidth", new SimpleDoubleProperty(PREF_WIDTH));
        return (B)this;
    }
    public final B prefHeight(final double PREF_HEIGHT) {
        properties.put("prefHeight", new SimpleDoubleProperty(PREF_HEIGHT));
        return (B)this;
    }

    public final B minWidth(final double MIN_WIDTH) {
        properties.put("minWidth", new SimpleDoubleProperty(MIN_WIDTH));
        return (B)this;
    }
    public final B minHeight(final double MIN_HEIGHT) {
        properties.put("minHeight", new SimpleDoubleProperty(MIN_HEIGHT));
        return (B)this;
    }

    public final B maxWidth(final double MAX_WIDTH) {
        properties.put("maxWidth", new SimpleDoubleProperty(MAX_WIDTH));
        return (B)this;
    }
    public final B maxHeight(final double MAX_HEIGHT) {
        properties.put("maxHeight", new SimpleDoubleProperty(MAX_HEIGHT));
        return (B)this;
    }

    public final B scaleX(final double SCALE_X) {
        properties.put("scaleX", new SimpleDoubleProperty(SCALE_X));
        return (B)this;
    }
    public final B scaleY(final double SCALE_Y) {
        properties.put("scaleY", new SimpleDoubleProperty(SCALE_Y));
        return (B)this;
    }

    public final B layoutX(final double LAYOUT_X) {
        properties.put("layoutX", new SimpleDoubleProperty(LAYOUT_X));
        return (B)this;
    }
    public final B layoutY(final double LAYOUT_Y) {
        properties.put("layoutY", new SimpleDoubleProperty(LAYOUT_Y));
        return (B)this;
    }

    public final B translateX(final double TRANSLATE_X) {
        properties.put("translateX", new SimpleDoubleProperty(TRANSLATE_X));
        return (B)this;
    }
    public final B translateY(final double TRANSLATE_Y) {
        properties.put("translateY", new SimpleDoubleProperty(TRANSLATE_Y));
        return (B)this;
    }

    public final B padding(final Insets INSETS) {
        properties.put("padding", new SimpleObjectProperty<>(INSETS));
        return (B)this;
    }


    public final Monitor build() {
        final Monitor monitor = new Monitor();
        for (String key : properties.keySet()) {
            if ("prefSize".equals(key)) {
                Dimension2D dim = ((ObjectProperty<Dimension2D>) properties.get(key)).get();
                monitor.setPrefSize(dim.getWidth(), dim.getHeight());
            } else if("minSize".equals(key)) {
                Dimension2D dim = ((ObjectProperty<Dimension2D>) properties.get(key)).get();
                monitor.setMinSize(dim.getWidth(), dim.getHeight());
            } else if("maxSize".equals(key)) {
                Dimension2D dim = ((ObjectProperty<Dimension2D>) properties.get(key)).get();
                monitor.setMaxSize(dim.getWidth(), dim.getHeight());
            } else if("prefWidth".equals(key)) {
                monitor.setPrefWidth(((DoubleProperty) properties.get(key)).get());
            } else if("prefHeight".equals(key)) {
                monitor.setPrefHeight(((DoubleProperty) properties.get(key)).get());
            } else if("minWidth".equals(key)) {
                monitor.setMinWidth(((DoubleProperty) properties.get(key)).get());
            } else if("minHeight".equals(key)) {
                monitor.setMinHeight(((DoubleProperty) properties.get(key)).get());
            } else if("maxWidth".equals(key)) {
                monitor.setMaxWidth(((DoubleProperty) properties.get(key)).get());
            } else if("maxHeight".equals(key)) {
                monitor.setMaxHeight(((DoubleProperty) properties.get(key)).get());
            } else if("scaleX".equals(key)) {
                monitor.setScaleX(((DoubleProperty) properties.get(key)).get());
            } else if("scaleY".equals(key)) {
                monitor.setScaleY(((DoubleProperty) properties.get(key)).get());
            } else if ("layoutX".equals(key)) {
                monitor.setLayoutX(((DoubleProperty) properties.get(key)).get());
            } else if ("layoutY".equals(key)) {
                monitor.setLayoutY(((DoubleProperty) properties.get(key)).get());
            } else if ("translateX".equals(key)) {
                monitor.setTranslateX(((DoubleProperty) properties.get(key)).get());
            } else if ("translateY".equals(key)) {
                monitor.setTranslateY(((DoubleProperty) properties.get(key)).get());
            } else if ("padding".equals(key)) {
                monitor.setPadding(((ObjectProperty<Insets>) properties.get(key)).get());
            } else if ("lineWidth".equals(key)) {
                monitor.setLineWidth(((DoubleProperty) properties.get(key)).get());
            } else if("lineColor".equals(key)) {
                monitor.setLineColor(((ObjectProperty<Color>) properties.get(key)).get());
            } else if("backgroundColor".equals(key)) {
                monitor.setBackgroundColor(((ObjectProperty<Color>) properties.get(key)).get());
            } else if("rasterColor".equals(key)) {
                monitor.setRasterColor(((ObjectProperty<Color>) properties.get(key)).get());
            } else if("textColor".equals(key)) {
                monitor.setTextColor(((ObjectProperty<Color>) properties.get(key)).get());
            } else if("dotSize".equals(key)) {
                monitor.setDotSize(((DoubleProperty) properties.get(key)).get());
            } else if ("data".equals(key)) {
                monitor.setData(((ObjectProperty<List<Number>>) properties.get(key)).get());
            } else if ("rasterVisible".equals(key)) {
                monitor.setRasterVisible(((BooleanProperty) properties.get(key)).get());
            } else if ("textVisible".equals(key)) {
                monitor.setTextVisible(((BooleanProperty) properties.get(key)).get());
            } else if ("glowVisible".equals(key)) {
                monitor.setGlowVisible(((BooleanProperty) properties.get(key)).get());
            } else if ("lineFading".equals(key)) {
                monitor.setLineFading(((BooleanProperty) properties.get(key)).get());
            } else if ("crystalOverlayVisible".equals(key)) {
                monitor.setCrystalOverlayVisible(((BooleanProperty) properties.get(key)).get());
            } else if ("timespan".equals(key)) {
                monitor.setTimespan(((ObjectProperty<Timespan>) properties.get(key)).get());
            } else if ("speedFactor".equals(key)) {
                monitor.setSpeedFactor(((DoubleProperty) properties.get(key)).get());
            } else if ("noOfSegments".equals(key)) {
                monitor.setNoOfSegments(((IntegerProperty) properties.get(key)).get());
            } else if ("colorTheme".equals(key)) {
                monitor.setColorTheme(((ObjectProperty<ColorTheme>) properties.get(key)).get());
            } else if ("scaleFactorY".equals(key)) {
                monitor.setScaleFactorY(((DoubleProperty) properties.get(key)).get());
            }
        }
        return monitor;
    }
}
