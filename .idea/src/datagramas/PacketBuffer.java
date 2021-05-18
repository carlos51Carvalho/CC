import java.net.*;
import java.util.*;
import java.util.concurrent.locks.*;

public class PacketBuffer {

    Queue <DatagramPacket> packetBuffer;
    Lock lock;
    Condition empty;

    public PacketBuffer(){
        this.packetBuffer = new ArrayDeque<DatagramPacket>();
        lock = new ReentrantLock();
        empty = this.lock.newCondition();
    }

    public synchronized boolean isEmpty(){
        Boolean res = true;
        if(this.packetBuffer.size() > 0){
            res = false;
        }
        return res;
    }

    public DatagramPacket take(){
        DatagramPacket res = null;

        try{
            lock.lock();
            while(packetBuffer.size() == 0){
                empty.await();
            }
            res = packetBuffer.poll();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally{
            lock.unlock();
        }
        
        return res;
    }

    public void add(DatagramPacket p){
        try{
            lock.lock();
            packetBuffer.add(p);
            this.empty.signalAll();
        }finally{
            lock.unlock();
        }
    }

}
