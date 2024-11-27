package nslookup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class NSLookup {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(true) {
            System.out.print("> ");
            String hostName = br.readLine();

            if(hostName.equals("exit")) {
                break;
            }

            try {
                InetAddress[] inetAddresses = InetAddress.getAllByName(hostName);

                for(InetAddress inetAddress : inetAddresses) {
                    System.out.println(inetAddress.getHostName() + " : " +  inetAddress.getHostAddress());
                }
            } catch(UnknownHostException e) {
                e.printStackTrace();
            }
        }
    }
}
