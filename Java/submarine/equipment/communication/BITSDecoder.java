
//////////////////////////////////////////////////////////////
/////////////////////|      Day 16      |/////////////////////
//////////////////////////////////////////////////////////////

package submarine.equipment.communication;

import submarine.core.*;
import java.io.*;
import java.util.*;

public class BITSDecoder 
{
    public static void main(String[] args) 
    {
        TimeMeasure timer = new TimeMeasure();
        Logger.print(timer, "Sum of version numbers", sumOfVersions(DataTray.getInput(16)));
    }

    public static int sumOfVersions(File file)
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            Message m = new Message(reader.readLine());
            return m.main.versionSum();
        }
        catch (Exception e) {Logger.error(e);return 0;}
    }
}
class Message
{
    String hex;
    String bin;
    Packet main;
    public Message(String hexadecimal)
    {
        hex = hexadecimal;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hex.length(); i++)
        {
            sb.append(hexToBin(hex.charAt(i)));
        }
        bin = sb.toString();
        main = Packet.decode(bin);
    }

    private static String hexToBin(char h)
    {
        switch (h)
        {
            case '0': return "0000";
            case '1': return "0001";
            case '2': return "0010";
            case '3': return "0011";
            case '4': return "0100";
            case '5': return "0101";
            case '6': return "0110";
            case '7': return "0111";
            case '8': return "1000";
            case '9': return "1001";
            case 'A': return "1010";
            case 'B': return "1011";
            case 'C': return "1100";
            case 'D': return "1101";
            case 'E': return "1110";
            case 'F': return "1111";
            default: throw new IllegalArgumentException("What the elves doing");
        }
    }
}

class Packet
{
    int len;
    int version;

    public static Packet decode(String bin)
    {
        int version = Integer.parseInt(bin.substring(0,3),2);
        int typeID = Integer.parseInt(bin.substring(3,6),2);
        return typeID == 4 ? Literal.decode(version,bin) : Operator.decode(version,typeID,bin);
    }
    public int versionSum()
    {
        return version;
    }
}

class Literal extends Packet
{
    long value;
    public static Literal decode(int version, String bin)
    {
        Literal outp = new Literal();
        outp.version = version;
        
        StringBuilder builder = new StringBuilder();
        int i = 6;
        while (bin.charAt(i) == '1')
        {
            builder.append(bin.substring(i+1,i+5));
            i+=5;
        }
        builder.append(bin.substring(i+1,i+5));
        outp.value = Long.parseLong(builder.toString(),2);

        outp.len = 6 + i-1;

        return outp; 
    }
}

class Operator extends Packet
{
    int typeID;
    Packet[] packets;
    public static Operator decode(int version, int typeID, String bin)
    {
        return bin.charAt(6) == '0' ? fifteenBit(version, typeID, bin) : elevenBit(version, typeID, bin);
    }
    public static Operator fifteenBit(int version, int typeID, String bin)
    {
        Operator outp = new Operator();
        outp.version = version;
        outp.typeID = typeID;
        List<Packet> packetlist = new ArrayList<>();

        int packetslen = Integer.parseInt(bin.substring(7,7+15),2);
        int packetcursor = 0;
        while (packetcursor < packetslen)
        {
            Packet p = Packet.decode(bin.substring(packetcursor+7+15));
            packetcursor += p.len;
            packetlist.add(p);
        }

        outp.packets = packetlist.toArray(new Packet[0]);
        outp.len = 7 + 15 + packetslen;

        return outp;
    }

    public static Operator elevenBit(int version, int typeID, String bin)
    {
        Operator outp = new Operator();
        outp.version = version;
        outp.typeID = typeID;

        int packetcount = Integer.parseInt(bin.substring(7,7+11),2);
        int packetcursor = 0;
        outp.packets = new Packet[packetcount];
        for (int i = 0; i < packetcount; i++)
        {
            outp.packets[i] = Packet.decode(bin.substring(packetcursor+7+11));
            packetcursor += outp.packets[i].len;
        }

        outp.len = 7 + 11 + packetcursor;
        
        return outp;
    }

    @Override
    public int versionSum()
    {
        int sum = 0;
        for (Packet packet : packets) {
            sum += packet.versionSum();
        }
        return sum + version;
    }
}


