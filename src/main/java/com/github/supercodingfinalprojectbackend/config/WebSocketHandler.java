package com.github.supercodingfinalprojectbackend.config;

// 입장 및 퇴장 메세지 필요 시 다시 구현예정
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class WebSocketHandler extends TextWebSocketHandler {
//
//    private static List<WebSocketSession> list = new ArrayList<>();
//
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        String payload = message.getPayload();
//        log.info("payload {}", payload);
//
//        for (WebSocketSession sess : list){
//            sess.sendMessage(message);
//        }
//    }
//
//    /* Client가 접속 시 호출되는 메서드 */
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//
//        list.add(session);
//
//        log.info(session + " 클라이언트 접속");
//    }
//
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//
//        log.info(session + " 클라이언트 접속 해제");
//        list.remove(session);
//    }
//
//}
