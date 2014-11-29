/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Blakerz
 */
import java.io.*;
import java.net.*;

public class ServerThread extends Thread {
 private BufferedReader br;
 private PrintWriter pw;
 public String userName;
 public String[] userList;
  
 public ServerThread(Socket socket) {
     try {
         InputStream is = socket.getInputStream();
         InputStreamReader isr = new InputStreamReader(is);
         br = new BufferedReader(isr);
         OutputStream os = socket.getOutputStream();
         pw = new PrintWriter(os, true);
     }
     catch(Exception e){
           System.out.println("Exception: " + e);
   }
  }
 
 public void updateOnline(){
     userList = ServerCode.userNames();
 }
 
 public void run() {
        boolean doEcho = false;                         
        
     try {
         while(true) {             
             String str = br.readLine();
             doEcho = true;
           
             if(str.equalsIgnoreCase("NAMES")){
                 updateOnline();
                 int i = 0;
                 while(i<userList.length){
                     pw.println(userList[i]);
                     i++;
                 }
                 doEcho = false;
             }
             
             if(str.equalsIgnoreCase("USER_EXIT")){
                 ServerCode.endUser(this);
                 doEcho = false;
             }
             
             if(str.equalsIgnoreCase("NUM_USERS")){
                 int numuser = 0;
                 numuser = ServerCode.users();             
                 pw.println("Their are " + numuser + " user(s) online.");
                 doEcho = false;
             }
             
             if(str.startsWith("LOGIN")){
                 String[] login = str.split("#");
                 String email = login[1];
                 String password = login[2];          
                 String LI = ServerCode.Login(email, password);
                 pw.println(LI);         
                 if(LI.equals("SUCCESS")){
                     userName = email;
                 }
                 doEcho = false;
             }
             
             if(str.startsWith("REGISTER")){
                 String[] register = str.split("#");
                 String email = register[1];
                 String password = register[2]; 
                 String LI = ServerCode.Register(email, password);
                 pw.println(LI);
                 doEcho = false;
             }
             
             if(doEcho){
                  ServerCode.echoAll(str);
             }
                               
         }
     }
     catch(Exception e){
           System.out.println("Exception: " + e);
   }
 }
 
 public void echo(String str){
     try {
         pw.println(str);
     }
          catch(Exception e){
           System.out.println("Exception: " + e);
  }
 }
}
