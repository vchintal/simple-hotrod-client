package org.everythingjboss.jdg;

import org.infinispan.client.hotrod.RemoteCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JDGGetThread<K,V> implements Runnable {
    
    private K key;
    private RemoteCache<K,V> cache;
    private static final Logger logger = LoggerFactory.getLogger(JDGGetThread.class);
    
    public JDGGetThread(RemoteCache<K,V> cache, K key) {
        this.key = key;
        this.cache = cache;
    }

    @Override
    public void run() {
        V value = cache.get(key);        
        logger.debug("Done getting key : "+key);
        
        // Placeholder for any verification of value instance
    }

}
