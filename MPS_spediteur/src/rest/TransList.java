package rest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by michaseverin on 05.06.14.
 */
public class TransList {

    private static TransList instance = null;
    private ArrayList<JSONObject> list;

    private TransList(){
        this.list = new ArrayList<JSONObject>();
    }

    public static TransList getInstance(){
        if(TransList.instance == null){
            TransList.instance = new TransList();
        }
        return TransList.instance;
    }

    public JSONObject get(Long transportauftragNr){
        JSONObject next = null;
        Iterator<JSONObject> it = this.list.iterator();
        while(it.hasNext()){
            next = it.next();
            if(next.get("transportauftragsNr") == transportauftragNr){
                return next;
            }
        }
        return null;
    }

    public JSONArray get(){
        JSONArray array = new JSONArray();
        Iterator<JSONObject> it = this.list.iterator();
        while(it.hasNext()){
            array.add(it.next());
        }
        return array;
    }

    public void lieferungErfolgt(Long nr, Boolean boo){
        if(this.has(nr)){
            this.get(nr).put("lieferungErfolgt", boo);
        }
    }

    public void set(JSONObject transportauftrag){
        if(!this.has((Long)transportauftrag.get("transportauftragsNr"))){
            this.list.add(transportauftrag);
        }
    }

    public Boolean has(Long transportauftragNr){
        JSONObject next = null;
        Iterator<JSONObject> it = this.list.iterator();
        while(it.hasNext()){
            next = it.next();
            if(next.get("transportauftragsNr") == transportauftragNr){
                return true;
            }
        }
        return false;
    }

    public void add(JSONObject obj){
        if (!this.has((Long)obj.get("transportauftragsNr"))){
            this.list.add(obj);
        }
    }

    public void remove(Long transportauftragNr){
        if(this.has(transportauftragNr)){
            JSONObject next = null;
            Iterator<JSONObject> it = this.list.iterator();
            while(it.hasNext()){
                next = it.next();
                if(next.get("transportauftragsNr") == transportauftragNr){
                    it.remove();
                    break;
                }
            }
        }
    }
}
