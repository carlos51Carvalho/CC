package datagrams;

import java.net.*;
import java.io.*;

public class Catcher implements Runnable{

    private byte[] packetBuffer;
    private DatagramSocket socket;

    public Catcher(DatagramSocket socket, byte[] packetBuffer){
        this.socket = socket;
        this.packetBuffer = packetBuffer;
    }

    //hello?
    @Override
    public void run() {

        while(true){
            byte[] received = new byte[256];
            DatagramPacket packet = new DatagramPacket(received, received.length);

            //recebe o pacote
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            byte[] mensagem = packet.getData();
            //decrypt mensagem antes de colocar no buffer
            //byte [] mensagem = packet.getData().decrypt();

            if(mensagem!= null){
                packet.setData(mensagem);
                packetBuffer.add(packet);
            }

        }

    }
}
