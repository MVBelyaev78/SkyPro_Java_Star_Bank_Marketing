package org.skypro.starbank.marketing.auxiliary;

import java.util.Collection;
import java.util.HashSet;

public class NewCollection<T> {
    public Collection<T> initCollection() {
        return new HashSet<T>();
    }
}
