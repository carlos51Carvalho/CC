package datagrams;

import java.net.*;
import java.io.*;

public class Sender implements Runnable {

    //lista com os pacotes recebidos
    private byte[] packetBuffer = new byte[256];
    private DatagramSocket socket;

    public Sender(DatagramSocket socket, byte[] packetBuffer){
        this.socket = socket;
        this.packetBuffer = packetBuffer;
    }

    @Override
    public void run() {

        while(true){
            DatagramPacket packet = this.packetBuffer.take();
            //encriptar informacao
            byte[] message = new byte[256];
            packet.setData(message);

            //enviar pacote
            try {
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


}
