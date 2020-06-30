/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructures;

import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Juan Diego
 */
public class SynchronizedLinkedList<T> {
    private final LinkedList<T> linkedList;
    private final ReentrantLock listMutex;
    
    public SynchronizedLinkedList(){
        this.linkedList = new LinkedList<>();
        listMutex = new ReentrantLock();
    }
    
    public boolean isEmpty(){
        boolean isEmpty = true;
        try{
            listMutex.lock();
            isEmpty = linkedList.isEmpty();
        } finally {
            listMutex.unlock();
        }
        return isEmpty;
    }
    
    public int getSize(){
        int size = -1;
        try {
            listMutex.lock();
            size = linkedList.size();
        } finally {
            listMutex.unlock();
        }
        return size;
    }
    
    public boolean AddFirst(T e){
        boolean add = false;
        try {
            listMutex.lock();
            linkedList.addFirst(e);
            add = true;
        } finally {
            listMutex.unlock();
        }
        return add;
    }
    
    public boolean Add(T e){
        boolean add = false;
        try {
            listMutex.lock();
            add = linkedList.add(e);
        } finally {
            listMutex.unlock();
        }
        return add;
    }
    
    public T removeFirst(){
        T element = null;
        try {
            listMutex.lock();
            element = linkedList.removeFirst();
        } finally {
            listMutex.unlock();
        }
        return element;
    }
    
    public T removeLast(){
        T element = null;
        try {
            listMutex.lock();
            element = linkedList.removeLast();
        } finally {
            listMutex.unlock();
        }
        return element;
    }
}
