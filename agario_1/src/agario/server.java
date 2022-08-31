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

public class server {
        DatagramSocket server;
    public server(){
        try {
             server = new DatagramSocket(12345);
        } catch (SocketException ex) {
            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String ricevi(String data){
        try {
            byte[] buffer = new byte[1500];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            server.receive(packet);
            byte[] dataReceived = packet.getData();
            String messaggioRicevuto = new String(dataReceived, 0, packet.getLength());
            
            byte[] sendBuffer = data.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length);
            sendPacket.setAddress(packet.getAddress());
            sendPacket.setPort(packet.getPort());
            server.send(sendPacket);
            
            return messaggioRicevuto;
        } catch (SocketException ex) {
            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
        }
            return null;
    }
    
}
