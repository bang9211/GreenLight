/*
 * Copyright (C) 2015 Software&System Lab. Kangwon National University.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package mintapplication_pm1001;

import MinT.ExternalDevice.Sensing.Dust.MinTDriver_pm1001;
import MinTFramework.ExternalDevice.DeviceType;
import MinTFramework.MinT;
import MinTFramework.Network.Resource.Request;
import MinTFramework.Util.PlatformInfo;
import MinTFramework.storage.ThingProperty;

/**
 *
 * @author youngtak Han <gksdudxkr@gmail.com>
 */
public class Sharing_MinT extends MinT{
    public Sharing_MinT(){
        init();
    }

    private void init() {
        MinTDriver_pm1001 pm1001 = new MinTDriver_pm1001();
        this.addDevice(pm1001);
        this.putService(new DustMonitoringService(pm1001));
        this.addResource(new ThingProperty("DUST", DeviceType.DUST) {
            @Override
            public void set(Request rqst) {
            }
            
            @Override
            public Object get(Request rqst) {
                return null;
            }
        });
        
        System.out.println("Current disk Total Size: "+PlatformInfo.getCurrentDiskTotalSpace());
        System.out.println("Current disk Usable Size: "+PlatformInfo.getCurrentDiskUsableSpace());
        System.out.println("Current disk Free Size: "+PlatformInfo.getCurrentDiskFreeSpace());
        System.out.println("Used Memory: "+PlatformInfo.getUsageMemory());
    }
}    
