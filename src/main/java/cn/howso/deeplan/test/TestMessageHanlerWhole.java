package cn.howso.deeplan.test;

import javax.websocket.MessageHandler;

public class TestMessageHanlerWhole implements MessageHandler.Whole<String> {

	@Override
	public void onMessage(String message) {
		System.out.println(message);
	}

}
