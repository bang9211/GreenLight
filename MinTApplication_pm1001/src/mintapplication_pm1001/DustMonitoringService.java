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
import MinTFramework.MinT;
import MinTFramework.SystemScheduler.Service;

/**
 *
 * @author soobin Jeon <j.soobin@gmail.com>, chungsan Lee <dj.zlee@gmail.com>,
 * youngtak Han <gksdudxkr@gmail.com>
 */
class DustMonitoringService extends Service {

    MinT frame = MinT.getInstance();
    MinTDriver_pm1001 pm1001;

    public DustMonitoringService(MinTDriver_pm1001 pm1001) {
        super("clientService");
        this.pm1001 = pm1001;
    }

    @Override
    public void execute() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                float dust = pm1001.getData();
                if(dust != -1)
                {
                    System.out.println("Dust : " + dust + "ug/m^3");
                }
                Thread.sleep(2000);
            }
        } catch (InterruptedException ex) {

        }
    }

}
