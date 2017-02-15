package org.everythingjboss.jdg;


import org.infinispan.client.hotrod.RemoteCache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JDGPutThread<K,V> implements Runnable {
    
    private K key;
    private V value;
    private RemoteCache<K,V> cache;
    
    private static final Logger logger = LogManager.getLogger(JDGPutThread.class);
    
    public JDGPutThread(RemoteCache<K,V> cache, K key, V value) {
        this.key = key;
        this.value = value;
        this.cache = cache;
    }

    @Override
    public void run() {
        cache.put(key,value);
        logger.debug("Done putting key : "+key);
    }

}
