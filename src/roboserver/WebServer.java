package roboserver;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WebServer {

      public static final ThreadPoolExecutor tpe=new ScheduledThreadPoolExecutor(5);
            
    public static void main(String[] args) throws Exception {
        lastSec=System.currentTimeMillis();
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/RoboSpider", new MyHandler());
        server.start();
      
    }

    
    static public Map<String, String> queryToMap(String query){
        Map<String, String> result = new HashMap<String, String>();
        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            if (pair.length>1) {
                result.put(pair[0], pair[1]);
            }else{
                result.put(pair[0], "");
            }
        }
        return result;
    }
    static RoboVoice rVoice=new RoboVoice();
    static long lastSec=0; 
    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
          
            String response = "<html><body>RoboSpider response:<br>";
            String query=t.getRequestURI().getQuery();
            
            Map<String,String> hGet=queryToMap(query);
            
            
            
            SpiderCommand spCommand=new SpiderCommand();
            
            if(System.currentTimeMillis()-lastSec>10000)
            {
                  rVoice.playVoiceUp();
                try {
                    Thread.sleep(800);
                } catch (InterruptedException ex) {
                    Logger.getLogger(WebServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
               
            
            
            if(hGet.containsKey("bm"))  spCommand.bodyMotorState=Integer.parseInt(hGet.get("bm"));
            if(hGet.containsKey("tbm")) spCommand.bodyMotorTimeOut=Integer.parseInt(hGet.get("tbm"));
            
            if(hGet.containsKey("hm"))  spCommand.headMotorState=Integer.parseInt(hGet.get("hm"));
            if(hGet.containsKey("thm")) spCommand.headMotorTimeOut=Integer.parseInt(hGet.get("thm"));
            
            
            
            if(spCommand.bodyMotorState!=0)
            {
                response+="Hmmm... bodyMotorState == \"" + spCommand.bodyMotorState + "\"<br>";
                
                if(spCommand.bodyMotorTimeOut==0)spCommand.bodyMotorTimeOut=2;
                if(spCommand.bodyMotorTimeOut!=0)
                    response+="Set disable after == \"" + spCommand.bodyMotorTimeOut + "\"s<br>";
                spCommand.bodyMotorEnable=true;
            }
            
            if(spCommand.headMotorState!=0)
            {
                response+="Hmmm... headMotorState == \"" + spCommand.headMotorState + "\"<br>";
                if(spCommand.headMotorTimeOut==0)spCommand.headMotorTimeOut=2;
                if(spCommand.headMotorTimeOut!=0)
                    response+="Set disable after == \"" + spCommand.headMotorTimeOut + "\"s<br>";
                spCommand.headMotorEnable=true;
            }
            if( spCommand.headMotorEnable) tpe.execute(new RoboHeadThread(spCommand));
            
            if(spCommand.bodyMotorEnable) tpe.execute(new RoboBodyThread(spCommand));
            
            if((spCommand.headMotorEnable) ||(spCommand.bodyMotorEnable))
            {
                lastSec=System.currentTimeMillis();            
            }
            
         response+="</body></html>";
            
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
        
      
   
        
        
    }

}
