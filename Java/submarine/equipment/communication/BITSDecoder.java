
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
        Logger.print(timer, "Sum of version numbers", sumOfVersions(DataTray.getTest(16)));
    }


    public static int sumOfVersions(File file)
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            Message m = new Message(reader.readLine());
            return 5;
        }
        catch (Exception e) {Logger.error(e);}
        return 2;
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
            sb.append(HexToBin(hex.charAt(i)));
        }
        bin = sb.toString();
        main = Packet.decode(bin);

        for (int i = 0; i < bin.length(); i++)
        {
            int ver;
            int type;
            
        }
    }

    private static String HexToBin(char h)
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
            default: throw new IllegalArgumentException("These elves are tripping");
        }
    }
}

class Packet
{
    int version;
    int typeID;
    public Packet(int version, int typeID)
    {
        this.version = version;
        this.typeID = typeID;
    }
    public static Packet decode(String bin)
    {
        int version = Integer.parseInt(bin.substring(0,3),2);
        int typeID = Integer.parseInt(bin.substring(3,6),2);
        return typeID == 4 ? new Literal(version,typeID,bin) : new Operator(version,typeID,bin);
    }
}

class Literal extends Packet
{
    int value;
    public Literal(int version, int typeID, String bin)
    {
        super(version,typeID);
    }
}

class Operator extends Packet
{
    Packet[] packets;
    public Operator(int version, int typeID, String bin)
    {
        super(version,typeID);
    }
}

