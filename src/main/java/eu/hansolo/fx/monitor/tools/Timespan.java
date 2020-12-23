/*
 * Copyright (c) 2020 by Gerrit Grunwald
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.hansolo.fx.monitor.tools;

public enum Timespan {
    ONE_SECOND(1),
    TWO_SECONDS(2),
    THREE_SECONDS(3),
    FOUR_SECONDS(4),
    FIVE_SECONDS(5),
    SIX_SECONDS(6),
    SEVEN_SECONDS(7),
    EIGHT_SECONDS(8),
    NINE_SECONDS(9),
    TEN_SECONDS(10);

    private final int seconds;


    Timespan(final int seconds) {
        this.seconds = seconds;
    }


    public int getSeconds() { return seconds; }
}
