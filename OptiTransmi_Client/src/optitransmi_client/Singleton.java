/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optitransmi_client;

import Connection.Model;
import java.util.PriorityQueue;
import Base.BasePackage;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Juan Diego
 */
public class Singleton {
    
    private int currentIdRequest;
    private double[] rates;     //tarifas: 1. Troncal, 2.Zonal
    private final Model client;
    private final ReentrantLock mutexToWrite;
    private final PriorityQueue<BasePackage> toWrite;
    private final ReentrantLock mutexToRead;
    private final PriorityQueue<BasePackage> toRead;
    
    private static Singleton singleton;
    
    public Singleton(){
        rates= new double[2];
        currentIdRequest = 0;
        client = new Model(7777);
        mutexToWrite = new ReentrantLock();
        toWrite = new PriorityQueue<>();
        mutexToRead = new ReentrantLock();
        toRead = new PriorityQueue<>();
        rates[0]=2500;
        rates[1]=2300;
    }
    
    public static Singleton getSingleton(){
        if(singleton == null)
            singleton = new Singleton();
        
        return singleton;
    }
    
    public Model getClient(){
        return client;
    }
    
    public int getCurrentIdRequest(){
        int reg = currentIdRequest;
        currentIdRequest++;
        return reg;
    }
    
    public boolean AddInToWriteQueue(BasePackage toAdd){
        boolean added = false;
        try{
            mutexToWrite.lock();
            added = toWrite.add(toAdd);
        } finally {
            mutexToWrite.unlock();
        }
        return added;
    }
    
    public BasePackage ReadInToWriteQueue(){
        BasePackage toReturn = null;
        try {
            mutexToWrite.lock();
            toReturn = toWrite.poll();
        } finally {
            mutexToWrite.unlock();
        }
        return toReturn;
    }
    
    public boolean AddInToReadQueue(BasePackage toAdd){
        boolean added = false;
        try{
            mutexToRead.lock();
            added = toRead.add(toAdd);
        } finally {
            mutexToRead.unlock();
        }
        return added;
    }
    
    public BasePackage ReadFromToReadQueue(){
        BasePackage toReturn = null;
        try {
            mutexToRead.lock();
            toReturn = toRead.poll();
        } finally {
            mutexToRead.unlock();
        }
        return toReturn;
    }
    
    public double[] getRates(){
        return rates;
    }
}
