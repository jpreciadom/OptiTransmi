/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructures;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Juan Diego
 */
public class SynchronizedHashMap<K, T> {
    public HashMap<K, T> hashMap;
    private final ReentrantLock hashMutex;
    
    public SynchronizedHashMap(){
        hashMap = new HashMap<>();
        hashMutex = new ReentrantLock();
    }
    
    public boolean isEmpty(){
        return getSize() == 0;
    }
    
    public int getSize(){
        int size = -1;
        try{
            hashMutex.lock();
            size = hashMap.size();
        } finally {
            hashMutex.unlock();
            return size;
        }
    }
    
    public boolean put(K key, T element){
        boolean answer = false;
        try{
            hashMutex.lock();
            hashMap.put(key, element);
            answer = true;
        } finally {
            hashMutex.unlock();
            return answer;
        }
    }
    
    public boolean remove(K key){
        boolean answer = false;
        try{
            hashMutex.lock();
            hashMap.remove(key);
            answer = true;
        } finally {
            hashMutex.unlock();
            return answer;
        }
    }
    
    public boolean exist(K key){
        boolean answer = false;
        try{
            hashMutex.lock();
            answer = hashMap.containsKey(key);
        } finally {
            hashMutex.unlock();
            return answer;
        }
    }
    
    public boolean update(K key, K newKey){
        boolean answer = false;
        try{
            hashMutex.lock();
            if(hashMap.containsKey(key)){
                T object = hashMap.remove(key);
                hashMap.put(newKey, object);
                answer = true;
            }
        } finally {
            hashMutex.unlock();
            return answer;
        }
    }
}
