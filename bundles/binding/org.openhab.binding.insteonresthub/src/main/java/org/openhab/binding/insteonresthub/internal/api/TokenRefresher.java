package org.openhab.binding.insteonresthub.internal.api;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openhab.binding.insteonresthub.internal.InsteonRestApi;

public class TokenRefresher extends Poller {

	@Override
	public void run() {
		Token newToken = Authorization.refreshToken(Authorization.getToken());
		long sleepTimeSec = (newToken.Expiration.getTime() - Calendar.getInstance().getTime().getTime())/1000;
		_futureScheduler.schedule(this, sleepTimeSec, TimeUnit.SECONDS);
	}

}
