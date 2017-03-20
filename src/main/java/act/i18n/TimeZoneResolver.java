package act.i18n;

/*-
 * #%L
 * ACT Framework
 * %%
 * Copyright (C) 2014 - 2017 ActFramework
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import act.controller.Controller;
import org.osgl.http.H;
import org.osgl.mvc.annotation.PostAction;
import org.osgl.util.S;

import java.util.Calendar;

@SuppressWarnings("unused")
public class TimeZoneResolver extends Controller.Util {

    public static final String SESSION_KEY = "__tz__";

    @PostAction("i18n/timezone")
    public static void updateTimezoneOffset(int offset, H.Session session) {
        session.put(SESSION_KEY, offset);
    }

    public static int timezoneOffset() {
        return timezoneOffset(H.Session.current());
    }

    public static int timezoneOffset(H.Session session) {
        String s = null != session ? session.get(SESSION_KEY) : null;
        return S.notBlank(s) ? Integer.parseInt(s) : serverTimezoneOffset();
    }

    public static int serverTimezoneOffset() {
        Calendar cal = Calendar.getInstance();
        return -(cal.get(Calendar.ZONE_OFFSET) + cal.get(Calendar.DST_OFFSET)) / (1000 * 60);
    }

}
