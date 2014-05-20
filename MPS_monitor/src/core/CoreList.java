package core;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

public class CoreList implements Iterable{

    private static CoreList instance;
    private ArrayList<Core> list;

    private CoreList(){
        CoreList.instance = null;
        this.list = new ArrayList<Core>();
    }

    public static CoreList getInstance() {
        if (CoreList.instance == null) {
            CoreList.instance = new CoreList();
        }
        return CoreList.instance;
    }

    public void add(InetAddress address, Integer port){
        if(this.has(address, port) == false){
            this.list.add(new Core(address, port));
        }
    }
    public void add(String address, String port){
        try {
            this.add(InetAddress.getByName(address), Integer.parseInt(port));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public Core get(InetAddress address, Integer port){
        Core cc;
        Iterator<Core> itcc = this.list.iterator();
        while(itcc.hasNext()){
            cc = itcc.next();
            if(cc.getAddress().equals(address) && cc.getPort().equals(port)){
                return cc;
            }
        }
        return null;
    }
    public Core get(String address, String port){
        try {
            return this.get(InetAddress.getByName(address), Integer.parseInt(port));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean has(InetAddress address, Integer port){
        Core coreCore;
        Iterator<Core> it = this.list.iterator();
        while(it.hasNext()){
            coreCore = it.next();
            if(coreCore.getAddress().equals(address) && coreCore.getPort().equals(port)){
                return true;
            }
        }
        return false;
    }
    public Boolean has(String address, String port){
        try {
            return this.has(InetAddress.getByName(address), Integer.parseInt(port));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String toString(){
        String str = "[";
        Iterator<Core> it = this.list.iterator();
        while(it.hasNext()){
            str += it.next().toString() + ",";
        }
        str = str.substring(0, str.length() - 1);
        str += "]";
        return str;
    }

    @Override
    public Iterator iterator() {
        return this.list.iterator();
    }
}
