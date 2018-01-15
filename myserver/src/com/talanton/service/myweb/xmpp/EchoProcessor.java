/*
 * Modifications Copyright 2016 Nimrod Dayan
 *
 * Copyright 2014 Wolfram Rittmeyer.
 *
 * Portions Copyright Google Inc.
 *
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
 */

package com.talanton.service.myweb.xmpp;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.talanton.service.myweb.xmpp.messages.UpstreamMessage;

/**
 * Handles an echo request.
 */
public class EchoProcessor implements PayloadProcessor {
	private JsonAdapter<UpstreamMessage.Response> mUpstreamResponseAdapter;

    @Override
    public void handleMessage(UpstreamMessage.Request msg) {
        PseudoDao dao = PseudoDao.getInstance();
        CcsClient client = CcsClient.getInstance();
        String msgId = dao.getUniqueMessageId();
        // added by ksseo
        Moshi moshi = new Moshi.Builder().build();
        mUpstreamResponseAdapter = moshi.adapter(UpstreamMessage.Response.class);
        String json = mUpstreamResponseAdapter.toJson(new UpstreamMessage.Response(msg.getFrom(), msgId));
        client.send(json);
    }

}
