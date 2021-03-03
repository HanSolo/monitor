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
package eu.hansolo.fx.monitor.tools;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.Random;


public class Helper {
    public static final double clamp(final double min, final double max, final double value) {
        if (value < min) { return min; }
        if (value > max) { return max; }
        return value;
    }

    public static final void enableNode(final Node node, final boolean enable) {
        node.setManaged(enable);
        node.setVisible(enable);
    }

    public static final Image createNoiseImage(final double width, final double height, final Color darkColor, final Color brightColor, final double alphaVariationInPercent) {
        if (Double.compare(width, 0) <= 0 || Double.compare(height, 0) <= 0) return null;
        int                 w              = (int) width;
        int                 h              = (int) height;
        double              alphaVariation = clamp(0.0, 100.0, alphaVariationInPercent);
        final WritableImage image          = new WritableImage(w, h);
        final PixelWriter   pixelWriter    = image.getPixelWriter();
        final Random        rndBlackWhite  = new Random();
        final Random        rndAlpha       = new Random();
        final double        alphaStart     = alphaVariation / 100 / 2;
        final double        variation      = alphaVariation / 100;
        for (int y = 0 ; y < h ; y++) {
            for (int x = 0 ; x < w ; x++) {
                final Color  NOISE_COLOR = rndBlackWhite.nextBoolean() ? brightColor : darkColor;
                final double NOISE_ALPHA = clamp(0.0, 1.0, alphaStart + rndAlpha.nextDouble() * variation);
                pixelWriter.setColor(x, y, Color.color(NOISE_COLOR.getRed(), NOISE_COLOR.getGreen(), NOISE_COLOR.getBlue(), NOISE_ALPHA));
            }
        }
        return image;
    }
}
