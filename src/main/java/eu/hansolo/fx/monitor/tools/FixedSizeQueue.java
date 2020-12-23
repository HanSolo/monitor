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

import java.util.concurrent.ArrayBlockingQueue;


public class FixedSizeQueue<E> extends ArrayBlockingQueue<E> {
    private static final long SERIAL_VERSION_UID = -7772085623838075506L;
    private              int  size;


    public FixedSizeQueue(final int size) {
        super(size);
        this.size = size;
    }


    @Override synchronized public boolean add(final E element) {
        if (super.size() == this.size) { this.remove(); }
        return super.add(element);
    }

    public E getElementAt(final int index) {
        if (index < 0 || index > size() - 1) { throw new IllegalArgumentException("Index out of bounds."); }
        return (E) toArray()[index];
    }
}
