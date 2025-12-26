package tsp.pro3600.memoryrace.interfaceswing;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import tsp.pro3600.memoryrace.connectionlocal.LocalConnectionHandler;
import tsp.pro3600.memoryrace.connectionmodel.*;

public class Main {
    public static void main(String[] args) throws ConnectionErrorException {
    	String rootPath; // dossier racine des photos
    	if (args.length == 0)
    	{
    		rootPath = "/tmp/photos/"; // emplacement par défaut
    	}
    	else
    	{
    		rootPath = args[0];
    	}
    	ConnectionHandler conn = new LocalConnectionHandler("127.0.0.1", 3306, "root", "password", rootPath);
    	conn.connect();
        FenetreAcceuil fenetreAcceuil = new FenetreAcceuil(conn);
//        fenetreAcceuil.addWindowListener(new WindowListener() {
//
//			@Override
//			public void windowOpened(WindowEvent e) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void windowClosing(WindowEvent e) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void windowClosed(WindowEvent e) {
//				try {
//					conn.disconnect();
//				} catch (ConnectionErrorException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//
//			}
//
//			@Override
//			public void windowIconified(WindowEvent e) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void windowDeiconified(WindowEvent e) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void windowActivated(WindowEvent e) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void windowDeactivated(WindowEvent e) {
//				// TODO Auto-generated method stub
//
//			}
//
//        });
        fenetreAcceuil.setVisible(true);
        
    }
}
