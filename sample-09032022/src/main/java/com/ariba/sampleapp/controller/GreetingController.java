package com.ariba.sampleapp.controller;

import java.io.FileNotFoundException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ariba.sampleapp.service.GreetingService;
import com.ariba.sampleapp.model.Greeting;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * This controller deals with requests related with greeting message and has two
 * mappings:
 * <p>
 * /greeting - display the message load from application.yml file
 * <p>
 * /loadjson - force to reload application.yml file and rebind the model
 * Greeting
 */
@Controller
public class GreetingController {
    private static int count = 0;
    private Logger logger = Logger.getLogger(getClass());

    // the service interacting with Greeting model
    @Autowired
    private GreetingService greetingService;

    /**
     * Display the message load from application.yml file
     *
     * @param model this model will be bind to front-end template
     * @return 'greeting' template
     * @throws UnknownHostException
     * @throws SocketException
     */
    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public String greeting(Model model) throws UnknownHostException,
            SocketException {

        logger.info("\n[greeting] Showing greeting variable read from application.yml file:\t"
                + greetingService.getGreeting() + "\n");

        // bind model to template
        model.addAttribute("name", greetingService.getGreeting());

        model.addAttribute("jobname", System.getenv("NOMAD_JOB_NAME"));

        try {
            model.addAttribute("ip", getFirstNonLoopbackAddress(true, false)
                    .getHostAddress());
            model.addAttribute("count", ++count);
        } catch (Exception e) {
            model.addAttribute("ip", e.getMessage());
        }

        return "html/greeting/greeting";
    }


    /**
     * get public address
     *
     * @param preferIpv4
     * @param preferIPv6
     * @return
     * @throws Exception
     */
    private static InetAddress getFirstNonLoopbackAddress(boolean preferIpv4,
                                                          boolean preferIPv6) throws Exception {
        Enumeration<NetworkInterface> en = NetworkInterface
                .getNetworkInterfaces();

        while (en.hasMoreElements()) {

            NetworkInterface i = (NetworkInterface) en.nextElement();
            if (i.getName().equalsIgnoreCase("en0")
                    || i.getName().equalsIgnoreCase("eth0")) {
                for (Enumeration en2 = i.getInetAddresses(); en2
                        .hasMoreElements(); ) {
                    InetAddress addr = (InetAddress) en2.nextElement();
                    if (!addr.isLoopbackAddress()) {
                        if (addr instanceof Inet4Address) {
                            if (preferIPv6) {
                                continue;
                            }
                            return addr;
                        }
                        if (addr instanceof Inet6Address) {
                            if (preferIpv4) {
                                continue;
                            }
                            return addr;
                        }
                    }
                }
            }
        }

        throw new Exception("Error: Could not find public ip address!");
    }
}
