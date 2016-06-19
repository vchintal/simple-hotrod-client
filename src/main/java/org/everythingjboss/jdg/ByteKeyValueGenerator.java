package org.everythingjboss.jdg;

public class ByteKeyValueGenerator implements KeyValueGenerator<Long, byte[]> {

    private int size;
    
    public ByteKeyValueGenerator(int size) {
        this.size = size;
    }

    @Override
    public Long generateKey(long i) {
        return Long.valueOf(i);
    }

    @Override
    public byte[] generateValue() {
        return new byte[size];
    }

}
