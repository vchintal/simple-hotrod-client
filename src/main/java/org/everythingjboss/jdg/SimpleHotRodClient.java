package org.everythingjboss.jdg;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.Configuration;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleHotRodClient<K,V> { 
    
    private RemoteCacheManager rcm;
    private String serverEndpoint;
    private RemoteCache<K,V> cache;
    private Long numEntries;
    private KeyValueGenerator<K, V> keyValueGenerator;
    
    private static final Logger logger = LoggerFactory.getLogger(SimpleHotRodClient.class);

    public SimpleHotRodClient(String serverEndpoint, String cacheName, long numEntries) {
        this.serverEndpoint = serverEndpoint;
        this.rcm = new RemoteCacheManager(getConfiguration());
        this.cache = rcm.getCache(cacheName);
        this.numEntries = numEntries;
    }
    
    public void setKeyValueGenerator(KeyValueGenerator<K, V> keyValueGenerator) {
        this.keyValueGenerator = keyValueGenerator;
    }
    
    public void writeEntries()
            throws InterruptedException {

        if(keyValueGenerator == null) {
            logger.warn("The keyValueGenerator is null");
            return;
        }

        ExecutorService executor = Executors.newFixedThreadPool(100);
        StopWatch timer = new StopWatch();
        timer.start();
        
        for (int i = 0; i < this.numEntries; i++) {
            K key = keyValueGenerator.generateKey(i);
            V value = keyValueGenerator.generateValue();
            executor.execute(new JDGPutThread<K,V>(this.cache, key,value));    
        }
        
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);        
        timer.stop();
        logger.info("It took ["+timer.getTime()+"] milliseconds to finish the puts");
    }

    public void readEntries()
            throws InterruptedException {
        
        if(keyValueGenerator == null) {
            logger.warn("The keyValueGenerator is null");
            return;
        }

        StopWatch timer = new StopWatch();
        timer.start();

        ExecutorService executor = Executors.newFixedThreadPool(100);
        for (int i = 0; i < numEntries; i++) {
            K key = keyValueGenerator.generateKey(i);
            executor.execute(new JDGGetThread<K,V>(this.cache,key));
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        timer.stop();
        logger.info("It took ["+timer.getTime()+"] milliseconds to finish the gets");

    }
    
    private Configuration getConfiguration() {
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.addServers(serverEndpoint);
        return builder.build();
    }

    public static void main(String[] args) throws InterruptedException, IOException {

        Properties properties = new Properties();
        ClassLoader cl = SimpleHotRodClient.class.getClassLoader();
      
        final File jdgProperties = new File(cl.getResource("jdg.properties").getFile());
        properties.load(new FileReader(jdgProperties));
        
        final String serverEndpoint = properties.getProperty("serverEndpoint");
        final String cacheName = properties.getProperty("cacheName");
        final Long numEntries = Long.valueOf(properties.getProperty("numEntries"));
        
        /***
        
        // Use the following code segment to compress the file content of sample.txt 
        // and use it as the content of the cache entry
        final File sampleFile = new File(cl.getResource("sample.txt").getFile());
        SimpleHotRodClient<Long,byte []> shrc = new SimpleHotRodClient<Long,byte []>(serverEndpoint,cacheName,numEntries);
        shrc.setKeyValueGenerator(new CompressedFileContentKeyValueGenerator(sampleFile));

        // Use the following code segment to use the file content of sample.txt 
        // as the content of the cache entry
        final File sampleFile = new File(cl.getResource("sample.txt").getFile());
        SimpleHotRodClient<Long,char []> shrc = new SimpleHotRodClient<Long,char []>(serverEndpoint,cacheName,numEntries);
        shrc.setKeyValueGenerator(new FileContentKeyValueGenerator(sampleFile));
            
        ***/

        // Use the following code segment to generate a byte[] value generator
        final Integer sizeInBytes = Integer.valueOf(properties.getProperty("sizeInBytes"));
        SimpleHotRodClient<Long,byte[]> shrc = new SimpleHotRodClient<Long,byte []>(serverEndpoint,cacheName,numEntries);
        shrc.setKeyValueGenerator(new ByteKeyValueGenerator(sizeInBytes));
        
        shrc.writeEntries();
        shrc.readEntries();
    }

}
