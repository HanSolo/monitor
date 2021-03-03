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

import javafx.scene.paint.Color;


public enum Theme implements ColorTheme {
    BLUE(Color.rgb(54, 162, 218), Color.rgb(216, 252, 254), Color.BLACK, Color.BLACK),
    BLUE_BLACK(Color.BLACK, Color.rgb(66, 249, 252), Color.rgb(25, 126, 40), Color.WHITE),
    DARK_GREEN(Color.rgb(35, 162, 9), Color.rgb(93, 243, 128), Color.BLACK, Color.BLACK),
    GREEN(Color.rgb(30, 132, 119), Color.rgb(177, 246, 238), Color.BLACK, Color.BLACK),
    GREEN_BLACK(Color.rgb(159, 173, 151), Color.BLACK, Color.BLACK, Color.BLACK),
    GREEN_GRAY(Color.rgb(44, 48, 47), Color.rgb(111, 227, 179), Color.rgb(95, 85, 81), Color.rgb(200, 200, 200)),
    GREEN_RED(Color.rgb(105, 33, 29), Color.rgb(139, 247, 236), Color.rgb(250, 152, 133), Color.rgb(250, 152, 133)),
    LIGHT_GREEN(Color.rgb(84, 184, 168), Color.rgb(151, 255, 242), Color.BLACK, Color.BLACK),
    ORANGE_BLACK(Color.rgb(65, 68, 74), Color.rgb(255, 203, 135), Color.rgb(245, 211, 155), Color.rgb(245, 211, 155)),
    YELLOW_BLACK(Color.BLACK, Color.rgb(215, 215, 43), Color.rgb(50, 50, 50), Color.WHITE);

    private final Color backgroundColor;
    private final Color lineColor;
    private final Color rasterColor;
    private final Color textColor;


    Theme(final Color backgroundColor, final Color lineColor, final Color rasterColor, final Color textColor) {
        this.backgroundColor = backgroundColor;
        this.lineColor       = lineColor;
        this.rasterColor     = rasterColor;
        this.textColor       = textColor;
    }


    @Override public Color getBackgroundColor() { return backgroundColor; }

    @Override public Color getLineColor() { return lineColor; }

    @Override public Color getRasterColor() { return rasterColor; }

    @Override public Color getTextColor() { return textColor; }
}
