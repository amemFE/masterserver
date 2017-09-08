package hello;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class GreetingController {
    String myip = "192.168.11.105";
    String jsonPath = "/home/asanka/ipmap.json";
    Map<String, String> ipmap = new HashMap<>();
    JSONParser parser = new JSONParser();

    public void initialize() {
        try {

            myip = InetAddress.getLocalHost().getHostAddress();


        } catch (UnknownHostException e) {

            e.printStackTrace();

        }
        try {

            Object tempobj = parser.parse(new FileReader(jsonPath));
            JSONObject jsonObject = (JSONObject) tempobj;


            for (Object key : jsonObject.keySet()) {
                //based on you key types
                String ip = (String) key;
                String location = (String) jsonObject.get(ip);
                System.out.println(ip + location);
                ipmap.put(ip, location);
                //Print key and value
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @RequestMapping("/greeting")
    public String greeting(Model model,HttpServletRequest request) {
        this.initialize();
        model.addAttribute("ip","192.168.11.105");
        String ip = request.getRemoteAddr();
        String loc = ipmap.get(ip);
        System.out.println(loc);
        if(loc!=null) {
            switch (loc) {
                case "s14":
                    return "wish";
                case "s12":
                    return "greeting";
            }
        }
        return "def";
    }


}
