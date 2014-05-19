package monitor;

import clients.Client;
import clients.ClientList;
import clients.Status;
import config.Configuration;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.TimerTask;

public class Cleanup extends TimerTask {

    private Date lastCleanup;
    private Integer cleanupInterval;
    private ClientList clientList;

    public Cleanup(){
        this.lastCleanup = new Date();
        this.cleanupInterval = Integer.parseInt(new Configuration().get("cleanupInterval"));
        this.clientList = ClientList.getInstance();
    }

    @Override
    public void run() {
        Client cc;
        Iterator<Client> it;
        ArrayList<Client> green;

        green = new ArrayList<Client>();
        it = this.clientList.iterator();
        while(it.hasNext()){
            cc = it.next();
            if(this.isOutdated(cc.getSignal()) == true && cc.getStatus() != Status.RED){
                cc.setStatus(Status.RED);
//System.out.println("xx " + cc.getAddress().getHostAddress() + ":" + cc.getPort());
            }
            if(this.isOutdated(cc.getSignal()) == false){
                switch (cc.getStatus()){
                    case RED:
                        cc.setStatus(Status.YELLOW);
                        break;
                    case YELLOW: break;
                    case GREEN:
                        green.add(cc);
                        break;
                }
//System.out.println("++ " + cc.getAddress().getHostAddress() + ":" + cc.getPort());
            }
        }

        // tell dispatcher green (active) clients
        DispatcherClient.getInstance().send(green);

        this.lastCleanup = new Date();
    }

    private Boolean isOutdated(Date date){
        return lastCleanup.after(new Date(date.getTime() + this.cleanupInterval));
    }
}
