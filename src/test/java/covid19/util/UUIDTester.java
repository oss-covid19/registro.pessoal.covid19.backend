package covid19.util;

import java.util.UUID;

public class UUIDTester {
    public static void main(String[] args) {
        UUID uuid = java.util.UUID.randomUUID();
        
        System.out.println(uuid);
    
        System.out.println(Long.MAX_VALUE);
    
        System.out.println(uuid.getLeastSignificantBits());
        System.out.println(uuid.getMostSignificantBits());
    }
}
