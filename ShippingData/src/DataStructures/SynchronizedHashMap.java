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
 * @param <K> Key of the map
 * @param <T> Element in the map
 */
public class SynchronizedHashMap<K, T> extends HashMap<K, T> {
    private final ReentrantLock hashMutex;
    
    public SynchronizedHashMap(){
        super();
        hashMutex = new ReentrantLock();
    }
    
    public int getSize(){
        int size = -1;
        try{
            hashMutex.lock();
            size = super.size();
        } finally {
            hashMutex.unlock();
        }
        return size;
    }
    
    public T put(K key, T element){
        T elementInserted = null;
        try{
            hashMutex.lock();
            elementInserted = super.put(key, element);
        } finally {
            hashMutex.unlock();
        }
        return elementInserted;
    }
    
    public T remove(Object key){
        T removed = null;
        try{
            hashMutex.lock();
            removed = super.remove(key);
        } finally {
            hashMutex.unlock();
        }
        return removed;
    }
    
    public boolean containsKey(Object key){
        boolean answer = false;
        try{
            hashMutex.lock();
            answer = super.containsKey(key);
        } finally {
            hashMutex.unlock();
        }
        return answer;
    }
    
    public boolean updateKey(K key, K newKey){
        boolean answer = false;
        try{
            hashMutex.lock();
            if(containsKey(key)){
                T object = remove(key);
                put(newKey, object);
                answer = true;
            }
        } finally {
            hashMutex.unlock();
        }
        return answer;
    }
    
    public T get(Object key){
        T element = null;
        try{
            hashMutex.lock();
            element = super.get(key);
        } finally {
            hashMutex.unlock();
        }
        return element;
    }
}
