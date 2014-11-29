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
import java.util.*;

public class ServerCode {
 static Vector serverThreads;
 static BufferedWriter out;
 static BufferedWriter makeUser;
 static FileInputStream fis = null;
 static BufferedInputStream bis = null;
 static DataInputStream dis = null;
 
 public static void main(String args[]){


     try{
         serverThreads = new Vector();
         ServerSocket ss = new ServerSocket(4816);
         
         try {
             out = new BufferedWriter(new FileWriter("ChattaLog.txt", true));                              
         } catch (Exception ex) { System.out.println(ex); }
         
         while(true){
             Socket s = ss.accept();
             ServerThread st = new ServerThread(s);
             st.start();
             serverThreads.addElement(st);
         }
     }
     catch(Exception e){
         System.out.println("Exception: " + e);
     }
 }
     
     public synchronized static int users(){
         Enumeration e = serverThreads.elements();
         while(e.hasMoreElements()){
         try {                 
                 ServerThread st = (ServerThread)e.nextElement();
                 if(st.isAlive()){

                 } else { 
                     endUser(st);
                 }                  
             } catch (Exception ex){
                 System.out.println("Exception: " + ex);
             }
         }
         
        return serverThreads.size();
     }
     
     public synchronized static String[] userNames(){
         String users = "";
         Enumeration e = serverThreads.elements();
         while(e.hasMoreElements()){
         try {                 
                 ServerThread st = (ServerThread)e.nextElement();
                 if(st.isAlive()){
                     
                     if(users.isEmpty()){
                         users = st.userName;
                     } else {
                         users = users + "###" + st.userName;
                     }
                     
                 } else {                     
                     endUser(st);
                 }                  
             } catch (Exception ex){
                 System.out.println("Exception: " + ex);
             }
         }
        String[] toReturn = users.split("###");
        return toReturn;
     }
     
     public synchronized static void echoAll(String str){
         Enumeration e = serverThreads.elements();
         str = (str + "\n"); 
         
         while(e.hasMoreElements()){
             try {                 
                 ServerThread st = (ServerThread)e.nextElement();
                 if(st.isAlive()){
                    st.echo(str);
                 } else { 
                     endUser(st);
                 }                  
             }
             catch (Exception ex){
                 System.out.println("Exception: " + ex);
             }
         }
         
         try {
                 out.write(str);
                 out.flush();
         } catch (Exception ex) {}
     }
     
     
     public synchronized static String Login(String email, String password) {
         try {
             File f = new File(email + ".txt");
             if(f.exists()){   
                 FileInputStream fstream = new FileInputStream(f);
                 DataInputStream in = new DataInputStream(fstream);
                 BufferedReader br = new BufferedReader(new InputStreamReader(in));
                 String strLine;
                 while ((strLine = br.readLine()) != null)   {
                     if(strLine.equals(password)){
                         return "SUCCESS";
                     } else {
                         return "FAIL";
                     }
                 }                 
                 in.close();
             } else {
                 return "FAIL";
             }  
         } catch (Exception e) { System.out.println(e); }
        return "FAIL"; 
     }
     
     public synchronized static String Register(String email, String password){
         try {    
             File f = new File(email + ".txt");
             if(f.exists()) { return "FAIL"; }
         } catch (Exception ex) { }
         
         try {
             makeUser = new BufferedWriter(new FileWriter((email + ".txt"), true));                              
             makeUser.write(password);
             makeUser.flush();
             makeUser.close();
         } catch (Exception ex) {
             System.out.println(ex);
             return "FAIL";
         }
         return "SUCCESS";
     }
     
     public synchronized static void endUser(Thread thread) {       
         serverThreads.remove(thread);
     }   
 }
