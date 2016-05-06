package org.everythingjboss.jdg;

public interface KeyValueGenerator<K,V> {
    public K generateKey(long i);
    public V generateValue();
}
