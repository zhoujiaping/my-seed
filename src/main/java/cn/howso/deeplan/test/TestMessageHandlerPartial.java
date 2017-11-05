package cn.howso.deeplan.test;

import javax.websocket.MessageHandler;

public class TestMessageHandlerPartial implements MessageHandler.Partial<String>{

	@Override
	public void onMessage(String messagePart, boolean last) {
		System.out.println(messagePart);
		System.out.println(last);
	}


}
