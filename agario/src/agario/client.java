/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agario;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class client {
        DatagramSocket client;
        String ip;
        int porta;
    public client(String ip, int porta){
        try {
             client = new DatagramSocket();
        } catch (SocketException ex) {
            Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.ip=ip;
        this.porta=porta;
    }
    
    public String trasmeti(String data){
        try {
            byte[] sendBuffer = data.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length);
            sendPacket.setAddress(InetAddress.getByName(ip));
            sendPacket.setPort(porta);
            client.send(sendPacket);
            
            
            byte[] buffer = new byte[1500];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            client.receive(packet);
            byte[] dataReceived = packet.getData();
            String messaggioRicevuto = new String(dataReceived, 0, packet.getLength());
            return messaggioRicevuto;
            
            
        } catch (SocketException ex) {
            Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
