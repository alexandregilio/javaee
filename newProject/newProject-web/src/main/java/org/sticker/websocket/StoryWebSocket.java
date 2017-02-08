package org.sticker.websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;

@ServerEndpoint(
        value = "/story/notifications",
        encoders = {StickerEncoder.class},
        decoders = {StickerDecoder.class})
public class StoryWebSocket {

  private static final List<Sticker> stickers = Collections.synchronizedList(new LinkedList<Sticker>());
  private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

  @OnMessage
  public void onMessage(Session session, Sticker sticker) {
    stickers.add(sticker);
    Set<Session> openSessions = session.getOpenSessions();
    for (Session openSession : openSessions) {
      try {
        openSession.getBasicRemote().sendObject(sticker);
      } catch (IOException | EncodeException ex) {
        Logger.getLogger(StoryWebSocket.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }

  @OnOpen
  public void onOpen(Session session) {
    sessions.add(session);
    synchronized (stickers) {
      for (Sticker sticker : stickers) {
        try {
          session.getBasicRemote().sendObject(sticker);
        } catch (IOException | EncodeException ex) {
          Logger.getLogger(StoryWebSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
  }

  @OnClose
  public void onClose(Session session) {
    sessions.remove(session);
  }
}
