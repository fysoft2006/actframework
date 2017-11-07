package act.ws;

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

import act.app.ActionContext;
import act.handler.RequestHandlerBase;
import com.alibaba.fastjson.JSON;
import org.osgl.$;
import org.osgl.http.H;
import org.osgl.util.C;
import org.osgl.util.S;

import javax.inject.Inject;
import java.util.Map;

/**
 * This request handler is responsible for generating a
 * secure ticket which can be used to authenticate a user.
 *
 * A typical use case is once a user logged in the frontend
 * app can request a secure ticket and use it to
 * authenticate a websocket connection
 */
public class SecureTicketHandler extends RequestHandlerBase {

    private SecureTicketCodec secureTicketCodec;

    @Inject
    public SecureTicketHandler(SecureTicketCodec codec) {
        this.secureTicketCodec = $.notNull(codec);
    }

    @Override
    public void handle(ActionContext context) {
        Object ticket = secureTicketCodec.createTicket(context.session());
        H.Format accept = context.accept();
        H.Response resp = context.prepareRespForWrite();
        resp.contentType(accept.contentType());
        String content;
        if (H.Format.JSON == accept) {
            Map<String, Object> map = C.map("ticket", ticket);
            content = JSON.toJSONString(map);
        } else if (H.Format.XML == accept) {
            content = S.concat("<?xml version=\"1.0\" ?><ticket>", ticket.toString(), "</ticket>");
        } else {
            content = ticket.toString();
        }
        resp.writeContent(content);
    }

    @Override
    public void prepareAuthentication(ActionContext context) {

    }
}
