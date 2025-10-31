import java.io.*;
import java.util.*;

public class CacheDemo {
    static class LRUCache<K,V> extends LinkedHashMap<K,V> {
        private final int capacity;
        LRUCache(int cap){ super(cap,0.75f,true); capacity=cap; }
        protected boolean removeEldestEntry(Map.Entry<K,V> eldest){ return size()>capacity; }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        LRUCache<Integer,String> cache = new LRUCache<>(3);

        while(true){
            System.out.println("\n1) Add Data 2) View Cache 3) Exit");
            String ch = br.readLine();
            if(ch.equals("1")){
                System.out.print("Enter key: "); int k = Integer.parseInt(br.readLine());
                System.out.print("Enter value: "); String v = br.readLine();
                cache.put(k,v);
            }else if(ch.equals("2")){
                System.out.println("Cache Contents: "+cache);
            }else break;
        }
    }
}
