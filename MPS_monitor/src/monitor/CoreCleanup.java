package monitor;

import core.Core;
import core.CoreList;
import core.CoreStatus;
import config.Configuration;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.TimerTask;

public class CoreCleanup extends TimerTask {

    private Date lastCleanup;
    private Integer cleanupInterval;
    private CoreList coreList;

    public CoreCleanup(){
        this.lastCleanup = new Date();
        this.cleanupInterval = Integer.parseInt(new Configuration().get("cleanupInterval"));
        this.coreList = CoreList.getInstance();
    }

    @Override
    public void run() {
        Core cc;
        Iterator<Core> it;
        ArrayList<Core> green;

        green = new ArrayList<Core>();
        it = this.coreList.iterator();
        while(it.hasNext()){
            cc = it.next();
            if(this.isOutdated(cc.getSignal()) == true && cc.getStatus() != CoreStatus.RED){
                cc.setStatus(CoreStatus.RED);
                //System.out.println("xx " + cc.getAddress().getHostAddress() + ":" + cc.getPort());
            }
            if(this.isOutdated(cc.getSignal()) == false){
                switch (cc.getStatus()){
                    case RED:
                        cc.setStatus(CoreStatus.YELLOW);
                        break;
                    case YELLOW: break;
                    case GREEN:
                        green.add(cc);
                        break;
                }
            System.out.println("++ " + cc.getAddress().getHostAddress() + ":" + cc.getPort());
            }
        }

        // tell dispatcher green (active) core
        DispatcherClient.getInstance().send(green);

        this.lastCleanup = new Date();
    }

    private Boolean isOutdated(Date date){
        return lastCleanup.after(new Date(date.getTime() + this.cleanupInterval));
    }
}
